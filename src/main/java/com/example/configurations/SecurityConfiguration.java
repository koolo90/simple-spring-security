package com.example.configurations;

import com.example.services.AdminCheckService;
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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final Logger log = LogManager.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AdminCheckService adminCheckService) throws Exception {
        log.info("Configuring own security filter chain...");
        if(!adminCheckService.isAdminInitialized()) {
            log.info("Admin was not initalized, sealing instance for admin initalization.");
            return http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/init-admin", "/css/**", "/js/**", "/webjars/**").permitAll()
                    .anyRequest().denyAll())
                    .formLogin(AbstractHttpConfigurer::disable)
                    .build();

        }

        log.info("Configuring classing filter chain");
        return http.authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/login", "/logout", "/register", "/css/**", "/js/**", "/webjars/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.logoutUrl("/logout").permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
