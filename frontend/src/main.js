import Vue from 'vue'
import Vuex from 'vuex'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import App from './App.vue'
import store from './store'

import VueLayers from 'vuelayers'
import 'vuelayers/lib/style.css'
import DatetimePicker from 'vuetify-datetime-picker'

import MainNavigation from './components/MainNavigation'
import SearchNavigation from './components/SearchNavigation'
import vuetify from './plugins/vuetify';
import Sidebar from './components/Sidebar'

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(Vuex)
Vue.use(VueLayers)
Vue.use(DatetimePicker)

/*const store = new Vuex.Store({
  state: {
    jws: "",
  },
})*/

Vue.component('main-navigation', MainNavigation)
Vue.component('sidebar', Sidebar, {
	props: ["title", "user"],
})
Vue.component('search-navigation', SearchNavigation)

axios.defaults.withCredentials = true;
axios.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT;

Vue.mixin({

  methods: {
    getAHeader: function() {
      return { 'Authorization': 'Bearer ' + localStorage.jws };
    },

    refreshToken: async function() {
      // TODO(Jovan): KEEP IN STORE!
      return axios.get("api/auth/refresh", { headers: { "Authorization" : "Bearer " + localStorage.jws}});
    },

    // Expected yy-mm-dd and HH:mm format
    getDateTimeFromString: function(dstr, tstr) {
      let dparts = dstr.split('-');
      let tparts = tstr.split(':');
      // -1 because js counts months from 0
      return new Date(dparts[0], dparts[1] - 1, dparts[2], tparts[0], tparts[1]);
    },
  },
});

new Vue({
  router: router,
  store: store,
  vuetify,
  render: h => h(App)
}).$mount('#app')