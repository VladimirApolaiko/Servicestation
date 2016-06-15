package org.servicestation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final String RESOURCE_BASE = "src/main/webapp/";
    private static final String DEFAULT_SERVER_PORT = "5000";
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = DEFAULT_SERVER_PORT;
        }

        Server server = new Server(Integer.parseInt(webPort));

        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setParentLoaderPriority(true);
        root.setResourceBase(RESOURCE_BASE);
        root.setDescriptor(RESOURCE_BASE + "/WEB-INF/web.xml");

        server.setHandler(root);

        //Create web socket container
        ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(root);
        /*serverContainer.addEndpoint(WebSocketEndPoint.class);*/

        server.start();
        LOGGER.info("Start application on port {}", webPort);
        server.join();
    }
}
