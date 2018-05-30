package org.dbiagi.marketplace.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class ListingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String slug;

    @ManyToOne
    private ListingCategory parent;

    @OneToMany(mappedBy = "parent")
    private Collection<ListingCategory> children = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ListingCategory getParent() {
        return parent;
    }

    public void setParent(ListingCategory parent) {
        this.parent = parent;
    }

    public Collection<ListingCategory> getChildren() {
        return children;
    }

    public void addChild(ListingCategory listingCategory) {
        if(!children.contains(listingCategory)) {
            children.add(listingCategory);

            listingCategory.setParent(this);
        }
    }
}
