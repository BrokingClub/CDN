package club.broking.cdn.servlets.delete.user;

import club.broking.cdn.models.User;
import club.broking.cdn.models.UserAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

public class DeleteUserServlet extends AbstractJsonServlet<DeleteUserRequest, DeleteUserResponse> {

    private final CassandraService cassandraService;
    private final UserAccessor userAccessor;
    private final TokenService tokenService;

    public DeleteUserServlet() {
        super(DeleteUserRequest.class, DeleteUserResponse.class);

        this.cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.userAccessor = mappingManager.createAccessor(UserAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(DeleteUserRequest request, DeleteUserResponse response) {
        if(!this.tokenService.isAdmin(request.token)) {
            response.result = false;
            return;
        }

        User user = this.findById(request.id);

        if(user == null) {
            response.result = false;
            return;
        }

        // Fucking Object Mapper did not work
        this.cassandraService.getSession().execute("DELETE FROM shop.users WHERE id = " + user.getId().toString() + ";");

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
