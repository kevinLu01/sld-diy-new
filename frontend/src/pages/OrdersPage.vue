<template>
  <main class="app">
    <header class="header">
      <div>
        <h1>订单与询价</h1>
        <p class="sub">展示最新询价与跟进状态</p>
      </div>
    </header>

    <section class="section card">
      <div class="section-head">
        <h2>询价列表</h2>
        <button class="btn" @click="loadInquiries">刷新</button>
      </div>
      <div v-if="!inquiries.length" class="empty">暂无询价记录</div>
      <div v-else class="list">
        <div v-for="item in inquiries" :key="item.id" class="list-item">
          <div>
            <strong>{{ item.customerName }}</strong>
            <div class="muted">{{ item.phone }}</div>
          </div>
          <div class="right">
            <div class="price">¥{{ formatPrice(item.calcPriceCents) }}</div>
            <span class="status">{{ item.status }}</span>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';

type Inquiry = {
  id: number;
  customerName: string;
  phone: string;
  calcPriceCents: number;
  status: string;
};

const API_BASE = import.meta.env.PROD ? '/api' : (import.meta.env.VITE_API_BASE ?? 'http://localhost:8081/api');
const inquiries = ref<Inquiry[]>([]);

const formatPrice = (cents: number) => (cents / 100).toLocaleString('zh-CN');

const loadInquiries = async () => {
  const res = await fetch(`${API_BASE}/admin/inquiries`);
  if (res.ok) {
    inquiries.value = await res.json();
  }
};

onMounted(loadInquiries);
</script>

<style scoped>
.app {
  max-width: 430px;
  margin: 0 auto;
  padding: 18px 16px 88px;
  font-family: "PingFang SC", "Microsoft YaHei", sans-serif;
  background: #f3f7fb;
  min-height: 100vh;
}

.header h1 {
  margin: 0;
  font-size: 18px;
}

.sub {
  margin: 6px 0 0;
  color: #5c6f8d;
  font-size: 12px;
}

.section {
  margin-top: 14px;
}

.card {
  background: #fff;
  border-radius: 16px;
  padding: 12px;
  box-shadow: 0 8px 24px rgba(22, 37, 61, 0.08);
}

.section-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.list {
  display: grid;
  gap: 10px;
}

.list-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #e5edf7;
  border-radius: 12px;
  padding: 10px 12px;
  background: #f8fbff;
}

.price {
  font-weight: 700;
  color: #045fc7;
}

.status {
  font-size: 11px;
  border-radius: 999px;
  padding: 3px 8px;
  background: #ecf5ff;
  color: #245184;
}

.btn {
  border: none;
  border-radius: 10px;
  background: #0a7cff;
  color: #fff;
  font-size: 12px;
  padding: 6px 10px;
  cursor: pointer;
}

.empty {
  text-align: center;
  color: #5c6f8d;
  font-size: 12px;
}

.right {
  text-align: right;
}

.muted {
  color: #5c6f8d;
  font-size: 12px;
}
</style>
