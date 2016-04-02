package club.broking.cdn.setup;

import club.broking.cdn.services.CassandraService;

public class Bootstrap {

    public void setup() {
        final CassandraService cassandraService = CassandraService.getInstance();
        Jetty jetty = new Jetty();

        cassandraService.connect("localhost");
        cassandraService.createSchema();
        jetty.setup();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                cassandraService.close();
            }
        });
    }

}
