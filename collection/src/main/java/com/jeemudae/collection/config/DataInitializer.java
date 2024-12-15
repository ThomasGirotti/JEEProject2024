package com.jeemudae.collection.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jeemudae.collection.repository.Role;
import com.jeemudae.collection.repository.User;
import com.jeemudae.collection.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("budae").isEmpty()) {
            User admin = new User();
            admin.setUsername("budae");
            admin.setPassword(passwordEncoder.encode("budaeadmin-1656"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}
