package club.broking.cdn.servlets.register;

import club.broking.cdn.models.UserAccessor;
import club.broking.cdn.models.User;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.PasswordService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.UUID;

public class RegisterServlet extends AbstractJsonServlet<RegisterRequest, RegisterResponse> {

    private final Mapper<User> userMapper;
    private final UserAccessor userAccessor;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    public RegisterServlet() {
        super(RegisterRequest.class, RegisterResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.userMapper = mappingManager.mapper(User.class);
        this.userAccessor = mappingManager.createAccessor(UserAccessor.class);
        this.passwordService = PasswordService.getInstance();
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(RegisterRequest request, RegisterResponse response) {
        Result<User> users = this.userAccessor.all();

        for(User user:users) {
            if(user.getName().equalsIgnoreCase(request.name)) {
                response.result = false;
                response.errorMessage = "Der Name '" + request.name + "' wurde bereits vergeben!";
                return;
            }

            if(user.getEmail().equalsIgnoreCase(request.email)) {
                response.result = false;
                response.errorMessage = "Die Email '" + request.name + "' wurde bereits vergeben!";
                return;
            }
        }

        User user = new User();
        UUID id = UUID.randomUUID();
        String hash = this.passwordService.hash(request.password);

        user.setId(id);
        user.setEmail(request.email);
        user.setName(request.name);
        user.setPassword(hash);
        user.setAdmin(false);

        this.userMapper.save(user);

        response.result = true;
        response.token = this.tokenService.sign(user);
    }

}
