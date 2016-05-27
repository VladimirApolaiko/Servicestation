package org.servicestation.config.app;

import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.servicestation.resources.sokets.WebSocketEventEmitter;
import org.servicestation.resources.sokets.WebSocketExample;
import org.servicestation.resources.sokets.handlers.GetAllOrdersWebSocketEventHandler;
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
    public WebSocketExample toUpper356Socket() {
        return new WebSocketExample();
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
                        WebSocketExample.class,
                        WebSocketExample.class.getAnnotation(ServerEndpoint.class)) {
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
    public GetAllOrdersWebSocketEventHandler getAllOrdersWebSocketEventHandler() {
        return new GetAllOrdersWebSocketEventHandler();
    }


}

