import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
      themes: {
          light: {
              primary: "#fbecdd",
              secondary: "#223a66",
              accent: "#e12454",
              info: "#8692a8",
          },
      },
  },
  icons: {
    iconfont: 'fa4',
  },
});
