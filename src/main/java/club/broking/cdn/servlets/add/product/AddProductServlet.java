package club.broking.cdn.servlets.add.product;

import club.broking.cdn.models.Product;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import java.util.UUID;

public class AddProductServlet extends AbstractJsonServlet<AddProductRequest, AddProductResponse> {

    private final Mapper<Product> productMapper;
    private final TokenService tokenService;

    public AddProductServlet() {
        super(AddProductRequest.class, AddProductResponse.class);

        CassandraService cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.productMapper = mappingManager.mapper(Product.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(AddProductRequest request, AddProductResponse response) {
        if(!this.tokenService.isAdmin(request.token)) {
            response.result = false;
            return;
        }

        Product product = new Product();
        UUID id = UUID.randomUUID();

        product.setId(id);
        product.setName(request.name);
        product.setPrice(request.price);
        product.setImage(request.image);

        this.productMapper.save(product);

        response.result = true;
        response.product = product;
    }

}
