package com.domeastudio.mappingo.servers.microservice.surveying.config;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.impl.UserDetailServiceImpl;
import com.domeastudio.mappingo.servers.microservice.surveying.filter.JWTAuthenticationFilter;
import com.domeastudio.mappingo.servers.microservice.surveying.filter.JWTLoginFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConfigurationProperties("spring.white-list")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private String[] serviceFilter;

    @Bean
    UserDetailsService customUserService() {
        return new UserDetailServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.OPTIONS,new String[]{
//                        "/swagger*/*"
//                }).permitAll()
                .antMatchers(HttpMethod.POST, serviceFilter).permitAll()
                .anyRequest().authenticated().and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTLoginFilter("/register", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    public String[] getServiceFilter() {
        return serviceFilter;
    }

    public void setServiceFilter(String[] serviceFilter) {
        this.serviceFilter = serviceFilter;
    }
}
