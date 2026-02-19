<template>
  <article class="card">
    <div class="card-top">
      <h3>{{ sku.name }}</h3>
      <span class="tag">{{ sku.tag }}</span>
    </div>
    <div class="specs">
      <span v-for="spec in sku.specs" :key="spec" class="spec">{{ spec }}</span>
    </div>
    <div class="card-footer">
      <div class="price">¥{{ formatPrice(sku.price) }}</div>
      <button class="btn-lite" @click="$emit('add')">加入DIY方案</button>
    </div>
  </article>
</template>

<script setup>
defineProps({
  sku: { type: Object, required: true }
});

defineEmits(['add']);

const formatPrice = (value) => Number(value || 0).toLocaleString('zh-CN');
</script>

<style scoped>
.card {
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 12px;
}

.card-top {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 10px;
}

h3 { margin: 0; font-size: 14px; line-height: 1.35; }

.tag {
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 999px;
  background: #eef4ff;
  color: #355989;
  white-space: nowrap;
}

.specs {
  margin: 8px 0 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.spec {
  font-size: 11px;
  color: #466189;
  background: #f5f8ff;
  border: 1px solid #e0ebff;
  border-radius: 8px;
  padding: 2px 8px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price { font-weight: 700; color: var(--brand-dark); font-size: 16px; }

.btn-lite {
  border: 1px solid #cfe2ff;
  background: #fff;
  color: #21508a;
  border-radius: 10px;
  font-size: 12px;
  padding: 7px 11px;
  cursor: pointer;
}
</style>
