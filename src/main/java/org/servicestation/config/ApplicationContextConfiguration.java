package org.servicestation.config;

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

    private static final Integer INITIAL_POOL_SIZE = 1;

    @Value("${database.url}")
    private String databaseUrl;

    @Autowired
    private WebApplicationContext webAppContext;

    private ServerContainer container;

    @Bean
    public BasicDataSource basicDataSource() throws URISyntaxException {
        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = new URI(databaseUrl);
        dataSource.setUrl("jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        if (dbUri.getUserInfo() != null) {
            dataSource.setUsername(dbUri.getUserInfo().split(":")[0]);
            dataSource.setPassword(dbUri.getUserInfo().split(":")[1]);
        }
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setInitialSize(INITIAL_POOL_SIZE);

        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() throws URISyntaxException {
        return new NamedParameterJdbcTemplate(basicDataSource());
    }

    @Bean
    public IStationDao stationDao() {
        return new StationDaoImpl();
    }

    @Bean
    public ICarDao carsDao() {
        return new CarDaoImpl();
    }

    @Bean
    public IMechanicDao mechanicDao() {
        return new MechanicDaoImpl();
    }

    @Bean
    public IOrderDao orderDao() {
        return new OrderDaoImpl();
    }

    @Bean
    public IUserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public IAuthoritiesDao authoritiesDao() {
        return new AuthorityDaoImpl();
    }

    @Bean
    public IMechanicOrder mechanicOrder() {
        return new MechanicOrderImpl();
    }

    @Bean
    public TestResourceImpl helloResource() {
        return new TestResourceImpl();
    }

    @Bean
    public CarResourceImpl carResource() {
        return new CarResourceImpl();
    }

    @Bean
    public IEmailVerificationDao emailVerificationDao() {
        return new EmailVerificationDaoImpl();
    }

    @Bean
    public IPasswordRecoveryDao passwordRecoveryDao() {
        return new PasswordDaoImpl();
    }

    @Bean
    public UserResourceImpl userResource() {
        return new UserResourceImpl();
    }

    @Bean
    public IEmailVerificationResource emailVerificationResource() {
        return new EmailVerificationResourceImpl();
    }

    @Bean
    public IPasswordRecoveryResource passwordRecoveryResource() {
        return new PasswordRecoveryResourceImpl();
    }

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
        jdbcUserDetailsManager.setDataSource(basicDataSource());
        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserManager userManager() {
        return new UserManager();
    }

    @Bean
    public IAuthoritiesManager authoritiesManager() {
        return new AuthoritiesManager();
    }

    @Bean
    public IEmailVerificationManager emailVerificationManager() {
        return new EmailVerificationManagerImpl();
    }

    @Bean
    public ICarManager carManager() {
        return new CarManagerImpl();
    }

    @Bean
    public IPasswordRecoveryManager passwordRecoveryManager() {
        return new PasswordRecoveryManagerImpl();
    }

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

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", " org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }

    @Bean
    public MailManager mailManager() {
        return new MailManager();
    }
}

