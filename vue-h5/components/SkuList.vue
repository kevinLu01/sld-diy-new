<template>
  <div class="products">
    <SkuCard
      v-for="item in items"
      :key="item.id"
      :sku="item"
      @add="$emit('add', item)"
    />
  </div>
</template>

<script setup>
import SkuCard from './SkuCard.vue';

defineProps({
  items: { type: Array, required: true }
});

defineEmits(['add']);
</script>

<style scoped>
.products {
  display: grid;
  gap: 10px;
  margin-top: 10px;
}
</style>
