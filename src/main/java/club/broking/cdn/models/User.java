package club.broking.cdn.models;

import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(keyspace = "shop", name = "users")
public class User {

    private UUID id;
    private String email;
    private String name;
    private String password;
    private boolean admin;

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
