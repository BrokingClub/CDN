package club.broking.cdn.setup;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Jetty {

    public void setup() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setPort(80);
        server.setConnectors(new Connector[]{ connector });

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");
        //context.addServlet(HelloServlet.class, "/hello");
        //context.addServlet(AsyncEchoServlet.class, "/echo/*");

        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });

        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
