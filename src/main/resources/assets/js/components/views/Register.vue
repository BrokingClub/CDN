<template>
    <div class="row">
        <div class="col-xs-12 col-sm-8 col-md-6 col-lg-4 center-block">
            <h1>Registrieren</h1>
            <hr>
            <div class="form-group">
                <label for="email">Email</label>
                <input id="email" type="email" class="form-control" placeholder="Email" v-model="email">
            </div>
            <div class="form-group">
                <label for="name">Benutzername</label>
                <input id="name" type="text" class="form-control" placeholder="Benutzername" v-model="name">
            </div>
            <div class="form-group">
                <label for="password">Passwort</label>
                <input id="password" type="password" class="form-control" placeholder="Passwort" v-model="password">
            </div>
            <hr>
            <button type="button" class="btn btn-primary btn-block" @click="register()">Registrieren</button>
            <hr>
            <a v-link="{ path: '/anmelden' }">Sie haben bereits einen Account?</a>
        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';
    import userService from '../../services/userService';

    export default {
        data() {
            return {
                email: '',
                name: '',
                password: ''
            };
        },
        methods: {
            register() {
                apiService.register(this.email, this.name, this.password)
                    .then(response => {
                        const data = response.data;

                        if(data.result !== true) {
                            if(data.errorMessage) {
                                toastr.error(data.errorMessage, 'Fehler bei der Registrierung');
                                return;
                            }

                            toastr.error('Bitte versuchen Sie es später erneut', 'Unbekannter Fehler');
                            return;
                        }

                        userService.login(data.token, this.name, false);
                        toastr.success('Herzlich willkommen ' + this.name, 'Account registriert');
                        this.$route.router.go('/');
                    });
            }
        }
    };
</script>