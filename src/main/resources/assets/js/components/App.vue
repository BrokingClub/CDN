<template>
    <navbar></navbar>
    <br>
    <div class="container">
        <router-view></router-view>
    </div>
</template>

<script type="text/babel">
    import Navbar from './Navbar.vue'

    export default {
        components: { Navbar }
    };
</script>