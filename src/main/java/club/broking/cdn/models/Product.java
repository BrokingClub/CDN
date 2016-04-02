package club.broking.cdn.models;

import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(keyspace = "shop", name = "products")
public class Product {

    private UUID id;
    private String name;
    private double price;
    private String image;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
