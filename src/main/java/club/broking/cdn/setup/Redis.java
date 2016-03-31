package club.broking.cdn.setup;

import redis.clients.jedis.Jedis;

public class Redis {

    public void setup() {
        Jedis jedis = new Jedis("localhost");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");

        System.out.println(value);
    }

}
