import Vue from 'vue'
import VueRouter from 'vue-router'
import Table from '../views/Table.vue'
import Upload from '../views/Upload'

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/tabula'
  },
  {
    path: '/tabula',
    component: Table
  },
  {
    path: '/ielade',
    component: Upload
  }
];

const router = new VueRouter({
  routes
});

export default router
