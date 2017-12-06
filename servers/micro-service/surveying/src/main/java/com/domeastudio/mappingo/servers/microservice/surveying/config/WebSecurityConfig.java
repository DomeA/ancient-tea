package com.domeastudio.mappingo.servers.microservice.surveying.config;

import com.domeastudio.mappingo.servers.microservice.surveying.filter.JWTAuthenticationFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("spring.white-list")

public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    private String[] serviceFilter;

//    @Bean
//    public FilterRegistrationBean basicFilterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        JWTAuthenticationFilter httpBasicFilter = new JWTAuthenticationFilter();
//        registrationBean.setFilter(httpBasicFilter);
//        List<String> urlPatterns = new ArrayList<>();
//        for(String str : serviceFilter){
//            urlPatterns.add(str);
//        }
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*"); // 允许跨域请求
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        JWTAuthenticationFilter httpBearerFilter = new JWTAuthenticationFilter();
        registrationBean.setFilter(httpBearerFilter);
        List<String> urlPatterns = new ArrayList<>();
        for (String str : serviceFilter) {
            urlPatterns.add(str);
        }
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    public String[] getServiceFilter() {
        return serviceFilter;
    }

    public void setServiceFilter(String[] serviceFilter) {
        this.serviceFilter = serviceFilter;
    }
}
