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
      <div class="plans">
        <button
          v-for="pkg in filteredPackages"
          :key="pkg.id"
          class="plan-card"
          :class="{ active: pkg.id === selectedPackageId }"
          type="button"
          @click="applyPackage(pkg)"
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
            <span class="btn-lite">已套用</span>
          </div>
        </button>
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

const API_BASE = import.meta.env.PROD ? '/api' : (import.meta.env.VITE_API_BASE ?? 'http://localhost:8081/api');
const packages = ref<PackageSummary[]>([]);
const selectedPackageId = ref<number | null>(null);
const detail = ref<PackageDetail | null>(null);
const totalPriceCents = ref(0);
const selectedScene = ref('全部');
const submitting = ref(false);
const submitMessage = ref('');
const rulesResult = ref<RulesResponse | null>(null);
const loadError = ref('');

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
