<template>
  <div class="type-tabs">
    <button
      v-for="tab in tabs"
      :key="tab"
      class="tab"
      :class="{ active: tab === active }"
      @click="$emit('change', tab)"
    >
      {{ tab }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  tabs: { type: Array, required: true },
  active: { type: String, required: true }
});

defineEmits(['change']);
</script>

<style scoped>
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

.tab.active { background: var(--brand); border-color: var(--brand); color: #fff; }
</style>
