package club.broking.cdn.models;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface ProductAccessor {

    @Query("SELECT * FROM shop.products;")
    Result<Product> all();

}
