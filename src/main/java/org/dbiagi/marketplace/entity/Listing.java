package org.dbiagi.marketplace.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Listing {
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
    private List<ListingCategory> categories = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Collection<ListingCategory> getCategories() {
        return categories;
    }

    public void addCategory(ListingCategory listingCategory) {
        if (!categories.contains(listingCategory)) {
            categories.add(listingCategory);
        }
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
