// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import axios from 'axios'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueCookies from 'vue-cookies'
import store from './store'
import VueCoreVideoPlayer from 'vue-core-video-player'


Vue.use(VueCookies)
Vue.use(VueCoreVideoPlayer, {
  lang: 'zh-CN'
})

axios.defaults.withCredentials=true
// axios.defaults.baseURL= "http://localhost:8081"
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(ElementUI);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
