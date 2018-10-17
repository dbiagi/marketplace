package org.dbiagi.marketplace.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Listing extends BaseEntity {
    @NotEmpty
    private String title;

    private String shortDescription;

    private String longDescription;

    private boolean active = true;

    private boolean featured = false;

    // @TODO Create projection where slug is not required, and add NotEmpty annotation here
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
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "listing_x_tag",
        joinColumns = {@JoinColumn(name = "listing_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();
}


