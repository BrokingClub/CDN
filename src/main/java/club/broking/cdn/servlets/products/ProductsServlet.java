package club.broking.cdn.servlets.products;

import club.broking.cdn.models.Product;
import club.broking.cdn.models.ProductAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.MappingManager;

import java.util.List;

public class ProductsServlet extends AbstractJsonServlet<ProductsRequest, ProductsResponse> {

    private final ProductAccessor productAccessor;

    public ProductsServlet() {
        super(ProductsRequest.class, ProductsResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.productAccessor = mappingManager.createAccessor(ProductAccessor.class);
    }

    @Override
    protected void doPost(ProductsRequest request, ProductsResponse response) {
        List<Product> products = this.productAccessor.all().all();
        response.products = products.toArray(new Product[products.size()]);
    }

}
