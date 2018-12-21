package org.dbiagi.marketplace.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

    @Column(unique = true)
    @NotEmpty
    private String slug;

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


