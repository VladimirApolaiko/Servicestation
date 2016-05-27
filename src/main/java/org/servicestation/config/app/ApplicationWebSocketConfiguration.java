package org.servicestation.config.app;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.servicestation.dao.*;
import org.servicestation.dao.impl.*;
import org.servicestation.resources.ICarResource;
import org.servicestation.resources.IEmailVerificationResource;
import org.servicestation.resources.IPasswordRecoveryResource;
import org.servicestation.resources.impl.*;
import org.servicestation.resources.managers.*;
import org.servicestation.resources.managers.impl.*;
import org.servicestation.resources.managers.impl.MailManager;
import org.servicestation.resources.sokets.WebSocketEventEmitter;
import org.servicestation.resources.sokets.WebSocketExample;
import org.servicestation.resources.sokets.handlers.GetAllOrdersWebSocketEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@PropertySource({"classpath:database.properties", "classpath:mail.properties", "classpath:app.properties"})
public class ApplicationWebSocketConfiguration {

    @Autowired
    private WebApplicationContext webAppContext;

    private ServerContainer container;

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
        container = (ServerContainer) webAppContext.getServletContext().
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

