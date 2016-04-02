class UserService {

    constructor() {
        this.user = null;

        this.init();
    }

    init() {
        const userItem = localStorage.getItem('user');

        try {
            const user = JSON.parse(userItem);

            if(!user) {
                return;
            }

            this.user = user;
        } catch(err) {
            console.error(err);
        }
    }

    login(token, name, admin) {
        this.user = {
            token: token,
            name: name,
            admin: admin
        };

        localStorage.setItem('user', JSON.stringify(this.user));
    }

    logout() {
        this.user = null;

        localStorage.removeItem('user');
    }

}

export default new UserService();