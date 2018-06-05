package org.dbiagi.classification.model;

public interface ContextInterface extends BaseModel {
    String getName();

    void setName(String name);

    boolean isEnabled();

    void setEnabled(boolean enabled);
}
