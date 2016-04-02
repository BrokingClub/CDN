class UserService {

    constructor() {
        this.token = null;
        this.name = null;
        this.admin = false;
        this.loggedIn = false;

        this.init();
    }

    init() {
        const userItem = localStorage.getItem('user');

        try {
            const user = JSON.parse(userItem);

            this.token = user.token;
            this.name = user.name;
            this.admin = user.admin;
            this.loggedIn = true;
        } catch(err) {
            console.error(err);
        }
    }

    login(token, name, admin) {
        this.token = token;
        this.name = name;
        this.admin = admin;
        const user = this.user();

        localStorage.setItem('user', JSON.stringify(user));
    }

    logout() {
        this.token = null;
        this.name = null;
        this.admin = false;

        localStorage.removeItem('user');
    }

    user() {
        return {
            token: this.token,
            name: this.name,
            admin: this.admin
        };
    }

}

export default new UserService();