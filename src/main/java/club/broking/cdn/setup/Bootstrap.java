package club.broking.cdn.setup;

import club.broking.cdn.services.CassandraService;

public class Bootstrap {

    public void setup() {
        CassandraService cassandraService = CassandraService.getInstance();
        Jetty jetty = new Jetty();

        cassandraService.connect("localhost");
        cassandraService.createSchema();
        cassandraService.querySchema();
        jetty.setup();
    }

}
