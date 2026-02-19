<template>
  <div class="builder">
    <DiyItem v-for="item in items" :key="item.id" :item="item" />
  </div>
</template>

<script setup>
import DiyItem from './DiyItem.vue';

defineProps({
  items: { type: Array, required: true }
});
</script>

<style scoped>
.builder {
  margin-top: 12px;
  display: grid;
  gap: 8px;
}
</style>
