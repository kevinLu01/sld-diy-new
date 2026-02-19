<template>
  <article class="plan-card" :class="{ active }">
    <div class="plan-top">
      <h3>{{ plan.name }}</h3>
      <span class="plan-level">{{ plan.level }}</span>
    </div>
    <p class="plan-desc">{{ plan.desc }}</p>
    <ul class="plan-points">
      <li v-for="point in plan.points" :key="point">{{ point }}</li>
    </ul>
    <div class="plan-footer">
      <div class="plan-price">¥{{ formatPrice(plan.price) }}</div>
      <button class="btn" @click="$emit('apply')">一键套用</button>
    </div>
  </article>
</template>

<script setup>
defineProps({
  plan: { type: Object, required: true },
  active: { type: Boolean, default: false }
});

defineEmits(['apply']);

const formatPrice = (value) => Number(value || 0).toLocaleString('zh-CN');
</script>

<style scoped>
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

.plan-top h3 { margin: 0; font-size: 14px; }

.plan-level {
  font-size: 11px;
  border-radius: 999px;
  padding: 3px 8px;
  background: #ecf5ff;
  color: #245184;
  white-space: nowrap;
}

.plan-desc { margin: 8px 0; color: var(--sub); font-size: 12px; }

.plan-points {
  margin: 0;
  padding-left: 18px;
  color: #415d84;
  font-size: 12px;
  display: grid;
  gap: 3px;
}

.plan-footer {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.plan-price { font-size: 16px; font-weight: 700; color: var(--brand-dark); }

.btn {
  border: none;
  border-radius: 10px;
  background: var(--brand);
  color: #fff;
  font-size: 12px;
  padding: 7px 11px;
  cursor: pointer;
}
</style>
