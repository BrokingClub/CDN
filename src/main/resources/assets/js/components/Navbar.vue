<template>
    <nav class="navbar navbar-full navbar-dark bg-inverse">
        <a class="navbar-brand" v-link="{ path: '/', exact: true }">Woods Sooper Dooper Shop</a>
        <div class="nav navbar-nav">
            <a class="nav-item nav-link" v-link="{ path: '/', exact: true }">Shop</a>
            <a class="nav-item nav-link" v-link="{ path: '/backend' }">Backend</a>
        </div>
        <div class="nav navbar-nav pull-sm-right">
            <a class="nav-item nav-link" v-link="{ path: '/anmelden' }">Anmelden</a>
            <a class="nav-item nav-link" v-link="{ path: '/registrieren' }">Registrieren</a>
            <a class="nav-item nav-link" href="">Abmelden</a>
        </div>
    </nav>
</template>