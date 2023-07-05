package org.lessons.springlibrary.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    /* per definireun AuthenticationProvider ho bisogno di:
       -  uno UserDetailService
       -  un PasswordEncoder
    */
    // questo e' lo  UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // questo e' il PasswordEncoder (che deduce l'algoritmo di encoding da una stringa nella password stessa)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // gli setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    /*
        "/" admin e user
        "/edit" admin
        "/create"  admin
        "/offerte" admin
    */

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // definisco la catena di filtri
        http.authorizeHttpRequests()
//                .requestMatchers("/edit/**").hasAuthority("ADMIN")
//                .requestMatchers("/create").hasAuthority("ADMIN")
//                .requestMatchers("/offerte/create/**").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/pizza/**").hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        // disabilitiamo csrf per poter invocare le api da postman
        http.csrf().disable();
        return http.build();
    }

}
