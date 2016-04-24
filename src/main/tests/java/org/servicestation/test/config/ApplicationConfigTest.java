package org.servicestation.test.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ApplicationConfigTest {

    private static final Integer INITIAL_POOL_SIZE = 10;

    private String DATABASE_URL = System.getenv("DATABASE_URL");

    @Bean
    public BasicDataSource testBasicDataSource() throws URISyntaxException {
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
    public JdbcTemplate testJdbcTemplate() throws URISyntaxException {
        return new JdbcTemplate(testBasicDataSource());
    }
}
