package com.rjb.hobby_tracker.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/").permitAll() // Specify unauthenticated requests
                                .anyRequest().authenticated() // Specify authenticated requests
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll
                ) // permit all requests after login
                .logout(LogoutConfigurer::permitAll)
                .csrf(csrf -> csrf.disable()) // disable csrf for testing/development (fix later)
                .httpBasic(Customizer.withDefaults()); // Set this to enable basic auth

		return http.build();
	}

    @Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
