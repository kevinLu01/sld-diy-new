<template>
  <main class="app">
    <header class="header">
      <div class="brand">
        <div>
          <h1>生利达冷冻设备｜DIY商城</h1>
          <p class="sub">一键套用方案包 + DIY 微调 + 提交询价</p>
        </div>
        <span class="badge">MVP 联调</span>
      </div>
      <div class="hero">
        <h2 class="hero-title">推荐方案包，3 分钟完成选型</h2>
        <p>支持工程冷库/门店冷链/中央空调的快速套用与价格联动。</p>
        <div class="quick-grid">
          <div class="quick-item">整机方案</div>
          <div class="quick-item">辅材包</div>
          <div class="quick-item">送装服务</div>
        </div>
      </div>
    </header>

    <section class="section">
      <div class="section-head">
        <h2>1) 推荐方案包</h2>
        <span class="hint">点击套用后自动带入配置</span>
      </div>
      <p v-if="loadError" class="error">{{ loadError }}</p>
      <p v-else-if="!packages.length" class="hint">暂无方案包，请检查 /api/packages 是否可用</p>
      <div class="plans">
        <div
          v-for="pkg in filteredPackages"
          :key="pkg.id"
          class="plan-card"
          :class="{ active: pkg.id === selectedPackageId }"
        >
          <div class="plan-top">
            <div>
              <h3>{{ pkg.name }}</h3>
              <p class="plan-desc">{{ pkg.summary || '默认配置方案' }}</p>
            </div>
            <span class="plan-level">{{ pkg.scene }}</span>
          </div>
          <div class="plan-footer">
            <div class="plan-price">¥{{ formatPrice(pkg.defaultPriceCents) }}</div>
            <button class="btn" @click="applyPackage(pkg)">一键套用</button>
          </div>
        </div>
      </div>
    </section>

    <section class="section">
      <div class="section-head">
        <h2>2) 场景筛选</h2>
        <span class="hint">支持不同场景 SKU 参考</span>
      </div>
      <div class="type-tabs">
        <button
          v-for="scene in scenes"
          :key="scene"
          class="tab"
          :class="{ active: selectedScene === scene }"
          @click="selectScene(scene)"
        >
          {{ scene }}
        </button>
      </div>
      <p class="hint">当前筛选：{{ selectedScene }}</p>
    </section>

    <section class="section">
      <div class="section-head">
        <h2>3) DIY 微调</h2>
        <span class="hint">可替换组件与数量微调</span>
      </div>
      <div class="builder">
        <div
          v-for="item in builderItems"
          :key="item.skuId"
          class="builder-row"
        >
          <div>
            <strong>{{ item.skuName }}</strong>
            <span>单价 ¥{{ formatPrice(item.basePriceCents) }}</span>
          </div>
          <div class="qty">
            <button class="btn-lite" @click="changeQty(item.skuId, -1)" :disabled="item.quantity <= 1">-</button>
            <span>{{ item.quantity }}</span>
            <button class="btn-lite" @click="changeQty(item.skuId, 1)">+</button>
          </div>
        </div>
        <div v-if="!builderItems.length" class="empty">请先套用方案包</div>
      </div>

      <div v-if="rulesResult" class="rules">
        <div class="rules-head">
          <span class="rules-title">兼容校验</span>
          <span class="rules-status" :class="rulesResult.isCompatible ? 'ok' : 'warn'">
            {{ rulesResult.isCompatible ? '兼容' : '需确认' }}
          </span>
        </div>
        <ul v-if="rulesResult.violations.length" class="rules-list">
          <li v-for="item in rulesResult.violations" :key="item">{{ item }}</li>
        </ul>
        <ul v-if="rulesResult.replacements.length" class="rules-list">
          <li v-for="item in rulesResult.replacements" :key="item">{{ item }}</li>
        </ul>
      </div>
    </section>

    <section class="section">
      <div class="section-head">
        <h2>4) 询价提交</h2>
        <span class="hint">提交后销售将联系您</span>
      </div>
      <div class="form">
        <label>
          联系人
          <input v-model="form.customerName" placeholder="请输入姓名" />
        </label>
        <label>
          手机号
          <input v-model="form.phone" placeholder="请输入手机号" />
        </label>
        <label>
          面积（㎡）
          <input v-model.number="form.area" type="number" min="1" placeholder="例如 60" />
        </label>
        <label>
          项目地址
          <input v-model="form.projectAddress" placeholder="请输入项目地址" />
        </label>
        <button class="btn" @click="submitInquiry" :disabled="submitting">
          {{ submitting ? '提交中...' : '提交询价' }}
        </button>
        <p v-if="submitMessage" class="hint">{{ submitMessage }}</p>
      </div>
    </section>

    <section class="section card" style="margin-bottom: 16px;">
      <div class="section-head" style="margin-bottom: 6px;">
        <h2>5) 生利达服务保障</h2>
      </div>
      <p class="service">7×12 小时技术支持、全国安装协同、核心部件质保 18 个月。</p>
    </section>

    <div class="total">
      <div>
        <small>预估总价（含税）</small>
        <div class="total-price">¥{{ formatPrice(totalPriceCents) }}</div>
      </div>
      <button class="btn" @click="submitInquiry" :disabled="submitting">提交询价</button>
    </div>

    <div class="footer-link">
      后台管理原型：<a href="/admin.html">admin.html</a>
    </div>

    <nav class="bottom-nav">
      <button
        v-for="item in navItems"
        :key="item"
        :class="{ active: activeNav === item }"
        @click="selectNav(item)"
      >
        {{ item }}
      </button>
    </nav>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';

type PackageSummary = {
  id: number;
  name: string;
  scene: string;
  defaultPriceCents: number;
  summary?: string;
  status: string;
};

type PackageItem = {
  skuId: number;
  skuName: string;
  quantity: number;
  isReplaceable: boolean;
  basePriceCents: number;
};

type PackageDetail = {
  id: number;
  name: string;
  scene: string;
  defaultPriceCents: number;
  summary: string;
  status: string;
  items: PackageItem[];
};

type PricingResponse = {
  totalPriceCents: number;
  breakdown: Array<{ skuId: number; quantity: number; unitPriceCents: number; lineTotalCents: number }>;
};

type RulesResponse = {
  isCompatible: boolean;
  violations: string[];
  replacements: string[];
};

const API_BASE = import.meta.env.PROD ? '/api' : (import.meta.env.VITE_API_BASE ?? 'http://localhost:8081');
const packages = ref<PackageSummary[]>([]);
const selectedPackageId = ref<number | null>(null);
const detail = ref<PackageDetail | null>(null);
const totalPriceCents = ref(0);
const selectedScene = ref('全部');
const submitting = ref(false);
const submitMessage = ref('');
const rulesResult = ref<RulesResponse | null>(null);
const loadError = ref('');
const navItems = ['首页', '方案包', '订单', '我的'];
const activeNav = ref('首页');

const form = reactive({
  customerName: '',
  phone: '',
  projectAddress: '',
  area: undefined as number | undefined
});

const scenes = computed(() => {
  const unique = new Set(packages.value.map((pkg) => pkg.scene));
  return ['全部', ...Array.from(unique)];
});

const filteredPackages = computed(() => {
  if (selectedScene.value === '全部') return packages.value;
  return packages.value.filter((pkg) => pkg.scene === selectedScene.value);
});

const builderItems = computed(() => detail.value?.items ?? []);

const formatPrice = (cents: number) => (cents / 100).toLocaleString('zh-CN');

const loadPackages = async () => {
  loadError.value = '';
  try {
    const res = await fetch(`${API_BASE}/packages`);
    if (!res.ok) {
      loadError.value = `方案包加载失败：${res.status}`;
      return;
    }
    packages.value = await res.json();
  } catch (err) {
    loadError.value = err instanceof Error ? err.message : '方案包加载失败';
  }
};

const applyPackage = async (pkg: PackageSummary) => {
  selectedPackageId.value = pkg.id;
  const res = await fetch(`${API_BASE}/packages/${pkg.id}`);
  if (res.ok) {
    detail.value = await res.json();
  }
  await refreshPricing();
  await validateRules();
};

const refreshPricing = async () => {
  const items = builderItems.value.map((item) => ({ skuId: item.skuId, quantity: item.quantity }));
  const res = await fetch(`${API_BASE}/pricing/calc`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ packageId: selectedPackageId.value, items })
  });
  if (!res.ok) return;
  const data: PricingResponse = await res.json();
  totalPriceCents.value = data.totalPriceCents;
};

const validateRules = async () => {
  const items = builderItems.value.map((item) => ({ skuId: item.skuId, quantity: item.quantity }));
  const res = await fetch(`${API_BASE}/rules/validate`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ items, scene: detail.value?.scene, area: form.area })
  });
  if (!res.ok) return;
  rulesResult.value = await res.json();
};

const changeQty = async (skuId: number, delta: number) => {
  if (!detail.value) return;
  detail.value.items = detail.value.items.map((item) => {
    if (item.skuId !== skuId) return item;
    const next = Math.max(1, item.quantity + delta);
    return { ...item, quantity: next };
  });
  await refreshPricing();
  await validateRules();
};

const selectScene = (scene: string) => {
  selectedScene.value = scene;
};

const selectNav = (item: string) => {
  activeNav.value = item;
};

const submitInquiry = async () => {
  if (!form.customerName || !form.phone) {
    submitMessage.value = '请填写联系人与手机号';
    return;
  }
  submitting.value = true;
  submitMessage.value = '';
  const payload = {
    customerName: form.customerName,
    phone: form.phone,
    projectAddress: form.projectAddress,
    area: form.area,
    scene: detail.value?.scene,
    packageId: selectedPackageId.value,
    selectedConfigJson: JSON.stringify(builderItems.value),
    calcPriceCents: totalPriceCents.value
  };
  const res = await fetch(`${API_BASE}/inquiries`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  if (res.ok) {
    submitMessage.value = '询价提交成功，我们会尽快联系您。';
  } else {
    submitMessage.value = '提交失败，请稍后再试。';
  }
  submitting.value = false;
};

onMounted(async () => {
  await loadPackages();
  const first = packages.value[0];
  if (first) {
    await applyPackage(first);
  }
});
</script>

<style scoped>
:root {
  --brand: #0a7cff;
  --brand-dark: #045fc7;
  --bg: #f3f7fb;
  --card: #ffffff;
  --text: #16253d;
  --sub: #5c6f8d;
  --radius: 16px;
  --shadow: 0 8px 24px rgba(22, 37, 61, 0.08);
}

* {
  box-sizing: border-box;
}

.app {
  max-width: 430px;
  margin: 0 auto;
  background: linear-gradient(180deg, #e9f3ff 0%, #f6faff 130px, var(--bg) 260px);
  min-height: 100vh;
  padding-bottom: 88px;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  color: var(--text);
}

.header {
  padding: 18px 16px 12px;
}

.brand {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}

.brand h1 {
  margin: 0;
  font-size: 18px;
}

.sub {
  margin: 6px 0 0;
  color: var(--sub);
  font-size: 12px;
}

.badge {
  background: #eaf3ff;
  color: var(--brand-dark);
  border: 1px solid #c7ddff;
  border-radius: 999px;
  padding: 4px 10px;
  font-size: 12px;
}

.hero {
  margin-top: 14px;
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 14px;
}

.hero-title {
  margin: 0 0 8px;
  font-size: 17px;
  line-height: 1.3;
}

.hero p {
  margin: 0;
  color: var(--sub);
  font-size: 13px;
}

.quick-grid {
  margin-top: 12px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 10px;
}

.quick-item {
  background: #f4f9ff;
  border-radius: 12px;
  padding: 10px 6px;
  text-align: center;
  font-size: 12px;
  color: #38557f;
}

.section {
  margin: 14px 16px 0;
}

.section-head {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 10px;
}

.section h2 {
  margin: 0;
  font-size: 16px;
}

.hint {
  font-size: 12px;
  color: var(--sub);
}

.error {
  color: #c0392b;
  font-size: 12px;
  margin: 0 0 8px;
}

.plans {
  display: grid;
  gap: 10px;
}

.plan-card {
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 12px;
  border: 1px solid transparent;
}

.plan-card.active {
  border-color: #9cc6ff;
  background: linear-gradient(180deg, #ffffff 0%, #f6faff 100%);
}

.plan-top {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  align-items: flex-start;
}

.plan-top h3 {
  margin: 0;
  font-size: 14px;
}

.plan-level {
  font-size: 11px;
  border-radius: 999px;
  padding: 3px 8px;
  background: #ecf5ff;
  color: #245184;
  white-space: nowrap;
}

.plan-desc {
  margin: 8px 0;
  color: var(--sub);
  font-size: 12px;
}

.plan-footer {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.plan-price {
  font-size: 16px;
  font-weight: 700;
  color: var(--brand-dark);
}

.btn {
  border: none;
  border-radius: 10px;
  background: var(--brand);
  color: #fff;
  font-size: 12px;
  padding: 7px 11px;
  cursor: pointer;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-lite {
  border: 1px solid #cfe2ff;
  background: #fff;
  color: #21508a;
  border-radius: 10px;
  font-size: 12px;
  padding: 5px 10px;
  cursor: pointer;
}

.type-tabs {
  display: flex;
  gap: 8px;
  overflow: auto;
  padding-bottom: 4px;
}

.tab {
  border: 1px solid #d0e3ff;
  background: #fff;
  color: #29517f;
  border-radius: 999px;
  padding: 8px 14px;
  font-size: 12px;
  white-space: nowrap;
  cursor: pointer;
}

.tab.active {
  background: var(--brand);
  border-color: var(--brand);
  color: #fff;
}

.builder {
  background: var(--card);
  border-radius: var(--radius);
  padding: 12px;
  box-shadow: var(--shadow);
  display: grid;
  gap: 10px;
}

.builder-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.builder-row span {
  display: block;
  font-size: 12px;
  color: var(--sub);
}

.qty {
  display: flex;
  align-items: center;
  gap: 8px;
}

.empty {
  text-align: center;
  color: var(--sub);
  font-size: 12px;
}

.rules {
  margin-top: 10px;
  background: var(--card);
  border-radius: var(--radius);
  padding: 12px;
  box-shadow: var(--shadow);
}

.rules-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.rules-title {
  font-size: 13px;
  font-weight: 600;
}

.rules-status {
  font-size: 11px;
  border-radius: 999px;
  padding: 3px 8px;
  background: #ecf5ff;
  color: #245184;
}

.rules-status.warn {
  background: #fff4e5;
  color: #9c5a0b;
}

.rules-list {
  margin: 6px 0 0;
  padding-left: 18px;
  color: var(--sub);
  font-size: 12px;
}

.form {
  background: var(--card);
  border-radius: var(--radius);
  padding: 12px;
  box-shadow: var(--shadow);
  display: grid;
  gap: 10px;
}

label {
  display: grid;
  gap: 6px;
  font-size: 12px;
  color: var(--sub);
}

input {
  border: 1px solid #d7e3f4;
  border-radius: 10px;
  padding: 8px 10px;
  font-size: 13px;
}

.service {
  margin: 0;
  color: var(--sub);
  font-size: 13px;
}

.total {
  margin: 14px 16px 0;
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 12px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.total-price {
  font-size: 19px;
  font-weight: 700;
  color: var(--brand-dark);
}

.footer-link {
  margin: 14px 16px 0;
  font-size: 12px;
  color: #5c6f8d;
}

.footer-link a {
  color: #0a7cff;
}

.bottom-nav {
  position: fixed;
  left: 50%;
  transform: translateX(-50%);
  bottom: 0;
  width: 100%;
  max-width: 430px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  background: #fff;
  border-top: 1px solid #e2e9f3;
  font-size: 12px;
  color: #6b7c95;
}

.bottom-nav button {
  border: none;
  background: transparent;
  font: inherit;
  text-align: center;
  padding: 12px 6px;
  color: inherit;
  cursor: pointer;
}

.bottom-nav .active {
  color: var(--brand);
  font-weight: 600;
}
</style>
