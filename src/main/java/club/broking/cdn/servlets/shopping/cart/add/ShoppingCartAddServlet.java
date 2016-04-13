package club.broking.cdn.servlets.shopping.cart.add;

import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import java.util.Map;
import java.util.UUID;

public class ShoppingCartAddServlet extends AbstractJsonServlet<ShoppingCartAddRequest, ShoppingCartAddResponse> {

    private final Session session;
    private final TokenService tokenService;

    public ShoppingCartAddServlet() {
        super(ShoppingCartAddRequest.class, ShoppingCartAddResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        this.session = cassandraService.getSession();
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(ShoppingCartAddRequest request, ShoppingCartAddResponse response) {
        Map<String, Object> claims = this.tokenService.verify(request.token);

        if(claims == null) {
            response.result = false;
            return;
        }

        PreparedStatement prepared = this.session.prepare("INSERT INTO shop.shopping_cart (id, user_id, product_id) VALUES (?, ?, ?);");
        BoundStatement bound = new BoundStatement(prepared);
        UUID id = UUID.randomUUID();
        UUID userId = UUID.fromString((String)claims.get("id"));
        UUID productId = UUID.fromString(request.productId);

        this.session.execute(bound.bind(id, userId, productId));

        response.result = true;
        response.id = id.toString();
    }

}
