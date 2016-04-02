import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './components/App.vue';
import router from './router';

Vue.config.debug = true;

Vue.use(VueRouter);
router.start(App, 'app');