<template>
    <div class="row">

        <!-- Products -->
        <div class="col-xs-12 col-lg-6">
            <h2>Produkte</h2>
            <br>
            <!-- Add Product -->
            <p>
                <button class="btn btn-secondary btn-sm" data-toggle="collapse" data-target="#collapse">Produkt hinzufügen</button>
            </p>
            <div id="collapse" class="collapse">
                <div class="card card-block">
                    <div class="form-group">
                        <label for="product">Produkt</label>
                        <input id="product" type="text" class="form-control" placeholder="Produkt" v-model="product">
                    </div>
                    <div class="form-group">
                        <label for="price">Preis &euro;</label>
                        <input id="price" type="number" step="0.01" min="0" class="form-control" placeholder="Preis" v-model="price">
                    </div>
                    <input id="file" type="file">
                    <button type="button" class="btn btn-primary" @click="addProduct()">Produkt hinzufügen</button>
                </div>
            </div>
            <!-- Add Product -->
            <!-- Products -->
            <div class="card-columns">
                <div class="card" v-for="product in products">
                    <img class="card-img-top" :src="product.image" style="width: 100%">
                    <div class="card-block">
                        <h4 class="card-title">{{ product.name }}</h4>
                        <p class="card-text">Preis: {{ product.price }} &euro;</p>
                        <button type="button" class="btn btn-danger-outline btn-sm">
                            Löschen
                            <i class="fa fa-trash-o"></i>
                        </button>
                    </div>
                </div>
            </div>
            <!-- Products -->
        </div>
        <!-- Products -->

        <!-- Users -->
        <div class="col-xs-12 col-lg-6">
            <h2>Benutzer</h2>
            <br>
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Admin</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users">
                    <th scope="row">{{ $index + 1 }}</th>
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
        <!-- Users -->

    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';

    export default {
        data() {
            return {
                users: [],
                product: '',
                price: 0,
                products: []
            };
        },
        route: {
            activate() {
                const usersPromise = apiService.users()
                    .then(response => {
                        this.users = response.data.users;
                    });
                const productsPromise = apiService.products()
                    .then(response => {
                        this.products = response.data.products;
                    });

                return Promise.all([ usersPromise, productsPromise ]);
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

                        for(let i = 0; i < this.users.length; i++) {
                            if(this.users[i].id === user.id) {
                                this.users.splice(i, 1);
                                break;
                            }
                        }

                        toastr.success(user.name + ' wurde aus der Datenbank gelöscht', 'Löschen erfolgreich');
                    });
            },
            addProduct() {
                if(!window.FileReader) {
                    window.location.href = 'https://www.google.com/chrome';
                    return;
                }

                const $file = $('#file');
                const files = $file.prop('files');

                if(files.length !== 1) {
                    toastr.warning('Produkt Bild fehlt', 'Bild auswählen');
                    return;
                }

                NProgress.start();

                const file = files[0];
                const fileReader = new FileReader();

                fileReader.onload = () => {
                    NProgress.inc();

                    const fileData = fileReader.result;

                    apiService.addProduct(this.product, this.price, fileData)
                        .then(response => {
                            NProgress.done();

                            if(response.data.result !== true) {
                                toastr.error(this.product + ' konnte nicht gespeichert werden', 'Fehler beim Hinzufügen');
                                return;
                            }

                            toastr.success(this.product + ' wurde gespeichert', 'Produkt hinzugefügt');
                            this.products.push(response.data.product);
                        })
                        .catch(() => NProgress.done());
                };

                fileReader.readAsDataURL(file);
            }
        }
    };
</script>