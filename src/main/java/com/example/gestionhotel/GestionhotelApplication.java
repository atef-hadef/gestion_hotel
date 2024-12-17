package com.example.gestionhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionhotelApplication {
	public static void main(String[] args){
		SpringApplication.run(GestionhotelApplication.class, args);
	}{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456789"; // Remplacez par votre mot de passe
		String hashedPassword = passwordEncoder.encode(rawPassword);
		System.out.println("Mot de passe haché : " + hashedPassword);
	}


}
