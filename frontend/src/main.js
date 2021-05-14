import Vue from 'vue'
import Vuex from 'vuex'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import App from './App.vue'
import MainNavigation from './components/MainNavigation'
import vuetify from './plugins/vuetify';
import Sidebar from './components/Sidebar'

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    jws: "",
  },
})

Vue.component('main-navigation', MainNavigation)
Vue.component('sidebar', Sidebar, {
	props: ["title", "user"],
})

axios.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT;

new Vue({
  router: router,
  store: store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
