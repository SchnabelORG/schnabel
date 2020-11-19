import Vue from 'vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import App from './App.vue'
import MainNavigation from './components/MainNavigation'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.component('main-navigation', MainNavigation)

new Vue({
  router: router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
