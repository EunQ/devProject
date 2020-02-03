import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import SearchList from '../components/SearchList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path : '/searchlist/:keyword',
    name : 'searchlist',
    component : SearchList
  },
  {
    path : '/detailpage/:data',
    name : 'detailpage',
    component : ()=>import('../components/DetailPage.vue')
  }
  //github
  ,
  {
    path : '/github',
    name : 'github',
    component : Home
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
