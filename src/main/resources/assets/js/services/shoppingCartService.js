import apiService from './apiService';

class ShoppingCartService {

    constructor() {
        this.products = [];
    }

    refresh() {
        apiService.shoppingCart()
            .then(response => {
                this.products = response.data.shoppingCartProducts;
            });
    }

    clear() {
        this.products.length = [];
    }

    totalPrice() {
        let totalPrice = 0;

        this.products.forEach(product => totalPrice += product.product.price);

        return totalPrice;
    }

    add(product) {
        apiService.addToShoppingCart(product)
            .then(response => {
                if(response.data.result !== true) {
                    toastr.error('Das Produkt konnte nicht in den Einkaufswagen gelegt werden', 'Hinzufügen fehlgeschlagen!');
                    return;
                }

                const id = response.data.id;

                this.products.push({
                    id: id,
                    product: product
                });

                toastr.success(product.name + ' hinzugefügt!');
            });
    }

    remove(id) {
        for(let i = 0; i < this.products.length; i++) {
            if(this.products[i].id === id) {
                this.products.splice(i, 1);
                break;
            }
        }

        apiService.deleteFromShoppingCart(id);
    }

}

export default new ShoppingCartService();