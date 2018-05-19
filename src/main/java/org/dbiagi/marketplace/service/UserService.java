package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Validator validator;

    @Autowired
    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    private void encryptPassword(User user) {
        if (user.getPlainPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPlainPassword()));
            user.setPlainPassword(null);
        }
    }

    private void validate(User user, Class<?> group) throws EntityValidationException {
        Set<ConstraintViolation<User>> violations = group == null ? validator.validate(user) : validator.validate(user, group);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<User>().create(violations);
        }
    }

    public User save(User user) throws EntityValidationException {
        encryptPassword(user);

        validate(user, null);

        return userRepository.save(user);
    }

    public User save(User user, Class<?> validationGroup) throws EntityValidationException {
        validate(user, validationGroup);

        return save(user);
    }

    public void save(List<User> users) {
        users.forEach(this::encryptPassword);

        userRepository.save(users);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void update(Long id, Map<String, Object> fields) throws ResourceNotFoundException, EntityValidationException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        fields.forEach((field, value) -> {
            switch (field) {
                case "name":
                    user.setName((String) value);
                    break;
                case "email":
                    user.setEmail((String) value);
                    break;
                case "phone":
                    user.setPhone((String) value);
                    break;
                case "cellphone":
                    user.setCellphone((String) value);
                    break;
            }
        });

        validate(user, null);

        userRepository.save(user);
    }

    public User find(Long id) throws ResourceNotFoundException {
        User user = userRepository.findOne(id);

        if (user == null) {
            throw new ResourceNotFoundException(id);
        }

        return user;
    }

    public User findByEmailOrUsername(String emailOrUsername) {
        return userRepository.findByEmailOrUsername(emailOrUsername);
    }
}
