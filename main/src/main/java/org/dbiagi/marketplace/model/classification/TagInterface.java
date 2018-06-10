package org.dbiagi.marketplace.model.classification;

public interface TagInterface {
    String getName();

    void setName(String name);

    String getSlug();

    void setSlug(String slug);

    ContextInterface getContext();

    void setContext(ContextInterface context);
}
