package org.dbiagi.marketplace.core.security;

import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    ApiUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found for email %s", username));
        }

        return new ApiUserDetail(user);
    }
}
