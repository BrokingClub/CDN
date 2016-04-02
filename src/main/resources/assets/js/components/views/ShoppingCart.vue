<template>
    <div class="card">
        <div class="card-block">
            <span>{{{ productCountSentence }}}</span>
            <span v-if="productCount > 0">
                <br>
                Der Gesamtwert beträgt <strong>{{ totalPrice }} &euro;</strong>
                <br><br>
            </span>
            <button type="button" class="btn btn-primary" @click="order()" v-if="productCount > 0">Jetzt bestellen!</button>
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
                let totalPrice = 0;

                this.products.forEach(product => totalPrice += product.price);

                return totalPrice;
            }
        },
        methods: {
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