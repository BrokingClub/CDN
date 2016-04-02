package club.broking.cdn.services;

public class UserService {

    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if(UserService.instance == null) {
            UserService.instance = new UserService();
        }

        return UserService.instance;
    }

    public void register(String email, String name, String password) {

    }

    public void login(String name, String password) {

    }

}
