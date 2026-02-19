<template>
  <div class="section-head">
    <h2>{{ title }}</h2>
    <span v-if="hint" class="hint">{{ hint }}</span>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  hint: { type: String, default: '' }
});
</script>

<style scoped>
.section-head {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 10px;
}

h2 { margin: 0; font-size: 16px; }

.hint { font-size: 12px; color: var(--sub); }
</style>
