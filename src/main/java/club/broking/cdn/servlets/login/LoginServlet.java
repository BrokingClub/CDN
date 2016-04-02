package club.broking.cdn.servlets.login;

import club.broking.cdn.servlets.AbstractJsonServlet;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends AbstractJsonServlet<LoginRequest, LoginResponse> {

    public LoginServlet() {
        super(LoginRequest.class, LoginResponse.class);
    }

    @Override
    protected void doPost(LoginRequest request, LoginResponse response) {
        JWTSigner signer = new JWTSigner("secret");
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("name", request.name);

        response.result = true;
        response.token = signer.sign(claims);
    }

}
