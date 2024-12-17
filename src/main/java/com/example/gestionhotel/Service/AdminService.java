package com.example.gestionhotel.Service;

import com.example.gestionhotel.Controllers.admin.LoginController;
import com.example.gestionhotel.Models.Admin;  // Modèle de l'Admin utilisateur
import com.example.gestionhotel.Repository.AdminRepository;  // Repository pour accéder à la base de données
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AdminRepository adminRepository;  // Injectez votre repository pour accéder aux admins dans la base de données
    private final PasswordEncoder passwordEncoder;  // Injectez un encodeur de mot de passe pour gérer les mots de passe

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Injection des dépendances via le constructeur
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cherche l'utilisateur dans la base de données
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                 logger.warn("Login failed for user: {}. Error: {}", username);

        // Retourne un objet User avec les informations de l'utilisateur
        return User.builder()
                .username(admin.getUsername())  // Nom d'utilisateur
                .password(admin.getPassword())  // Mot de passe crypté
                .roles("ADMIN")  // Rôle de l'utilisateur (modifiez selon vos besoins)
                .build();
    }

}
