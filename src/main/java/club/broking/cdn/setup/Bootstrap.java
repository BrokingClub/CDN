package club.broking.cdn.setup;

public class Bootstrap {

    public void setup() {
        Cassandra cassandra = new Cassandra();
        Jetty jetty = new Jetty();

        cassandra.setup();
        jetty.setup();
    }

}
