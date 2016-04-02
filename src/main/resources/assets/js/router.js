import VueRouter from 'vue-router';

/* Views */
import Shop from './components/views/Shop.vue';
import Backend from './components/views/Backend.vue';
import Register from './components/views/Register.vue';
import Login from './components/views/Login.vue';
/* Views */

const routerOptions = {
    hashbang: false,
    history: true,
    linkActiveClass: 'active',
    saveScrollPosition: true
};
const router = new VueRouter(routerOptions);

export default router;

/* Routes */
router.map({
    '/': {
        component: Shop
    },
    '/backend': {
        component: Backend
    },
    '/registrieren': {
        component: Register
    },
    '/anmelden': {
        component: Login
    }
});

router.redirect({
    '*': '/'
});
/* Routes */