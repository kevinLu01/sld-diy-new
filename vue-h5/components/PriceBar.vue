<template>
  <div class="total">
    <div>
      <small>预估总价（含税）</small>
      <div class="total-price">¥{{ formatPrice(total) }}</div>
    </div>
    <button class="btn">提交询价</button>
  </div>
</template>

<script setup>
defineProps({
  total: { type: Number, required: true }
});

const formatPrice = (value) => Number(value || 0).toLocaleString('zh-CN');
</script>

<style scoped>
.total {
  margin-top: 8px;
  background: #0c1a2f;
  color: #fff;
  border-radius: 12px;
  padding: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

small { color: #9eb4d3; }

.total-price { font-size: 19px; font-weight: 700; }

.btn {
  border: none;
  border-radius: 10px;
  background: var(--brand);
  color: #fff;
  font-size: 12px;
  padding: 9px 14px;
  cursor: pointer;
}
</style>
