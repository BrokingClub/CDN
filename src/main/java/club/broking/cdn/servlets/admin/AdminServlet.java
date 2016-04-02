package club.broking.cdn.servlets.admin;

import club.broking.cdn.models.User;
import club.broking.cdn.models.UserAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

public class AdminServlet extends AbstractJsonServlet<AdminRequest, AdminResponse> {

    private final Mapper<User> userMapper;
    private final UserAccessor userAccessor;
    private final TokenService tokenService;

    public AdminServlet() {
        super(AdminRequest.class, AdminResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.userMapper = mappingManager.mapper(User.class);
        this.userAccessor = mappingManager.createAccessor(UserAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(AdminRequest request, AdminResponse response) {
        if(!this.tokenService.isAdmin(request.token)) {
            response.result = false;
            return;
        }

        User user = this.findById(request.id);

        if(user == null) {
            response.result = false;
            return;
        }

        user.setAdmin(request.admin);
        this.userMapper.save(user);

        response.result = true;
    }

    private User findById(String id) {
        Result<User> users = this.userAccessor.all();

        for(User user:users) {
            if(user.getId().toString().equals(id)) {
                return user;
            }
        }

        return null;
    }

}
