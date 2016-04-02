package club.broking.cdn.services;

import club.broking.cdn.models.User;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
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

    public Map<String, Object> verify(String token) {
        if(token == null) {
            return null;
        }

        JWTVerifier verifier = new JWTVerifier("secret");

        try {
            return verifier.verify(token);
        } catch(Exception e) {
            e.printStackTrace();;
        }

        return null;
    }

    public boolean isAdmin(String token) {
        Map<String, Object> claims = this.verify(token);

        if(claims == null) {
            return false;
        }

        Object admin = claims.get("admin");

        if(admin instanceof Boolean) {
            return (Boolean)admin;
        }

        return false;
    }

}
