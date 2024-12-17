package com.example.gestionhotel.Config;

import com.example.gestionhotel.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private AdminService adminService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/dashboardr", "/dash", "/chambre", "/modifier/{id}", "/edit/{id}")
                        .hasRole("ADMIN")  // Autoriser uniquement les utilisateurs avec le rôle ADMIN
                        .anyRequest().permitAll()  // Toutes les autres requêtes sont permises
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Page de login personnalisée
                        .defaultSuccessUrl("/dash", true)  // Page après une connexion réussie
                        .permitAll()  // Permettre l'accès à la page de login à tout le monde
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL pour se déconnecter
                        .logoutSuccessUrl("/login")  // Page après la déconnexion
                        .invalidateHttpSession(true)  // Invalider la session
                        .deleteCookies("JSESSIONID")  // Supprimer les cookies de session
                        .permitAll()  // Permettre l'accès au logout à tout le monde
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout")  // Ignorer la protection CSRF pour l'URL de logout
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Utilisation de BCrypt pour le hash des mots de passe
    }

}
