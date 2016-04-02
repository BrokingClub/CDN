<template>
    <div class="card-columns">
        <div class="card" v-for="product in products">
            <img class="card-img-top" :src="product.image" style="width: 100%">
            <div class="card-block">
                <h4 class="card-title">{{ product.name }}</h4>
                <p class="card-text">Preis: {{ product.price }} &euro;</p>
                <button type="button" class="btn btn-primary-outline" @click="buy(product)">
                    In den Einkaufswagen
                    <i class="fa fa-shopping-cart"></i>
                </button>
            </div>
        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';
    import userService from '../../services/userService';
    import shoppingCartService from '../../services/shoppingCartService';

    export default {
        data() {
            return {
                products: []
            };
        },
        methods: {
            buy(product) {
                if(!userService.user) {
                    toastr.info('Sie müssen sich anmelden um Produkte in den Einkaufswagen zu legen', 'Anmeldung erforderlich');
                    this.$route.router.go('/anmelden');
                    return;
                }

                shoppingCartService.products.push(product);
            }
        },
        route: {
            activate() {
                return apiService.products()
                    .then(response => {
                        this.products = response.data.products;
                    });
            }
        },
    };
</script>