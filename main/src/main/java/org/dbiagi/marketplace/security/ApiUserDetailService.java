package org.dbiagi.marketplace.security;

import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private UserService userService;

    @Autowired
    ApiUserDetailService(UserService userRepository) {
        this.userService = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmailOrUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found for email %s", username));
        }

        return user;
    }
}
