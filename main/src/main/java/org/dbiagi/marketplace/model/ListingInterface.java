package org.dbiagi.marketplace.model;

import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.model.classification.CategoryInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;

import java.util.Collection;

public interface ListingInterface {
    String getTitle();

    void setTitle(String title);

    String getShortDescription();

    void setShortDescription(String shortDescription);

    String getLongDescription();

    void setLongDescription(String longDescription);

    String getSlug();

    void setSlug(String slug);

    Collection<? extends CategoryInterface> getCategories();

    void addCategory(CategoryInterface category);

    Boolean isActive();

    void setActive(Boolean active);

    StoreInterface getStore();

    void setStore(StoreInterface store);

    Collection<? extends Tag> getTags();

    void addTag(TagInterface tag);
}
