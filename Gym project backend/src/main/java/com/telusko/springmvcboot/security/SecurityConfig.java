package com.telusko.springmvcboot.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration

public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManagr() {
//        UserDetails shashank = User.builder().username("shashank").password("{noop}shashank").roles("admin").build();
//        UserDetails pranay = User.builder().username("pranay").password("{noop}shashank").roles("user").build();
//        UserDetails manjeet = User.builder().username("manjeet").password("{noop}shashank").roles("contributfffor")
//                .build();
//
//        return new InMemoryUserDetailsManager(shashank, pranay, manjeet);
//
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configure -> configure
                .requestMatchers(HttpMethod.GET,"/test").hasRole("MANAGER"));
        http.httpBasic();
        http.csrf().disable();

        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .antMatchers("/addnewplan").hasAnyRole("user")
//                ).formLogin();
//
//
//        //this is for HTTP Basic Authentication, so that user can send username nd passwrd along with rest request
//        http.httpBasic();
//
//        http       //other configure params.
//                .csrf().disable();
//
//    }

}
