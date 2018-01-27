package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        encryptPassword(user);

        userRepository.save(user);
    }

    public void save(List<User> users) {
        users.forEach(this::encryptPassword);

        userRepository.save(users);
    }

    private void encryptPassword(User user) {
        if (user.getPlainPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
        }
    }
}
