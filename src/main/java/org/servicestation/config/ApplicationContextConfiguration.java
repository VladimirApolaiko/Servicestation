package org.servicestation.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.servicestation.dao.impl.UserDaoImpl;
import org.servicestation.resources.impl.HelloResourceImpl;
import org.servicestation.resources.impl.UserResourceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ApplicationContextConfiguration {

    private static final Integer INITIAL_POOL_SIZE = 10;

    @Value("${DATABASE_URL}")
    private String DATABASE_URL;

    @Value("${DATABASE_USERNAME}")
    private String DATABASE_USERNAME;

    @Value("${DATABASE_PASSWORD}")
    private String DATABASE_PASSWORD;

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
    public UserDaoImpl userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws URISyntaxException {
        return new JdbcTemplate(basicDataSource());
    }

    @Bean
    public UserResourceImpl userResource() {
        return new UserResourceImpl();
    }

    @Bean
    public HelloResourceImpl helloResource() {
        return new HelloResourceImpl();
    }
}
