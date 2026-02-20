<template>
  <div class="shell">
    <router-view />

    <nav class="bottom-nav">
      <router-link
        v-for="item in navItems"
        :key="item.path"
        :to="item.path"
        class="nav-item"
      >
        {{ item.label }}
      </router-link>
    </nav>
  </div>
</template>

<script setup lang="ts">
const navItems = [
  { label: '首页', path: '/' },
  { label: '方案包', path: '/packages' },
  { label: '订单', path: '/orders' },
  { label: '我的', path: '/me' }
];
</script>

<style scoped>
.shell {
  min-height: 100vh;
  background: #f3f7fb;
}

.bottom-nav {
  position: fixed;
  left: 50%;
  transform: translateX(-50%);
  bottom: 0;
  width: 100%;
  max-width: 430px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  background: #fff;
  border-top: 1px solid #e2e9f3;
  font-size: 12px;
  color: #6b7c95;
}

.nav-item {
  text-align: center;
  padding: 12px 6px;
  text-decoration: none;
  color: inherit;
}

.nav-item.router-link-active {
  color: #0a7cff;
  font-weight: 600;
}
</style>
