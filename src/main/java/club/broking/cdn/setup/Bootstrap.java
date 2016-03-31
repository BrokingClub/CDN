package club.broking.cdn.setup;

public class Bootstrap {

    public void setup() {
        Redis redis = new Redis();
        Jetty jetty = new Jetty();

        //redis.setup();
        jetty.setup();
    }

}
