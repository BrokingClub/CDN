package club.broking.cdn.servlets.login;

import club.broking.cdn.models.User;
import club.broking.cdn.models.UserAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.PasswordService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.auth0.jwt.JWTSigner;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends AbstractJsonServlet<LoginRequest, LoginResponse> {

    private final Mapper<User> userMapper;
    private final UserAccessor userAccessor;
    private final TokenService tokenService;
    private final PasswordService passwordService;

    public LoginServlet() {
        super(LoginRequest.class, LoginResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.userMapper = mappingManager.mapper(User.class);
        this.userAccessor = mappingManager.createAccessor(UserAccessor.class);
        this.tokenService = TokenService.getInstance();
        this.passwordService = PasswordService.getInstance();
    }

    @Override
    protected void doPost(LoginRequest request, LoginResponse response) {
        User user = this.findUser(request.name);

        if(user == null) {
            response.result = false;
            return;
        }

        boolean correctPassword = this.passwordService.verify(request.password, user.getPassword());

        if(!correctPassword) {
            response.result = false;
            return;
        }

        response.result = true;
        response.token = tokenService.sign(user);
        response.name = user.getName();
        response.admin = user.isAdmin();
    }

    private User findUser(String name) {
        Result<User> users = userAccessor.all();

        for(User user:users) {
            if(user.getName().equalsIgnoreCase(name) || user.getEmail().equalsIgnoreCase(name)) {
                return user;
            }
        }

        return null;
    }

}
