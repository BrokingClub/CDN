package club.broking.cdn.setup;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Jetty {

    public void setup() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setPort(8000);
        server.setConnectors(new Connector[]{ connector });

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");

        ServletHolder holder = context.addServlet(DefaultServlet.class, "/");

        holder.setInitParameter("resourceBase", "./public");
        holder.setInitParameter("dirAllowed", "false");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
