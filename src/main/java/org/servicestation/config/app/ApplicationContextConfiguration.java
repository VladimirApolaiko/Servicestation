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
public class ApplicationContextConfiguration {

    @Autowired
    private BasicDataSource basicDataSource;


    @Bean(name = "springSecurityFilterChain")
    public DelegatingFilterProxy springSecurityFilterChain() {
        return new DelegatingFilterProxy();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }

    @Bean
    public UserDetailsService userDetailsService() throws URISyntaxException {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(basicDataSource);
        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

