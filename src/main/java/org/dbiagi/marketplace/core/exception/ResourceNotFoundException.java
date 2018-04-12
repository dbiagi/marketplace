package org.dbiagi.marketplace.core.exception;

public class ResourceNotFoundException extends Exception {
    private Long id;

    public ResourceNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

