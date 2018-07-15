package org.dbiagi.marketplace.controller;

import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
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
    public void put(@PathVariable Long id, @RequestBody Map<String, Object> fields)
        throws ResourceNotFoundException, EntityValidationException {
        userService.update(id, fields);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public User post(@RequestBody User user, UsernamePasswordAuthenticationToken token) throws EntityValidationException {
        User requestUser = (User) token.getPrincipal();

        user.setStore(requestUser.getStore());

        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STORE_OWNER')")
    public void delete(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
    }
}
