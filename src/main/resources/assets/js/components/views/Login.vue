<template>
    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-lg-4 center-block">
            <h1>Anmelden</h1>
            <hr>
            <div class="form-group">
                <label for="name">Benutzername</label>
                <input id="name" type="text" class="form-control" placeholder="Benutzername" v-model="name">
            </div>
            <div class="form-group">
                <label for="password">Passwort</label>
                <input id="password" type="password" class="form-control" placeholder="Passwort" v-model="password">
            </div>
            <hr>
            <button type="button" class="btn btn-primary btn-block" @click="login()">Anmelden</button>
        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';
    import userService from '../../services/userService';

    export default {
        data() {
            return {
                name: '',
                password: ''
            };
        },
        methods: {
            login() {
                apiService.login(this.name, this.password)
                    .then(response => {
                        const data = response.data;

                        if(data.result !== true) {
                            toastr.error('Die Anmeldedaten sind nicht gültig', 'Anmeldung fehlgeschlagen');
                            return;
                        }

                        userService.login(data.token, data.name, data.admin);
                        toastr.success('Willkommen zurück ' + data.name, 'Anmeldung erfolgreich');
                        this.$route.router.go('/');
                    });
            }
        }
    };
</script>