package club.broking.cdn.setup;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class Cassandra {

    public void setup() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("localhost")
                .build();
        Metadata meta = cluster.getMetadata();

        System.out.println("Connected to Cassandra: " + meta.getClusterName());

        for(Host host:meta.getAllHosts()) {
            System.out.println("Datacenter: " + host.getDatacenter() + ", Host: " + host.getAddress() + ", Rack: " + host.getRack());
        }
    }

}
