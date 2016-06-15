package org.servicestation.config.app;

import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.servicestation.resources.sokets.WebSocketEndPoint;
import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.sokets.WebSocketEventEmitter;
import org.servicestation.resources.sokets.WebSocketEventHandler;
import org.servicestation.resources.sokets.handlers.OrderChangedWebSocketEventHandler;
import org.servicestation.resources.sokets.handlers.StationOrdersWebSocketEventHandler;
import org.servicestation.resources.sokets.handlers.UserOrdersWebSocketEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
@PropertySource({"classpath:database.properties", "classpath:mail.properties", "classpath:app.properties"})
public class ApplicationWebSocketConfiguration {

    @Autowired
    private WebApplicationContext webAppContext;

    @Bean
    public WebSocketEndPoint toUpper356Socket() {
        return new WebSocketEndPoint();
    }

    @Bean
    public ServerEndpointConfig.Configurator configurator() {
        class SpringServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
            @Override
            public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
                return webAppContext.getAutowireCapableBeanFactory().getBean(endpointClass);
            }
        }

        return new SpringServerEndpointConfigurator();
    }

    @PostConstruct
    public void init() throws DeploymentException {
        ServerContainer container = (ServerContainer) webAppContext.getServletContext().
                getAttribute(javax.websocket.server.ServerContainer.class.getName());

        container.addEndpoint(
                new AnnotatedServerEndpointConfig(
                        container,
                        WebSocketEndPoint.class,
                        WebSocketEndPoint.class.getAnnotation(ServerEndpoint.class)) {
                    @Override
                    public Configurator getConfigurator() {
                        return configurator();
                    }
                });
    }

    @Bean
    public WebSocketEventEmitter deviceSessionHandler() {
        return new WebSocketEventEmitter();
    }

    @Bean
    @Scope("prototype")
    public WebSocketEventHandler getAllStationOrdersWebSocketEventHandler() {
        WebSocketEventHandler webSocketEventHandler = new StationOrdersWebSocketEventHandler();
        webSocketEventHandler.setAction(WebSocketEvent.GET_ALL_STATION_ORDERS);
        return webSocketEventHandler;
    }


    @Bean
    @Scope("prototype")
    public WebSocketEventHandler getAllUserOrdersWebSocketEventHandler() {
        WebSocketEventHandler webSocketEventHandler = new UserOrdersWebSocketEventHandler();
        webSocketEventHandler.setAction(WebSocketEvent.GET_ALL_USER_ORDERS);
        return webSocketEventHandler;
    }

    @Bean
    @Scope("prototype")
    public WebSocketEventHandler orderChangedWebSocketEventHandler() {
        WebSocketEventHandler webSocketEventHandler = new OrderChangedWebSocketEventHandler();
        webSocketEventHandler.setAction(WebSocketEvent.ORDERS_CHANGED);

        return webSocketEventHandler;
    }


}

