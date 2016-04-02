import Vue from 'vue';
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';
import App from './components/App.vue';
import router from './router';

Vue.config.debug = true;
Vue.http.options.root = '/api';

Vue.use(VueRouter);
Vue.use(VueResource);
router.start(App, 'app');