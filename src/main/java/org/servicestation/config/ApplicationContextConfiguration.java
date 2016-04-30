package org.servicestation.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.servicestation.dao.*;
import org.servicestation.dao.impl.*;
import org.servicestation.resources.impl.TestResourceImpl;
import org.servicestation.resources.impl.UserResourceImpl;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.IUserManager;
import org.servicestation.resources.managers.impl.AuthoritiesManager;
import org.servicestation.resources.managers.impl.UserManager;
import org.servicestation.resources.sokets.WebSocketExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:database.properties")
public class ApplicationContextConfiguration {

    private static final Integer INITIAL_POOL_SIZE = 10;


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
    public UserResourceImpl userResource() {
        return new UserResourceImpl();
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
    public WebSocketExample toUpper356Socket() {
        return new WebSocketExample();
    }

    @Bean
    public ServerEndpointConfig.Configurator configurator() {
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

    public class SpringServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
            return webAppContext.getAutowireCapableBeanFactory().getBean(endpointClass);
        }
    }
}

