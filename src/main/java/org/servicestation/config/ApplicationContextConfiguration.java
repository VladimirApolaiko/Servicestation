package org.servicestation.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.servicestation.config.provider.CustomPreAuthProvider;
import org.servicestation.dao.*;
import org.servicestation.dao.impl.*;
import org.servicestation.resources.impl.TestResourceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ApplicationContextConfiguration {

    private static final Integer INITIAL_POOL_SIZE = 10;

    private String DATABASE_URL = System.getenv("DATABASE_URL");

    @Bean
    public BasicDataSource basicDataSource() throws URISyntaxException {
        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = new URI(DATABASE_URL);
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
    public IProfileDao profileDao() {
        return new ProfileDaoImpl();
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
    public IMechanicOrder mechanicOrder() {
        return new MechanicOrderImpl();
    }

    @Bean
    public TestResourceImpl helloResource() {
        return new TestResourceImpl();
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
    public CustomPreAuthProvider customPreAuthProvider(){
        return new CustomPreAuthProvider();
    }

}
