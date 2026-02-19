<template>
  <section class="hero">
    <h2 class="hero-title">{{ title }}</h2>
    <p>{{ subtitle }}</p>
    <div class="quick-grid">
      <div class="quick-item">📦 整机组合</div>
      <div class="quick-item">🧰 辅材配件</div>
      <div class="quick-item">🚚 送装一体</div>
    </div>
  </section>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  subtitle: { type: String, required: true }
});
</script>

<style scoped>
.hero {
  margin-top: 14px;
  background: var(--card);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 14px;
}

.hero-title { margin: 0 0 8px; font-size: 17px; line-height: 1.3; }

p { margin: 0; color: var(--sub); font-size: 13px; }

.quick-grid {
  margin-top: 12px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 10px;
}

.quick-item {
  background: #f4f9ff;
  border-radius: 12px;
  padding: 10px 6px;
  text-align: center;
  font-size: 12px;
  color: #38557f;
}
</style>
