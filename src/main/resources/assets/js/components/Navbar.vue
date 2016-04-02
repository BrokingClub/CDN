<template>
    <nav class="navbar navbar-full navbar-dark bg-inverse">
        <a class="navbar-brand" v-link="{ path: '/', exact: true }">Woods Sooper Dooper Shop</a>
        <div class="nav navbar-nav">
            <a class="nav-item nav-link" v-link="{ path: '/', exact: true }">Shop</a>
            <a class="nav-item nav-link" v-link="{ path: '/warenkorb' }" v-if="userService.loggedIn">Warenkorb</a>
            <a class="nav-item nav-link" v-link="{ path: '/bestellungen' }" v-if="userService.loggedIn">Bestellungen</a>
        </div>
        <div class="nav navbar-nav pull-sm-right">
            <a class="nav-item nav-link" v-link="{ path: '/anmelden' }" v-if="!userService.loggedIn">Anmelden</a>
            <a class="nav-item nav-link" v-link="{ path: '/registrieren' }" v-if="!userService.loggedIn">Registrieren</a>
            <a class="nav-item nav-link" v-link="{ path: '/backend' }" v-if="userService.admin">Backend</a>
            <a class="nav-item nav-link" href="" v-if="userService.loggedIn" @click="logout($event)">Abmelden</a>
        </div>
    </nav>
</template>

<script type="text/babel">
    import userService from '../services/userService';

    export default {
        data() {
            return {
                userService
            };
        },
        methods: {
            logout(event) {
                event.preventDefault();
                toastr.info('Bis bald ' + this.userService.name, 'Abgemeldet');
                userService.logout();
            }
        }
    };
</script>