/*package com.example.gestionhotel.Service;

import com.example.gestionhotel.Models.Users;
import com.example.gestionhotel.Repository.UserRepository;
import com.example.gestionhotel.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService  {

    @Autowired
    private UserRepository repo;

    public Users login(String username, String password) {
        Users user = repo.findByUsernameAndPassword(username, password);
        return user;
    }
}
/*
 */


