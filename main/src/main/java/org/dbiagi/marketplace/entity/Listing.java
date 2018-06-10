package org.dbiagi.marketplace.entity;

import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.model.classification.CategoryInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;
import org.dbiagi.marketplace.model.ListingInterface;
import org.dbiagi.marketplace.model.StoreInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Listing extends BaseEntity implements ListingInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    private String shortDescription;

    private String longDescription;

    private Boolean active = true;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String slug;

    @ManyToOne
    @NotNull
    private Store store;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "listing_x_category",
            joinColumns = {@JoinColumn(name = "listing_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name= "listing_x_tag",
            joinColumns = {@JoinColumn(name = "listing_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags = new ArrayList<>();

    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public String getLongDescription() {
        return longDescription;
    }

    @Override
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public Collection<Category> getCategories() {
        return categories;
    }

    @Override
    public void addCategory(CategoryInterface category) {
        if (category instanceof Category) {
            addCategory((Category) category);
        }
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public void setStore(StoreInterface store) {
        if (store instanceof Store) {
            setStore((Store) store);
        }
    }

    @Override
    public Collection<? extends Tag> getTags() {
        return tags;
    }

    @Override
    public void addTag(TagInterface tag) {
        if (tag instanceof Tag && !tags.contains(tag)) {
            tags.add((Tag) tag);
        }
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void addCategory(Category category) {
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }
}
