package org.servicestation;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {
    private static final String RESOURCE_BASE = "src/main/webapp/";
    private static final String DEFAULT_SERVER_PORT = "5000";

    public static void main(String[] args) throws Exception {



        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = DEFAULT_SERVER_PORT;
        }

        Server server = new Server(Integer.valueOf(webPort));

        WebAppContext root = new WebAppContext();
        root.setContextPath("/");
        root.setParentLoaderPriority(true);
        root.setResourceBase(RESOURCE_BASE);
        root.setDescriptor(RESOURCE_BASE + "/WEB-INF/web.xml");

        server.setHandler(root);
        server.start();
        server.join();
    }
}
