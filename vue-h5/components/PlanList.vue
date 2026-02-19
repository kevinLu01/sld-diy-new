<template>
  <div class="plans">
    <PlanCard
      v-for="plan in plans"
      :key="plan.id"
      :plan="plan"
      :active="plan.id === activeId"
      @apply="$emit('apply', plan.id)"
    />
  </div>
</template>

<script setup>
import PlanCard from './PlanCard.vue';

defineProps({
  plans: { type: Array, required: true },
  activeId: { type: String, required: true }
});

defineEmits(['apply']);
</script>

<style scoped>
.plans {
  display: grid;
  gap: 10px;
}
</style>
