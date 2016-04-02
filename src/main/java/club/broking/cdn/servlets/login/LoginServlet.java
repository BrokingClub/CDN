package club.broking.cdn.servlets.login;

import club.broking.cdn.servlets.AbstractJsonServlet;

public class LoginServlet extends AbstractJsonServlet<LoginRequest, LoginResponse> {

    public LoginServlet() {
        super(LoginRequest.class, LoginResponse.class);
    }

    @Override
    protected void doPost(LoginRequest request, LoginResponse response) {

    }

}
