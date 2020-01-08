import Vue from 'vue'
import VueRouter from 'vue-router'
import Tournament from '../views/Tournament.vue'
import Top10 from '../views/Top10.vue'
import Teams from '../views/Teams.vue'
import Team from '../views/Team.vue'
import Referees from '../views/Referees.vue'
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
  path: '/komandas',
  component: Teams,
  children: [{
    path: ':team',
    component: Team
  }]
}, {
  path: '/tiesnesi',
  component: Referees
}, {
  path: '/ielade',
  component: Upload
}];

const router = new VueRouter({
  routes
});

export default router
