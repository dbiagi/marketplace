package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

abstract class BaseController {
    User getLoggedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
