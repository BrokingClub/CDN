import Vue from 'vue';
import userService from './userService';

class ApiService {

    constructor() {
        this.http = Vue.http;
    }

    register(email, name, password) {
        return this.post('/register', {
            email: email,
            name: name,
            password: password
        });
    }

    login(name, password) {
        return this.post('/login', {
            name: name,
            password: password
        });
    }

    users() {
        return this.post('/users', {
            token: userService.user.token
        });
    }

    admin(user) {
        return this.post('/admin', {
            token: userService.user.token,
            id: user.id,
            admin: user.admin
        });
    }

    deleteUser(user) {
        return this.post('/delete/user', {
            token: userService.user.token,
            id: user.id
        });
    }

    addProduct(name, price, image) {
        return this.post('/add/product', {
            token: userService.user.token,
            name: name,
            price: price,
            image: image
        });
    }

    post(url, data = {}) {
        return this.http.post('/api' + url, data)
            .catch(err => {
                toastr.error('Bitte versuchen Sie es später erneut', 'Unbekannter Fehler');
                console.error(err);
            });
    }

}

export default new ApiService();