package org.dbiagi.marketplace.model.classification;

import java.util.Collection;

public interface CategoryInterface {
    String getName();

    void setName(String string);

    String getSlug();

    void setSlug(String slug);

    CategoryInterface getParent();

    void setParent(CategoryInterface parent);

    Collection<? extends CategoryInterface> getChildren();

    void setChildren(Collection<? extends CategoryInterface> children);

    void addChild(CategoryInterface child);
}
