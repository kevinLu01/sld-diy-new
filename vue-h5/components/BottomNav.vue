<template>
  <nav class="bottom-nav">
    <div :class="{ active: active === '首页' }">首页</div>
    <div :class="{ active: active === '方案包' }">方案包</div>
    <div :class="{ active: active === '订单' }">订单</div>
    <div :class="{ active: active === '我的' }">我的</div>
  </nav>
</template>

<script setup>
defineProps({
  active: { type: String, default: '首页' }
});
</script>

<style scoped>
.bottom-nav {
  position: fixed;
  left: 50%;
  transform: translateX(-50%);
  bottom: 0;
  width: min(430px, 100%);
  background: #fff;
  border-top: 1px solid #dbe7f8;
  padding: 10px 14px 14px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 8px;
  text-align: center;
  font-size: 11px;
  color: #5f7497;
}

.active { color: var(--brand); font-weight: 700; }
</style>
