package club.broking.cdn.servlets.register;

import club.broking.cdn.models.User;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.auth0.jwt.JWTSigner;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterServlet extends AbstractJsonServlet<RegisterRequest, RegisterResponse> {

    public RegisterServlet() {
        super(RegisterRequest.class, RegisterResponse.class);
    }

    @Override
    protected void doPost(RegisterRequest request, RegisterResponse response) {
        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        Mapper<User> userMapper = mappingManager.mapper(User.class);
        User user = new User();
        user.id = UUID.randomUUID();
        user.email = request.email;
        user.name = request.name;

        userMapper.save(user);

        JWTSigner signer = new JWTSigner("secret");
        Map<String, Object> claims = new HashMap<String, Object>();

        claims.put("id", user.id.toString());
        claims.put("email", request.email);
        claims.put("name", request.name);

        response.result = true;
        response.token = signer.sign(claims);
    }

}
