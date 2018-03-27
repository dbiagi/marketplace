package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.find(id);
    }

    @PutMapping("/{id}")
    public void put(@PathVariable Long id, Map<String, Object> fields) throws ResourceNotFoundException {
        userService.update(id, fields);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@RequestBody User user, UsernamePasswordAuthenticationToken token) throws Exception {
        User requestUser = (User) token.getPrincipal();

        user.setStore(requestUser.getStore());

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<User>().create(violations);
        }

        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Map<String, Object> fields) throws ResourceNotFoundException {
        userService.update(id, fields);
    }
}
