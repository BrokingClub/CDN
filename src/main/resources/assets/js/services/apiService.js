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

    post(url, data = {}) {
        return this.http.post('/api' + url, data)
            .catch(err => {
                toastr.error('Bitte versuchen Sie es später erneut', 'Unbekannter Fehler');
                console.error(err);
            });
    }

}

export default new ApiService();