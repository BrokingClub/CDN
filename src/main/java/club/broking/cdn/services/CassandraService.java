package club.broking.cdn.services;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.MappingManager;

public class CassandraService {

    private static CassandraService instance;

    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;

    private CassandraService() {}

    public static CassandraService getInstance() {
        if(CassandraService.instance == null) {
            CassandraService.instance = new CassandraService();
        }

        return CassandraService.instance;
    }

    public void connect(String node) {
        this.cluster = Cluster.builder()
                .addContactPoint(node)
                .build();
        Metadata meta = cluster.getMetadata();

        System.out.println("Connected to Cassandra: " + meta.getClusterName());

        for(Host host:meta.getAllHosts()) {
            System.out.println("Datacenter: " + host.getDatacenter() + ", Host: " + host.getAddress() + ", Rack: " + host.getRack());
        }

        this.session = this.cluster.connect();
        this.mappingManager = new MappingManager(this.session);
    }

    public void createSchema() {
        //this.dropSchema();
        this.session.execute("CREATE KEYSPACE IF NOT EXISTS shop WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 3 };");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.users (id uuid, email text, name text, password text, admin boolean, PRIMARY KEY (id));");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.products (id uuid, name text, price double, image text, PRIMARY KEY (id));");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.shopping_cart (id uuid, user_id uuid, product_id uuid, PRIMARY KEY (id, user_id, product_id));");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.orders (id uuid, user_id uuid, total_price double, created_at timestamp, PRIMARY KEY(id, user_id));");
        this.session.execute("CREATE TABLE IF NOT EXISTS shop.order_products (order_id uuid, name text, price double, image text, PRIMARY KEY(order_id));");
    }

    public void dropSchema() {
        this.session.execute("DROP KEYSPACE shop;");
        System.out.println("Cassandra schema dropped!");
    }

    public Session getSession() {
        return this.session;
    }

    public MappingManager getMappingManager() {
        return this.mappingManager;
    }

    public void close() {
        this.cluster.close();
        System.out.println("Cassandra connection closed");
    }

}
