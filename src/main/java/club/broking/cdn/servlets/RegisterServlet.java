package club.broking.cdn.servlets;

import club.broking.cdn.servlets.models.RegisterRequest;
import club.broking.cdn.servlets.models.RegisterResponse;
import com.auth0.jwt.JWTSigner;

import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends AbstractJsonServlet<RegisterRequest, RegisterResponse> {

    public RegisterServlet() {
        super(RegisterRequest.class, RegisterResponse.class);
    }

    @Override
    protected void doPost(RegisterRequest registerRequest, RegisterResponse registerResponse) {
        JWTSigner signer = new JWTSigner("secret");
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("email", registerRequest.email);
        claims.put("name", registerRequest.name);

        registerResponse.result = true;
        registerResponse.token = signer.sign(claims);
    }

}
