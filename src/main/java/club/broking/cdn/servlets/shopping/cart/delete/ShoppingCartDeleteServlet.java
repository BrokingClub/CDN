package club.broking.cdn.servlets.shopping.cart.delete;

import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.UUID;

public class ShoppingCartDeleteServlet extends AbstractJsonServlet<ShoppingCartDeleteRequest, ShoppingCartDeleteResponse> {

    private final Session session;
    private final TokenService tokenService;

    public ShoppingCartDeleteServlet() {
        super(ShoppingCartDeleteRequest.class, ShoppingCartDeleteResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        this.session = cassandraService.getSession();
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(ShoppingCartDeleteRequest request, ShoppingCartDeleteResponse response) {
        Map<String, Object> claims = this.tokenService.verify(request.token);

        if(claims == null) {
            response.result = false;
            return;
        }

        PreparedStatement prepared = this.session.prepare("DELETE FROM shop.shopping_cart WHERE id = ?;");
        BoundStatement bound = new BoundStatement(prepared);
        UUID id = UUID.fromString(request.id);

        this.session.execute(bound.bind(id));

        response.result = true;
    }

}
