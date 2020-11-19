import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PSWRegistration from '../views/PSWRegistration.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/pswreg',
    name: 'PSWRegistration',
    component: PSWRegistration,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
