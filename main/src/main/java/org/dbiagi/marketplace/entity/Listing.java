package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.model.ListingInterface;
import org.dbiagi.marketplace.model.StoreInterface;
import org.dbiagi.marketplace.model.classification.CategoryInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode()
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
public class Listing implements ListingInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    private String shortDescription;

    private String longDescription;

    private boolean active = true;

    private boolean featured = false;

    private TemporalInfo temporalInfo = new TemporalInfo();

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
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "listing_x_tag",
        joinColumns = {@JoinColumn(name = "listing_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags = new HashSet<>();

    @Override
    public void addCategory(CategoryInterface category) {
        if (category instanceof Category && !categories.contains(category)) {
            categories.add((Category) category);
        }
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    @JsonDeserialize(as = Store.class)
    public void setStore(StoreInterface store) {
        if (store instanceof Store) {
            this.store = (Store) store;
        }
    }

    @Override
    public void addTag(TagInterface tag) {
        if (tag instanceof Tag && !tags.contains(tag)) {
            tags.add((Tag) tag);
        }
    }

    @Override
    public boolean isFeatured() {
        return featured;
    }
}
