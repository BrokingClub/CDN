class UserService {

    constructor() {
        this.loggedIn = false;
        this.isAdmin = false;
        this.user = null;
    }

    logout() {
        this.loggedIn = false;
        this.isAdmin = false;
        this.user = null;
    }

}

export default new UserService();