<template>
    <div class="row">
        <div class="col-xs-12 col-md-8 col-lg-5 center-block">

            <div class="card">
                <div class="card-block">
                    <div>{{{ productCountSentence }}}</div>
                    <div v-if="productCount > 0">
                        Der Gesamtwert beträgt <strong>{{ totalPrice }} &euro;</strong>
                    </div>
                </div>
            </div>

            <ul class="list-group">
                <button type="button" class="list-group-item" v-for="product in products" track-by="$index" @click="removeFromCart(product.id)">
                    <span class="label label-success label-pill pull-xs-right">{{ product.product.price }} &euro;</span>
                    {{ product.product.name }}
                </button>
            </ul>

            <br>

            <button type="button" class="btn btn-primary btn-block" @click="order()" v-if="productCount > 0">Jetzt bestellen!</button>

        </div>
    </div>
</template>

<script type="text/babel">
    import shoppingCartService from '../../services/shoppingCartService';

    export default {
        data() {
            return {
                shoppingCartService
            };
        },
        computed: {
            products() {
                return this.shoppingCartService.products;
            },
            productCount() {
                return this.products.length;
            },
            productCountSentence() {
                if(this.productCount === 0) {
                    return 'Der Einkaufswagen ist leer.';
                }

                return `Es ${ this.productCount === 1 ? 'befindet' : 'befinden' } sich <strong>${ this.productCount } ${ this.productCount === 1 ? 'Produkt' : 'Produkte' }</strong> im Einkaufswagen.`;
            },
            totalPrice() {
                return this.shoppingCartService.totalPrice();
            }
        },
        methods: {
            removeFromCart(id) {
                this.shoppingCartService.remove(id);
            },
            order() {
                if(this.productCount === 0) {
                    toastr.info('Es befinden sich keine Produkte im Einkaufswagen', 'Leerer Einkaufswagen');
                    return;
                }

                toastr.success('Ihre Bestellung über ' + this.totalPrice + ' &euro; wurde angenommen', 'Bestellung abgesendet');

                this.shoppingCartService.products = [];

                this.$route.router.go('/bestellungen');
            }
        }
    };
</script>