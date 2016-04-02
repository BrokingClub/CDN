import VueRouter from 'vue-router';

/* Views */
import Shop from './components/views/Shop.vue';
import ShoppingCart from './components/views/ShoppingCart.vue';
import Orders from './components/views/Orders.vue';
import Register from './components/views/Register.vue';
import Login from './components/views/Login.vue';
import Backend from './components/views/Backend.vue';
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
    '/warenkorb': {
        component: ShoppingCart
    },
    '/bestellungen': {
        component: Orders
    },
    '/registrieren': {
        component: Register
    },
    '/anmelden': {
        component: Login
    },
    '/backend': {
        component: Backend
    }
});

router.redirect({
    '*': '/'
});
/* Routes */