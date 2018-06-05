package org.dbiagi.classification.model;

public interface CategoryInterface extends BaseModel {
    String getName();

    void setName();

    String getSlug();

    void setSlug(String slug);
}
