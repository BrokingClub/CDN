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
        return this.post('/user/delete', {
            token: userService.user.token,
            id: user.id
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