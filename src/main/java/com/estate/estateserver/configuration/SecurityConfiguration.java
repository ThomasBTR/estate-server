package com.estate.estateserver.configuration;


import org.hibernate.dialect.Database;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /**
     * .
     *
     * @param httpSecurity the http
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity.authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("/resources/**", "/signup", "/about").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .formLogin()
                .and()
                .oauth2Login()
                .and()
                .logout().deleteCookies("JSESSIONID");
        return httpSecurity.build();
    }

    /**
     * Password encoder password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
