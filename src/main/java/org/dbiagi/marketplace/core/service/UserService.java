package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void encryptPassword(User user) {
        if (user.getPlainPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
            user.setPlainPassword(null);
        }
    }

    public User save(User user) {
        encryptPassword(user);

        return userRepository.save(user);
    }

    public void save(List<User> users) {
        users.forEach(this::encryptPassword);

        userRepository.save(users);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void update(Long id, Map<String, Object> fields) throws ResourceNotFoundException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        /* @TODO implement this */
    }

    public User find(Long id) throws ResourceNotFoundException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        return user;
    }
}
