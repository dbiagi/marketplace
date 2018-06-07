package org.dbiagi.classification.model;

import java.util.Collection;

public interface CategoryInterface extends BaseModel {
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
