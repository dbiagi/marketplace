package org.dbiagi.marketplace.listener;

import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryListener extends AbstractRepositoryEventListener<User> {

    private UserService userService;

    @Autowired
    UserRepositoryListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void onBeforeCreate(User user) {
        userService.encryptPassword(user);
    }

    @Override
    protected void onBeforeSave(User entity) {
        super.onBeforeSave(entity);
    }
}
