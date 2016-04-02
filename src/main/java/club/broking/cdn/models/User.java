package club.broking.cdn.models;

import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(keyspace = "shop", name = "users")
public class User {

    public UUID id;
    public String email;
    public String name;
    public String password;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
