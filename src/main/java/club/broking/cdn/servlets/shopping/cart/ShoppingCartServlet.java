package club.broking.cdn.servlets.shopping.cart;

import club.broking.cdn.models.Product;
import club.broking.cdn.models.ProductAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.core.*;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ShoppingCartServlet extends AbstractJsonServlet<ShoppingCartRequest, ShoppingCartResponse> {

    private final Session session;
    private final ProductAccessor productAccessor;
    private final TokenService tokenService;

    public ShoppingCartServlet() {
        super(ShoppingCartRequest.class, ShoppingCartResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.session = cassandraService.getSession();
        this.productAccessor = mappingManager.createAccessor(ProductAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(ShoppingCartRequest request, ShoppingCartResponse response) {
        Map<String, Object> claims = this.tokenService.verify(request.token);

        if(claims == null) {
            return;
        }

        PreparedStatement prepared = this.session.prepare("SELECT id, product_id FROM shop.shopping_cart WHERE user_id = ?;");
        BoundStatement bound = new BoundStatement(prepared);
        UUID userId = UUID.fromString((String)claims.get("id"));
        ResultSet result = this.session.execute(bound.bind(userId));
        Set<ShoppingCartProduct> products = new HashSet<ShoppingCartProduct>();

        for(Row row:result) {
            String id = row.getString("id");
            UUID productId = row.getUUID("product_id");
            Product product = this.findById(productId);

            if(product == null) {
                return;
            }

            ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
            shoppingCartProduct.id = id;
            shoppingCartProduct.product = product;

            products.add(shoppingCartProduct);
        }

        response.shoppingCartProducts = products.toArray(new ShoppingCartProduct[products.size()]);
    }

    private Product findById(UUID id) {
        Result<Product> Products = this.productAccessor.all();

        for(Product product:Products) {
            if(product.getId().equals(id)) {
                return product;
            }
        }

        return null;
    }

}
