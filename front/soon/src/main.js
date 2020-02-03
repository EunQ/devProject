import Vue from 'vue'
import App from './App.vue'
import 'vuetify/dist/vuetify.min.css'
import router from './router'
import store from './store'
import Vuetify from 'vuetify/lib'
import 'vuetify/dist/vuetify.min.css'
//import vuetify from './plugins/vuetify';
import VueSimplemde from 'vue-simplemde'
import 'simplemde/dist/simplemde.min.css'
import 'font-awesome/css/font-awesome.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
Vue.use(Vuetify)
Vue.config.productionTip = false
Vue.use(VueSimplemde)
new Vue({
  router,
  store,
  vuetify: new Vuetify({
    iconfont: 'fa',
  }),
  render: h => h(App)
}).$mount('#app')
