<template>
    <div class="row">
        <div class="col-xs-12 col-lg-6">
            <h2>Produkte</h2>
            <br>
        </div>
        <div class="col-xs-12 col-lg-6">
            <h2>Benutzer</h2>
            <br>
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Admin</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users">
                    <th scope="row">{{ $index + 1 }}</th>
                    <td>{{ user.id }}</td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                    <td style="text-align: center">
                        <input type="checkbox" v-model="user.admin" @click="toggleAdmin(user)">
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger-outline btn-sm" @click="deleteUser(user)">
                            <i class="fa fa-trash-o"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';

    export default {
        data() {
            return {
                users: []
            };
        },
        route: {
            activate() {
                return apiService.users()
                    .then(response => {
                        this.users = response.data.users;
                    })
            }
        },
        methods: {
            toggleAdmin(user) {
                user.admin = !user.admin;

                apiService.admin(user)
                    .then(response => {
                        if(response.data.result !== true) {
                            toastr.error('Der Admin Status von ' + user.name + ' konnte nicht gespeichert werden', 'Fehler beim Speichern');
                            return;
                        }

                        const message = user.admin ? user.name + ' ist nun Admin' : user.name + ' ist kein Admin mehr';

                        toastr.success(message, 'Admin Status gespeichert');
                    });
            },
            deleteUser(user) {
                apiService.deleteUser(user)
                    .then(response => {
                        if(response.data.result !== true) {
                            toastr.error(user.name + ' konnte nicht gelöscht werden', 'Fehler beim Löschen');
                            return;
                        }

                        _.remove(this.users, user);
                        toastr.success(user.name + ' wurde aus der Datenbank gelöscht', 'Löschen erfolgreich');
                    });
            }
        }
    };
</script>