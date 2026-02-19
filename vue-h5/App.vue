<template>
  <main class="app">
    <header class="header">
      <div class="brand">
        <div>
          <h1>生利达冷冻设备 · DIY商城</h1>
          <p class="sub">为冷库、商超、餐饮场景快速拼装空调冷冻方案</p>
        </div>
        <span class="badge">移动端优先</span>
      </div>

      <HeroCard
        title="为冷库、商超、餐饮场景快速拼装空调冷冻方案"
        subtitle="支持按制冷量、能效等级、环境温度、安装空间进行选型，提供整机+辅材一站式下单。"
      />
    </header>

    <section class="section">
      <SectionHead title="1) 一键推荐方案包" hint="不懂选型？直接套用整套方案" />
      <PlanList
        :plans="plans"
        :active-id="activePlanId"
        @apply="handlePlanApply"
      />
    </section>

    <section class="section">
      <SectionHead title="2) 细化场景（可选）" hint="套用方案后还能继续DIY微调" />
      <SceneTabs
        :tabs="scenes"
        :active="activeScene"
        @change="activeScene = $event"
      />
      <SkuList :items="filteredSkus" @add="handleSkuAdd" />
    </section>

    <section class="section card">
      <SectionHead title="3) DIY配置清单" hint="自动校验兼容性" />
      <div class="selected-plan">当前已套用：{{ activePlan?.name || '-' }}</div>
      <DiyList :items="builderItems" />
      <PriceBar :total="totalPrice" />
    </section>

    <section class="section card">
      <SectionHead title="4) 提交询价" hint="填写项目资料，销售 2 小时内联系" />
      <InquiryForm
        :default-plan="activePlan?.name || ''"
        :total="totalPrice"
        @submit="handleInquirySubmit"
      />
    </section>

    <ServiceCard />
  </main>

  <BottomNav :active="'首页'" />
</template>

<script setup>
import { computed, reactive, ref } from 'vue';
import SectionHead from './components/SectionHead.vue';
import HeroCard from './components/HeroCard.vue';
import PlanList from './components/PlanList.vue';
import SceneTabs from './components/SceneTabs.vue';
import SkuList from './components/SkuList.vue';
import DiyList from './components/DiyList.vue';
import InquiryForm from './components/InquiryForm.vue';
import PriceBar from './components/PriceBar.vue';
import ServiceCard from './components/ServiceCard.vue';
import BottomNav from './components/BottomNav.vue';

const plans = [
  {
    id: 'plan-basic',
    name: '冷库标准版（20~30㎡）',
    level: '入门型',
    desc: '适合便利店后仓、小型食材冷藏，强调稳定与性价比。',
    price: 26480,
    points: ['SLD-LK35 压缩机组 + 吊顶冷风机', '基础铜管包 15m + 标准控制箱', '上门安装调试 + 首年巡检']
  },
  {
    id: 'plan-market',
    name: '商超节能版（40~60㎡）',
    level: '热销型',
    desc: '针对商超陈列和频繁开门，优先控制电耗和温度波动。',
    price: 32800,
    points: ['SLD-SY28 双机并联 + 智能化霜', '高效换热冷风机 + 防潮阀件包', '远程监控模块 + 安装调试']
  },
  {
    id: 'plan-pro',
    name: '冷链高配版（医药/生鲜）',
    level: '旗舰型',
    desc: '强调温控精度和连续运行稳定性，适合高标准冷链项目。',
    price: 46900,
    points: ['双回路机组 + 备用电控保护', '数据记录仪 + 超温告警联动', '18个月核心质保 + 快速维保']
  }
];

const scenes = ['冷库工程', '商用中央空调', '食品加工车间', '医药冷链'];
const activeScene = ref(scenes[0]);
const activePlanId = ref(plans[0].id);

const skus = [
  {
    id: 'sku-1',
    scene: '冷库工程',
    name: 'SLD-LK35 低温冷库机组',
    tag: '推荐',
    specs: ['35kW 制冷量', '-25℃低温', 'R404A'],
    price: 18900
  },
  {
    id: 'sku-2',
    scene: '冷库工程',
    name: '吊顶冷风机 12000m³/h',
    tag: '标配',
    specs: ['低噪节能', '防腐蚀涂层', '配套控制箱'],
    price: 6480
  },
  {
    id: 'sku-3',
    scene: '商用中央空调',
    name: 'SLD-CA28 商用机组',
    tag: '热销',
    specs: ['28kW 制冷量', '智能化霜', 'R410A'],
    price: 21400
  }
];

const builderItems = reactive([
  { id: 'b1', name: '压缩机组', detail: 'SLD-LK35 · 380V · 三相', status: 'ok', label: '兼容' },
  { id: 'b2', name: '冷风机', detail: '风量 12000m³/h · 吊顶式', status: 'ok', label: '兼容' },
  { id: 'b3', name: '膨胀阀 + 干燥过滤器', detail: '建议升级防潮版（沿海项目）', status: 'warn', label: '建议' },
  { id: 'b4', name: '安装服务', detail: '含铜管焊接 / 抽真空 / 调试', status: 'ok', label: '已选' }
]);

const activePlan = computed(() => plans.find(plan => plan.id === activePlanId.value));
const totalPrice = computed(() => activePlan.value?.price || 0);

const handlePlanApply = (planId) => {
  activePlanId.value = planId;
};

const filteredSkus = computed(() => skus.filter(item => item.scene === activeScene.value));

const handleSkuAdd = (sku) => {
  builderItems.push({
    id: `b-${sku.id}`,
    name: sku.name,
    detail: sku.specs.join(' · '),
    status: 'ok',
    label: '已加入'
  });
};

const handleInquirySubmit = (payload) => {
  const submitPayload = {
    ...payload,
    planId: activePlanId.value,
    planName: activePlan.value?.name || '',
    items: builderItems,
    total: totalPrice.value
  };
  console.log('Inquiry submit', submitPayload);
};
</script>

<style>
:root {
  --brand: #0a7cff;
  --brand-dark: #045fc7;
  --bg: #f3f7fb;
  --card: #ffffff;
  --text: #16253d;
  --sub: #5c6f8d;
  --ok: #1f9d57;
  --warn: #f59e0b;
  --radius: 16px;
  --shadow: 0 8px 24px rgba(22, 37, 61, 0.08);
}

* { box-sizing: border-box; }

body {
  margin: 0;
  font-family: "PingFang SC", "Microsoft YaHei", "Noto Sans SC", sans-serif;
  background: var(--bg);
  color: var(--text);
  line-height: 1.5;
}

#app {
  max-width: 430px;
  margin: 0 auto;
}

.app {
  min-height: 100vh;
  background: linear-gradient(180deg, #e9f3ff 0%, #f6faff 130px, var(--bg) 260px);
  padding-bottom: 88px;
}

.header { padding: 18px 16px 12px; }

.brand {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}

.brand h1 { margin: 0; font-size: 18px; }

.sub { margin: 6px 0 0; font-size: 12px; color: var(--sub); }

.badge {
  background: #eaf3ff;
  color: var(--brand-dark);
  border: 1px solid #c7ddff;
  border-radius: 999px;
  padding: 4px 10px;
  font-size: 12px;
  white-space: nowrap;
}

.section { margin: 14px 16px 0; }

.card {
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 12px;
}

.selected-plan {
  background: #f3f8ff;
  border: 1px dashed #aacdff;
  border-radius: 10px;
  padding: 8px 10px;
  font-size: 12px;
  color: #36577f;
  margin-bottom: 10px;
}
</style>
