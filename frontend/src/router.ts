import { createRouter, createWebHistory } from 'vue-router';
import HomePage from './pages/HomePage.vue';
import PackagesPage from './pages/PackagesPage.vue';
import OrdersPage from './pages/OrdersPage.vue';
import ProfilePage from './pages/ProfilePage.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'home', component: HomePage },
    { path: '/packages', name: 'packages', component: PackagesPage },
    { path: '/orders', name: 'orders', component: OrdersPage },
    { path: '/me', name: 'me', component: ProfilePage }
  ]
});

export default router;
