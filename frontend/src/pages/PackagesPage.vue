<template>
  <main class="app">
    <header class="header">
      <div>
        <h1>方案包库</h1>
        <p class="sub">按场景查看并套用方案包</p>
      </div>
    </header>

    <section class="section">
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
    </section>

    <section class="section">
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
            <span class="btn-lite">查看详情</span>
          </div>
        </button>
      </div>
    </section>

    <section v-if="detail" class="section card">
      <div class="section-head">
        <h2>方案包详情</h2>
        <span class="plan-level">{{ detail.scene }}</span>
      </div>
      <p class="sub">{{ detail.summary }}</p>
      <ul class="detail-list">
        <li v-for="item in detail.items" :key="item.skuId">
          {{ item.skuName }} × {{ item.quantity }}
          <span class="muted">¥{{ formatPrice(item.basePriceCents) }}</span>
        </li>
      </ul>
    </section>
  </main>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';

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

const API_BASE = import.meta.env.PROD ? '/api' : (import.meta.env.VITE_API_BASE ?? 'http://localhost:8081/api');
const packages = ref<PackageSummary[]>([]);
const selectedPackageId = ref<number | null>(null);
const detail = ref<PackageDetail | null>(null);
const selectedScene = ref('全部');

const scenes = computed(() => {
  const unique = new Set(packages.value.map((pkg) => pkg.scene));
  return ['全部', ...Array.from(unique)];
});

const filteredPackages = computed(() => {
  if (selectedScene.value === '全部') return packages.value;
  return packages.value.filter((pkg) => pkg.scene === selectedScene.value);
});

const formatPrice = (cents: number) => (cents / 100).toLocaleString('zh-CN');

const loadPackages = async () => {
  const res = await fetch(`${API_BASE}/packages`);
  if (res.ok) {
    packages.value = await res.json();
  }
};

const applyPackage = async (pkg: PackageSummary) => {
  selectedPackageId.value = pkg.id;
  const res = await fetch(`${API_BASE}/packages/${pkg.id}`);
  if (res.ok) {
    detail.value = await res.json();
  }
};

const selectScene = (scene: string) => {
  selectedScene.value = scene;
};

onMounted(loadPackages);
</script>

<style scoped>
.app {
  max-width: 430px;
  margin: 0 auto;
  padding: 18px 16px 88px;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  background: #f3f7fb;
  min-height: 100vh;
}

.header h1 {
  margin: 0;
  font-size: 18px;
}

.sub {
  margin: 6px 0 0;
  color: #5c6f8d;
  font-size: 12px;
}

.section {
  margin-top: 14px;
}

.type-tabs {
  display: flex;
  gap: 8px;
  overflow: auto;
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
  background: #0a7cff;
  border-color: #0a7cff;
  color: #fff;
}

.plans {
  display: grid;
  gap: 10px;
}

.plan-card {
  width: 100%;
  text-align: left;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(22, 37, 61, 0.08);
  padding: 12px;
  border: 1px solid transparent;
  cursor: pointer;
}

.plan-card.active {
  border-color: #9cc6ff;
}

.plan-top {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  align-items: flex-start;
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
  color: #5c6f8d;
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
  color: #045fc7;
}

.btn-lite {
  border: 1px solid #cfe2ff;
  background: #fff;
  color: #21508a;
  border-radius: 10px;
  font-size: 12px;
  padding: 7px 11px;
}

.card {
  background: #fff;
  border-radius: 16px;
  padding: 12px;
  box-shadow: 0 8px 24px rgba(22, 37, 61, 0.08);
}

.detail-list {
  margin: 8px 0 0;
  padding-left: 18px;
  color: #415d84;
  font-size: 12px;
  display: grid;
  gap: 6px;
}

.muted {
  color: #5c6f8d;
  margin-left: 6px;
}
</style>
