<template>
    <div class="row">
        <div class="col-xs-12 col-md-8 col-lg-5 center-block">

            <div class="card" v-for="order in sortedOrders" track-by="$index">
                <div class="card-block">
                    <h4 class="card-title">Bestellung über {{ order.totalPrice }} &euro;</h4>
                    <h6 class="card-subtitle text-muted">{{ displayDate(order.createdAt) }}</h6>
                </div>

                <ul class="list-group list-group-flush">
                    <li class="list-group-item" v-for="product in order.products">{{ product.name }} ({{ product.price }} &euro;)</li>
                </ul>
            </div>

        </div>
    </div>
</template>

<script type="text/babel">
    import apiService from '../../services/apiService';

    export default {
        data() {
            return {
                orders: []
            };
        },
        computed: {
            sortedOrders() {
                return _.orderBy(this.orders, 'createdAt', 'desc');
            }
        },
        methods: {
            displayDate(date) {
                return moment(date).format('D. MMMM YYYY - HH:mm');
            }
        },
        route: {
            activate() {
                return apiService.orders()
                    .then(response => {
                        if(response.data.result !== true) {
                            toastr.error('Bestellungen konnten nicht geladen werden');
                            return;
                        }

                        this.orders = response.data.orders;
                    });
            }
        }
    };
</script>