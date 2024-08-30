package com.api.gateway.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain (ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity
                //Deprecated
                /*.csrf()
                .disable()*/
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        exchange -> exchange
                                .pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated()
                        )
                //Deprecated
                /*.oauth2ResourceServer(
                                ServerHttpSecurity.OAuth2ResourceServerSpec::jwt
                );*/
                .oauth2ResourceServer(
                        oAuth2 -> oAuth2.jwt(withDefaults())
                );
        return serverHttpSecurity.build();
    }
}
