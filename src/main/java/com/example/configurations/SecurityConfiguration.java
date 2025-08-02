package com.example.configurations;

import com.example.initializers.AdminInitializationChecker;
import com.example.initializers.AdminRedirectFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final Logger log = LogManager.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AdminInitializationChecker adminInitializationChecker, AdminRedirectFilter adminRedirectFilter) throws Exception {
        log.info("Configuring own security filter chain...");
        boolean adminInitialized = adminInitializationChecker.adminExists();

        if (adminInitialized) {
            log.info("Configuring classing filter chain");
            return http.addFilterBefore(adminRedirectFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(
                            auth -> auth
                                    .requestMatchers("/css/**", "/js/**", "/webjars/**").permitAll()
                                    .requestMatchers("/login", "/logout", "/register").permitAll()
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                    ).formLogin(form -> form.loginPage("/login").permitAll())
                    .logout(logout -> logout.logoutUrl("/logout").permitAll())
                    .build();
        } else {
            log.info("Admin was not initialized, sealing instance for admin initialization.");
            return http.addFilterBefore(adminRedirectFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/css/**", "/js/**", "/webjars/**").permitAll()
                            .requestMatchers("/init-admin", "/init-reboot").permitAll()
                            .anyRequest().denyAll()
                    ).formLogin(AbstractHttpConfigurer::disable).build();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
