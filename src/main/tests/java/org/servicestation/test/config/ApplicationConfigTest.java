package org.servicestation.test.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.servicestation.dao.*;
import org.servicestation.dao.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@PropertySource("classpath:test-database.properties")
public class ApplicationConfigTest {

    private static final Integer INITIAL_POOL_SIZE = 10;

    @Value("${database.url}")
    private String databaseUrl;

    @Bean
    public BasicDataSource testBasicDataSource() throws URISyntaxException {
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
    public JdbcTemplate testJdbcTemplate() throws URISyntaxException {
        return new JdbcTemplate(testBasicDataSource());
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
    public IStationOrderDao mechanicOrder() {
        return new StationOrderImpl();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate() throws URISyntaxException {
        return new NamedParameterJdbcTemplate(testBasicDataSource());
    }
}
