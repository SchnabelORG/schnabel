import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },

  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue'),
  },

  //Pharmacy
  {
    path: '/pharmacy',
    name: 'PharmacyPanel',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyPanel.vue'),
    children:[
      {
        path: '',
        name: 'PharmacyHome',
        component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyHome.vue'),
      },
      {
        path: 'dermatologist',
        name: 'PharmacyDermatologists',
        component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyDermatologists.vue'),
      },
      {
        path: 'pharmacist',
        name: 'PharmacyPharmacists',
        component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyPharmacists.vue'),
      },
      {
        path: 'drug',
        name: 'PharmacyDrugs',
        component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyDrugs.vue'),
      },
    ],
  },
  
  // User
  {
    path: '/user/about',
    name: 'UserAbout',
    component: () => import(/* webpackChunkName: "user" */ '../views/UserAbout.vue'),
  },

  // PSW
  {
    path: '/pswreg',
    name: 'PSWRegistration',
    component: () => import( /* webpackChunkName: "psw" */ '../views/PSWRegistration.vue'),
  },
  {
    path: '/pswreports',
    name: 'PSWUsageReport',
    component: () => import(/* webpackChunkName: "psw" */ '../views/PSWUsageReport.vue'),
  },

  // Pharmacist
  {
    path: "/pharmacist",
    name: 'PharmacistPanel',
    component: () => import(/* webpackChunkName: "pharmacist" */ '../views/PharmacistPanel.vue'),
    children:[
      {
        path: '',
        name: 'PharmacistHome',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/PharmacistHome.vue'),
      },
      {
        path: 'account',
        name: 'Account',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/Account.vue'),
      },
      {
        path: 'calendar',
        name: 'PharmacistCalendar',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/PharmacistCalendar.vue'),
      },
      {
        path: 'consultationReport',
        name: 'ConsultationReport',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/ConsultationReport.vue'),
      },
      {
        path: 'medicationReservations',
        name: 'MedicationReservations',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/MedicationReservations.vue'),
      },
    ],
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
