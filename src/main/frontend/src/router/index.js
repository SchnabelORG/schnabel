import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import PSWRegistration from '../views/PSWRegistration.vue'
import PSWUsageReport from '../views/PSWUsageReport.vue'
import PharmacistPanel from '../views/PharmacistPanel.vue'
import Account from '../views/Account.vue'
import PharmacistHome from '../views/PharmacistHome.vue'
import PharmacistCalendar from '../views/PharmacistCalendar.vue'
import ConsultationReport from '../views/ConsultationReport.vue'

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
  },
  {
    path: '/pswreports',
    name: 'PSWUsageReport',
    component: PSWUsageReport,
  },
  {
    path: "/pharmacist",
    name: 'PharmacistPanel',
    component: PharmacistPanel,
    children:[
      {
        path: '',
        name: 'PharmacistHome',
        component: PharmacistHome,
      },
      {
        path: 'account',
        name: 'Account',
        component: Account,
      },
      {
        path: 'calendar',
        name: 'PharmacistCalendar',
        component: PharmacistCalendar,
      },
      {
        path: 'consultationReport',
        name: 'ConsultationReport',
        component: ConsultationReport,
      },
    ],
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
