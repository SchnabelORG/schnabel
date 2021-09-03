import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import axios from 'axios'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    beforeEnter: (to, from, next) => {
      if(localStorage.jws) {
        axios.get('api/auth/role', {headers: {"Authorization": "Bearer " + JSON.parse(localStorage.getItem('user'))}})
        .then(r => {
          if(r.data == 'ROLE_PATIENT') {
            next({name: 'UserHome'});
          } else if (r.data == 'ROLE_ADMIN') {
            next({name: 'SystemAdminHome'});
          } else if (r.data == 'ROLE_PHARMACIST') {
            next({name: 'PharmacistHome'});
          } else if (r.data == 'ROLE_DERMATOLOGIST') {
            next({name: 'DermatologistHome'}); 
          } else if (r.data == 'ROLE_SUPPLIER') {
            next({name: 'SupplierHome'});
          } else if (r.data == 'ROLE_PHARMACYADMIN') {
            next({name: 'PharmacyAdminHome'});
          }else {
            next({name: 'Home'});
          }
        })
        .catch(() => next({name: 'Home'}));
      } else {
        next({name: 'Home'});
      }
    },
  },

  {
    path: '/index',
    name: 'Home',
    component: Home,
  },

  {
    path: '/logout',
    name: 'Logout',
    beforeEnter: (to, from, next) => {
      localStorage.removeItem('jws');
      next({name:'Home'});
    },
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


  // NOTE(Jovan): Redirect based on role
  {
    path: '/redirect',
    beforeEnter: (to, from, next) => {
      axios.get('api/auth/role', {headers: {"Authorization": "Bearer " + JSON.parse(localStorage.getItem('user'))}})
        .then(r => {
          if(r.data == 'ROLE_PATIENT') {
            next({name: 'UserHome'});
          } else if (r.data == 'ROLE_ADMIN') {
            next({name: 'SystemAdminHome'});
          } else if (r.data == 'ROLE_PHARMACIST') {
            next({name: 'PharmacistHome'});
          } else if (r.data == 'ROLE_DERMATOLOGIST') {
            next({name: 'DermatologistHome'}); 
          } else if (r.data == 'ROLE_SUPPLIER') {
            next({name: 'SupplierHome'});
          } else if (r.data == 'ROLE_PHARMACYADMIN') {
            next({name: 'PharmacyAdminHome'});
          }else {
            next({name: 'Home'});
          }
        })
        .catch(() => next({name: 'Home'}));
    },
  },

  {
    path:'/drugsearch',
    name: 'DrugSearch',
    component: () => import( /* webpackChunkName: "drugsearch" */ '../views/DrugSearch.vue'),
  },

  {
    path:'/ereceipt',
    name: 'EReceipt',
    component: () => import( /* webpackChunkName: "drugsearch" */ '../views/EReceipt.vue'),
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
  {
    path: '/pharmacysearch/:name',
    name: 'PharmacySearch',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacySearch.vue'),
  },

  {
    path: '/dermatologistsearch',
    name: 'DermatologistSearch',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/DermatologistSearch.vue'),
  },

  {
    path: '/pharmacistsearch',
    name: 'PharmacistSearch',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacistSearch.vue'),
  },

  //Pharmacy
  {
    path: '/pharmacy/:id',
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
      {
        path: 'freedermatologistappointments',
        name: 'PharmacyFreeDermApp',
        component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyFreeDermApp.vue'),
      },
    ],
  },

  {
    path: '/a',
    name: 'a',
    component: () => import(/* webpackChunkName: "pharmacy" */ '../views/PharmacyAdminAppointments.vue'),
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
      {
        path: 'vacations',
        name: 'PharmacistVacation',
        component: () => import(/* webpackChunkName: "pharmacist" */ '../views/PharmacistVacation.vue'),
      },
    ],
  },

  {
    path: '/defaultpass',
    name: 'ChangeDefaultPassword',
    component: () => import(/* webpackChunkName: "employee" */ '../views/ChangeDefaultPassword.vue'),
  },

  //Dermatologist
  {
    path: "/dermatologist",
    name: 'DermatologistPanel',
    component: () => import(/* webpackChunkName: "dermatologist" */ '../views/DermatologistPanel.vue'),
    children:[
      {
        path: '',
        name: 'DermatologistHome',
        component: () => import(/* webpackChunkName: "dermatologist" */ '../views/DermatologistHome.vue'),
      },
      {
        path: 'dermatologistacc',
        name: 'DermatologistAccount',
        component: () => import(/* webpackChunkName: "dermatologist" */ '../views/DermatologistAccount.vue'),
      },
      {
        path: 'calendar',
        name: 'DermatologistCalendar',
        component: () => import(/* webpackChunkName: "dermatologist" */ '../views/DermatologistCalendar.vue'),
      },
      {
        path: 'appointmentReport',
        name: 'AppointmentReport',
        component: () => import(/* webpackChunkName: "dermatologist" */ '../views/AppointmentReport.vue'),
      },
      {
        path: 'vacations',
        name: 'DermatologistVacation',
        component: () => import(/* webpackChunkName: "dermatologist" */ '../views/DermatologistVacation.vue'),
      },
    ],
  },

//System Admin

  {
    path: '/systemadmin',
    name: 'SystemAdminPanel',
    component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminPanel.vue'),
    children:[
      {
        path: '',
        name: 'SystemAdminHome',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminHome.vue'),
      },
      {
        path: 'pharmacies',
        name: 'SystemAdminPharmacies',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminPharmacies.vue'),
      },
      {
        path: 'pharmacies/register',
        name: 'SystemAdminRegisterPharmacy',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminRegisterPharmacy.vue'),
      },
      {
        path: 'dermatologists',
        name: 'SystemAdminDermatologists',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminDermatologists.vue'),
      },
      {
        path: 'sytemadmin',
        name: 'SystemAdminSupplier',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminSupplier.vue'),
      },
      {
        path: 'drugs',
        name: 'SystemAdminDrugs',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminDrugs.vue'),
      },
      {
        path: 'suppliers',
        name: 'SystemAdminSupplier',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminSupplier.vue'),
      },
      {
        path: 'changepass',
        name: 'SystemAdminChangePassword',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminChangePassword.vue'),
      },
      {
        path: 'register',
        name: 'SystemAdminRegistration',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SystemAdminRegistration.vue'),
      },
    ],
  },


  //Supplier

  {
    path: '/supplier',
    name: 'SupplierPanel',
    component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierPanel.vue'),
    children: [
      {
        path: '',
        name: 'SupplierHome',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierHome.vue'),
      },
      {
        path: 'changepass',
        name: 'SupplierChangePassword',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierChangePassword.vue'),
      },
      {
        path: 'orders',
        name: 'SupplierOrders',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierOrders.vue'),
      },
      {
        path: 'offers',
        name: 'SupplierOffers',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierOffers.vue'),
      },
      {
        path: 'account',
        name: 'SupplierAccount',
        component: () => import(/* webpackChunkName: "systemadmin" */ '../views/SupplierAccount.vue'),
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
      {
        path: 'pricelist',
        name: 'PriceList',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PriceList.vue'),
      },
      {
        path: 'pharmacistsearch',
        name: 'PharmacyAdminPharmacistSearch',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminPharmacistSearch.vue'),
      },
      {
        path: 'dermatologistsearch',
        name: 'PharmacyAdminDermatologistSearch',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminDermatologistSearch.vue'),
      },
      {
        path: 'freedermatologistappointment',
        name: 'PharmacyAdminFreeAppointments',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminFreeAppointments.vue'),
      },
      {
        path: 'appointmentsreport',
        name: 'PharmacyAdminAppointments',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminAppointments.vue'),
      },
      {
        path: 'drugusage',
        name: 'PharmacyAdminDrugUsage',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminDrugUsage.vue'),
      },
      {
        path: 'pharmacyincome',
        name: 'PharmacyAdminIncome',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminIncome.vue'),
      },
      {
        path: 'availabilityrequests',
        name: 'PharmacyAdminAvailabilityRequests',
        component: () => import(/* webpackChunkName: "pharmacyadmin" */ '../views/PharmacyAdminAvailabilityRequests.vue'),
      },
    ],
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/home', '/index', '/signup', '/email/activate/:token', '/', '/drugsearch'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('user');

  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/login');
  } else {
    next();
  }
});

export default router
