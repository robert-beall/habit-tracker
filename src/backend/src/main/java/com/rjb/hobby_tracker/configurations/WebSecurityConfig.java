package com.rjb.hobby_tracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.rjb.hobby_tracker.auth.UnauthorizedEntrypoint;
import com.rjb.hobby_tracker.auth.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private UnauthorizedEntrypoint unauthorizedEntrypoint;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, UnauthorizedEntrypoint unauthorizedEntrypoint) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.unauthorizedEntrypoint = unauthorizedEntrypoint;
    }

    /**
     * Establishes a security filter chain bean handled by Spring. This bean specifies 
     * which endpoints are and are not authenticated along with login and logout behavior.
     * 
     * Here you can also specify a custom login page for application that use Thymeleaf 
     * templates as well as custom logout behavior.
     * 
     * @param http
     * @return DefaultSecurityFilterChain
     * @throws Exception
     */
    @Bean
    @Order
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/auth/**").permitAll() // Specify unauthenticated requests
                                .anyRequest().authenticated() // Specify authenticated requests
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                ) // permit all requests after login
                .logout(LogoutConfigurer::permitAll)
                .csrf(csrf -> csrf.disable()) // disable csrf for testing/development (fix later)
                .httpBasic(httpSec -> httpSec.authenticationEntryPoint(unauthorizedEntrypoint)).userDetailsService(userDetailsServiceImpl); // Set this to enable basic auth

		return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
