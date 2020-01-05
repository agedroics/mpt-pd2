import Vue from 'vue'
import VueRouter from 'vue-router'
import Tournament from '../views/Tournament.vue'
import Top10 from '../views/Top10.vue'
import Upload from '../views/Upload'

Vue.use(VueRouter);

const routes = [{
  path: '/',
  redirect: '/turnirs'
}, {
  path: '/turnirs',
  component: Tournament
}, {
  path: '/top10',
  component: Top10
}, {
  path: '/ielade',
  component: Upload
}];

const router = new VueRouter({
  routes
});

export default router
