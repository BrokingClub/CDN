package club.broking.cdn.servlets.users;

import club.broking.cdn.models.User;
import club.broking.cdn.models.UserAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.PasswordService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import club.broking.cdn.servlets.users.UsersRequest;
import club.broking.cdn.servlets.users.UsersResponse;
import com.auth0.jwt.JWTSigner;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersServlet extends AbstractJsonServlet<UsersRequest, UsersResponse> {

    private final UserAccessor userAccessor;
    private final TokenService tokenService;

    public UsersServlet() {
        super(UsersRequest.class, UsersResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.userAccessor = mappingManager.createAccessor(UserAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(UsersRequest request, UsersResponse response) {
        if(!this.tokenService.isAdmin(request.token)) {
            return;
        }

        List<User> users = this.userAccessor.all().all();

        for(User user:users) {
            user.setPassword("Nothing to see here!");
        }

        response.users = users.toArray(new User[users.size()]);
    }

}
