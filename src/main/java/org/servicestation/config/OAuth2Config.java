package org.servicestation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OAuth2Config {

    private static final String RESOURCE_ID = "xxx";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and().authorizeRequests().anyRequest().authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Value("353b302c44574f565045687e534e7d6a")
        private String clientName;

        @Value("286924697e615a672a646a493545646c")
        private String clientSecret;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private DataSource dataSource;


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.setClientDetailsService(clientDetailsService()); // Service which responsible for client (where clients saved)
            endpoints.tokenServices(tokenServices()) //set token services which responsible for place where tokens saved, support refresh token
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager) // Authorization codes for Authorization grant flow
                    .requestFactory(requestFactory())
                    .tokenGranter(tokenGranter()); // specify supported grant types
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService());
        }


        @Bean
        public TokenGranter tokenGranter() {
            final List<TokenGranter> tokenGranters = new ArrayList<>();
            tokenGranters.add(new RefreshTokenGranter(tokenServices(), clientDetailsService(), requestFactory()));
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices(), clientDetailsService(), requestFactory()));

            return new CompositeTokenGranter(tokenGranters);
        }

        @Bean
        public ClientDetailsService clientDetailsService() {
            final InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
            builder.withClient(clientName)
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret(clientSecret);
            try {
                return builder.build();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Bean
        public AuthorizationServerTokenServices tokenServices() {
            final DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setClientDetailsService(clientDetailsService());
            tokenServices.setTokenStore(tokenStore());
            tokenServices.setAuthenticationManager(authenticationManager);
            return tokenServices;
        }

        @Bean
        public OAuth2RequestFactory requestFactory() {
            return new DefaultOAuth2RequestFactory(clientDetailsService());
        }

    }
}
