package org.dbiagi.marketplace.model.classification;

import java.util.Collection;

public interface CategoryInterface {
    ContextInterface getContext();

    void setContext(ContextInterface context);

    String getName();

    void setName(String string);

    String getSlug();

    void setSlug(String slug);

    CategoryInterface getParent();

    void setParent(CategoryInterface parent);

    Collection<? extends CategoryInterface> getChildren();

    void addChild(CategoryInterface child);
}
