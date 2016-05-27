package org.servicestation.config.app;

import org.apache.commons.dbcp2.BasicDataSource;
import org.servicestation.dao.*;
import org.servicestation.dao.impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ApplicationDaoConfiguration {

    private static final Integer INITIAL_POOL_SIZE = 10;

    @Value("${database.url}")
    private String databaseUrl;

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
    public IEmailVerificationDao emailVerificationDao() {
        return new EmailVerificationDaoImpl();
    }

    @Bean
    public IPasswordRecoveryDao passwordRecoveryDao() {
        return new PasswordDaoImpl();
    }

}
