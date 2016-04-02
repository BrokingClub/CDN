package club.broking.cdn.servlets.register;

import club.broking.cdn.servlets.AbstractJsonServlet;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends AbstractJsonServlet<RegisterRequest, RegisterResponse> {

    public RegisterServlet() {
        super(RegisterRequest.class, RegisterResponse.class);
    }

    @Override
    protected void doPost(RegisterRequest request, RegisterResponse response) {
        JWTSigner signer = new JWTSigner("secret");
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("email", request.email);
        claims.put("name", request.name);

        response.result = true;
        response.token = signer.sign(claims);
    }

}
