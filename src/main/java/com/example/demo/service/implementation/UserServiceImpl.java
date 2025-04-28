package com.example.demo.service.implementation;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User save(UserDTO userDTO) {
        User user = new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword())
                , userDTO.getRole(), userDTO.getFullname());
        return userRepository.save(user);
    }

    @Override
    public void InitDatabase() {
        if (userRepository.findByEmail("admin@gmail.com") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullname("admin");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        } else{
            System.out.println("Admin already exists");
        }

        if (userRepository.findByEmail("admin@gmail.com") == null) {
            User user = new User();
            user.setEmail("khanhbl9848@gmail.com");
            user.setPassword(passwordEncoder.encode("pklinh123"));
            user.setFullname("PhamLinh");
            user.setRole("USER");
            userRepository.save(user);
        } else{
            System.out.println("User already exists");
        }
    }
}
