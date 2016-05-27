package org.servicestation.config.app;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

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

