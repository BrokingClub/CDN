package club.broking.cdn.services;

import club.broking.cdn.models.User;
import com.auth0.jwt.JWTSigner;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class TokenService {

    private static TokenService instance;

    private TokenService() {}

    public static TokenService getInstance() {
        if(TokenService.instance == null) {
            TokenService.instance = new TokenService();
        }

        return TokenService.instance;
    }

    public String sign(User user) {
        JWTSigner signer = new JWTSigner("secret");
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("id", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("admin", user.isAdmin());

        return signer.sign(claims);
    }

}
