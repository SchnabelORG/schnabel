import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import axios from 'axios'

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
  {
    path: '/signup',
    name: 'SignUp',
    component: () => import(/* webpackChunkName: "signup" */ '../views/SignUp.vue'),
  },
  // Email
  {
    path: '/email/activate/:token',
    beforeEnter: (to, from, next) => {
      let token = to.params["token"]
      axios.put("api/email/activate/" + token)
        .finally(function(){
          next({ name: "Home"});
        });
    },
  },

  {
    path:'/drugsearch',
    name: 'DrugSearch',
    component: () => import( /* webpackChunkName: "drugsearch" */ '../views/DrugSearch.vue'),
  },

  {
    path: '/consult',
    name: 'ScheduleConsult',
    component: () => import( /* webpackChunkName: "makeappointment" */ '../views/ScheduleConsult.vue'),
  },

  {
    path: '/rating',
    name: 'Rating',
    component: () => import( /* webpackChunkName: "rating" */ '../views/Rating.vue'),
  },

  {
    path: '/dermappointment/:pharmacyname',
    beforeEnter: (to, from, next) => {
      // Validate pharmacy exists
      let pharmacyName = to.params["pharmacyname"]
      axios.get("api/pharmacy/check/" + pharmacyName)
        .then(r => {
          console.log(r);
          next();
        })
        .catch(r => {
          console.log(r);
          next({name: 'Home'});
        });
    },
    component: () => import(/* webpackChunkName: "makeappointment" */  '../views/DermAppointment.vue'),
  },

  // PharmacySearch

  {
    path: '/pharmacysearch',
    name: 'PharmacySearch',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacySearch.vue'),
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
    path: '/user',
    name: 'UserHome',
    component: () => import(/* webpackChunkName: "user" */ '../views/UserHome.vue'),
  },
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
        path: 'pharmacistacc',
        name: 'PharmacistAccount',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/PharmacistAccount.vue'),
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

  //PharmacyAdmin
  {
    path: '/pharmacyadmin',
    name: 'PharmacyAdminPanel',
    component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminPanel.vue'),
    children:[
      {
        path: '',
        name: 'PharmacyAdminHome',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminHome.vue'),
      },
      {
        path: 'promotion',
        name: 'Promotions',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/Promotions.vue'),
      },
      {
        path: 'account',
        name: 'PharmacyAdminAccount',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminAccount.vue'),
      },
      {
        path: 'vacation',
        name: 'Vacations',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/Vacations.vue'),
      },
      {
        path: 'order',
        name: 'Orders',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/Orders.vue'),
      },
      {
        path: 'makeorder',
        name: 'MakeOrder',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/MakeOrder.vue'),
      },
      {
        path: 'drug',
        name: 'PharmacyAdminDrugs',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminDrugs.vue'),
      },
      {
        path: 'dermatologist',
        name: 'PharmacyAdminDermatologists',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminDermatologists.vue'),
      },
      {
        path: 'pharmacist',
        name: 'PharmacyAdminPharmacists',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminPharmacists.vue'),
      },
      {
        path: 'defineappointment',
        name: 'PharmacyAdminDefineAppointment',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminDefineAppointment.vue'),
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
