package org.dbiagi.classification.entity;

import org.dbiagi.classification.model.ContextInterface;

import java.util.Date;

public class Context extends BaseEntity implements ContextInterface {
    private int id;
    private String name;
    private boolean enabled;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int getId() {
        return id;
    }
}
