package org.dbiagi.classification.model;

public interface TagInterface extends BaseModel {
    String getName();

    void setName(String name);

    String getSlug();

    void setSlug(String slug);

    ContextInterface getContext();

    void setContext(ContextInterface context);
}
