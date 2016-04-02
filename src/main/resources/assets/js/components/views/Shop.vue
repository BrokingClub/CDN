<template>
    <div class="card-columns">
        <div class="card" v-for="product in products">
            <img class="card-img-top" :src="product.image" style="width: 100%">
            <div class="card-block">
                <h4 class="card-title">{{ product.name }}</h4>
                <p class="card-text">Preis: {{ product.price }} &euro;</p>
                <button type="button" class="btn btn-primary-outline" @click="buy(product)">
                    Kaufen
                    <i class="fa fa-shopping-basket"></i>
                </button>
            </div>
        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';
    import userService from '../../services/userService';

    export default {
        data() {
            return {
                products: []
            };
        },
        methods: {
            buy(product) {
                if(!userService.user) {
                    toastr.info('Sie müssen sich anmelden um Produkte zu kaufen', 'Anmeldung erforderlich');
                    this.$route.router.go('/anmelden');
                    return;
                }
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