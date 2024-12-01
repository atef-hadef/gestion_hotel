package com.example.gestionhotel.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/dashboardr","/dash", "/chambre","/modifier/{id}").authenticated() // Authentification requise pour /dashboard
                        .anyRequest().permitAll() // Toutes les autres routes sont accessibles
                )
                .formLogin(form -> form
                        .loginPage("/login") // Spécifie la page de connexion
                        .defaultSuccessUrl("/dash", true) // Redirige après connexion
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de déconnexion
                        .logoutSuccessUrl("/login") // Redirection après déconnexion
                        .invalidateHttpSession(true) // Invalide la session
                        .deleteCookies("JSESSIONID") // Supprime les cookies d'authentification
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout") // Désactiver la protection CSRF pour /logout
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
