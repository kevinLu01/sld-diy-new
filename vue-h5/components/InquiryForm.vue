<template>
  <form class="form" @submit.prevent="submit">
    <div class="form-row">
      <label>联系人</label>
      <input v-model.trim="form.contact" type="text" placeholder="请输入联系人姓名" />
    </div>
    <div class="form-row">
      <label>联系电话</label>
      <input v-model.trim="form.phone" type="tel" placeholder="11 位手机号" />
    </div>
    <div class="form-row">
      <label>项目地址</label>
      <input v-model.trim="form.address" type="text" placeholder="城市 + 具体地址" />
    </div>
    <div class="form-row">
      <label>项目面积</label>
      <input v-model.trim="form.area" type="text" placeholder="例如 30㎡" />
    </div>
    <div class="form-row">
      <label>期望工期</label>
      <select v-model="form.schedule">
        <option disabled value="">请选择</option>
        <option>7 天内</option>
        <option>2 周内</option>
        <option>1 个月内</option>
        <option>暂不确定</option>
      </select>
    </div>
    <div class="form-row">
      <label>已选方案</label>
      <input :value="defaultPlan" type="text" readonly />
    </div>

    <div class="summary">
      <span>预计总价</span>
      <strong>¥{{ formatPrice(total) }}</strong>
    </div>

    <button class="submit" type="submit">提交询价</button>
  </form>
</template>

<script setup>
import { reactive } from 'vue';

defineProps({
  defaultPlan: { type: String, default: '' },
  total: { type: Number, default: 0 }
});

const emit = defineEmits(['submit']);

const form = reactive({
  contact: '',
  phone: '',
  address: '',
  area: '',
  schedule: ''
});

const submit = () => {
  emit('submit', {
    contact: form.contact,
    phone: form.phone,
    address: form.address,
    area: form.area,
    schedule: form.schedule
  });
};

const formatPrice = (value) => Number(value || 0).toLocaleString('zh-CN');
</script>

<style scoped>
.form {
  display: grid;
  gap: 10px;
}

.form-row {
  display: grid;
  gap: 6px;
}

label { font-size: 12px; color: var(--sub); }

input, select {
  border: 1px solid #dce7f7;
  border-radius: 10px;
  padding: 9px 10px;
  font-size: 12px;
  font-family: inherit;
}

input[readonly] {
  background: #f4f8ff;
  color: #4b668f;
}

.summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #f0f5ff;
  border-radius: 10px;
  padding: 10px;
  font-size: 12px;
}

.summary strong { font-size: 15px; color: var(--brand-dark); }

.submit {
  border: none;
  background: var(--brand);
  color: #fff;
  border-radius: 10px;
  padding: 10px;
  font-size: 13px;
  cursor: pointer;
}
</style>
