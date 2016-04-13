package club.broking.cdn.servlets.order;

import club.broking.cdn.models.Product;
import club.broking.cdn.models.ProductAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.*;

public class OrderServlet extends AbstractJsonServlet<OrderRequest, OrderResponse> {

    private final Session session;
    private final ProductAccessor productAccessor;
    private final TokenService tokenService;

    public OrderServlet() {
        super(OrderRequest.class, OrderResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.session = cassandraService.getSession();
        this.productAccessor = mappingManager.createAccessor(ProductAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(OrderRequest request, OrderResponse response) {
        Map<String, Object> claims = tokenService.verify(request.token);

        if(claims == null) {
            response.result = false;
            return;
        }

        Product[] products = this.findProducts(request.productIds);
        PreparedStatement prepared = this.session.prepare("INSERT INTO shop.orders (id, user_id, total_price, created_at) VALUES (?, ?, ?, ?);");
        BoundStatement bound = new BoundStatement(prepared);
        UUID id = UUID.randomUUID();
        UUID userId = UUID.fromString((String)claims.get("id"));
        double totalPrice = this.calculateTotalPrice(products);

        this.session.execute(bound.bind(id, userId, totalPrice, new Date()));

        for(Product product:products) {
            this.addToOrder(id, product);
        }

        response.result = true;
    }

    private Product[] findProducts(String[] productIds) {
        Set<Product> products = new HashSet<Product>();
        Result<Product> allProducts = this.productAccessor.all();

        for(Product product:allProducts) {
            String productId = product.getId().toString();

            if(this.contains(productIds, productId)) {
                products.add(product);
            }
        }

        return products.toArray(new Product[products.size()]);
    }

    private boolean contains(String[] array, String input) {
        for(String element:array) {
            if(element.equals(input)) {
                return true;
            }
        }

        return false;
    }

    private double calculateTotalPrice(Product[] products) {
        double totalPrice = 0;

        for(Product product:products) {
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }

    private void addToOrder(UUID orderId, Product product) {
        PreparedStatement prepared = this.session.prepare("INSERT INTO shop.order_products (id, order_id, name, price, image) VALUES (?, ?, ?, ?, ?);");
        BoundStatement bound = new BoundStatement(prepared);
        UUID id = UUID.randomUUID();
        String name = product.getName();
        double price = product.getPrice();
        String image = product.getImage();

        this.session.execute(bound.bind(id, orderId, name, price, image));
    }

}
