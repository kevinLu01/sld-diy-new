<template>
  <div class="builder-row">
    <div>
      <strong>{{ item.name }}</strong>
      <span>{{ item.detail }}</span>
    </div>
    <span class="status" :class="item.status">{{ item.label }}</span>
  </div>
</template>

<script setup>
defineProps({
  item: { type: Object, required: true }
});
</script>

<style scoped>
.builder-row {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 10px;
  align-items: center;
  background: #f7fbff;
  border: 1px solid #e2efff;
  border-radius: 12px;
  padding: 10px;
}

strong { display: block; font-size: 13px; }
span { font-size: 11px; color: var(--sub); }

.status {
  font-size: 11px;
  border-radius: 999px;
  padding: 3px 8px;
}

.ok { background: #e8f9ef; color: var(--ok); }
.warn { background: #fff6e7; color: #a86b00; }
</style>
