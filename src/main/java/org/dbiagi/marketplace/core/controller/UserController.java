package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
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
    public User post(@RequestBody @Validated User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Map<String, Object> fields) throws ResourceNotFoundException {
        userService.update(id, fields);
    }
}
