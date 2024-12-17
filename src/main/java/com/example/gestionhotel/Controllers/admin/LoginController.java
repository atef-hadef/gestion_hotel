package com.example.gestionhotel.Controllers.admin;

import com.example.gestionhotel.Models.Admin;
import com.example.gestionhotel.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private AdminRepository adminRepository;


    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "VueAdmin/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Chercher l'utilisateur dans la base de données
        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        if (admin == null || !passwordEncoder.matches(password, admin.getPassword())) {

            logger.warn("Tentative de connexion échouée pour : {}", username);

            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
            return "VueAdmin/login";
        }


        logger.info("Connexion réussie pour : {}", username);


        model.addAttribute("admin", admin);
        return "redirect:/dash";
    }


}
