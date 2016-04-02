class UserService {

    constructor() {
        this.loggedIn = true;
        this.isAdmin = false;
        this.user = {
            name: 'Marc'
        };
    }

    logout() {
        this.loggedIn = false;
    }

}

export default new UserService();