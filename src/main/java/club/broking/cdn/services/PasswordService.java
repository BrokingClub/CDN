package club.broking.cdn.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    private static PasswordService instance;

    private PasswordService() {}

    public static PasswordService getInstance() {
        if(PasswordService.instance == null) {
            PasswordService.instance = new PasswordService();
        }

        return PasswordService.instance;
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
