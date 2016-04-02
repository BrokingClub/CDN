package club.broking.cdn.servlets.delete.product;

import club.broking.cdn.models.Product;
import club.broking.cdn.models.ProductAccessor;
import club.broking.cdn.services.CassandraService;
import club.broking.cdn.services.TokenService;
import club.broking.cdn.servlets.AbstractJsonServlet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

public class DeleteProductServlet extends AbstractJsonServlet<DeleteProductRequest, DeleteProductResponse> {

    private final CassandraService cassandraService;
    private final ProductAccessor ProductAccessor;
    private final TokenService tokenService;

    public DeleteProductServlet() {
        super(DeleteProductRequest.class, DeleteProductResponse.class);

        this.cassandraService = CassandraService.getInstance();
        MappingManager mappingManager = cassandraService.getMappingManager();
        this.ProductAccessor = mappingManager.createAccessor(ProductAccessor.class);
        this.tokenService = TokenService.getInstance();
    }

    @Override
    protected void doPost(DeleteProductRequest request, DeleteProductResponse response) {
        if(!this.tokenService.isAdmin(request.token)) {
            response.result = false;
            return;
        }

        Product product = this.findById(request.id);

        if(product == null) {
            response.result = false;
            return;
        }

        // Fucking Object Mapper did not work
        this.cassandraService.getSession().execute("DELETE FROM shop.products WHERE id = " + product.getId().toString() + ";");

        response.result = true;
    }

    private Product findById(String id) {
        Result<Product> Products = this.ProductAccessor.all();

        for(Product product:Products) {
            if(product.getId().toString().equals(id)) {
                return product;
            }
        }

        return null;
    }

}
