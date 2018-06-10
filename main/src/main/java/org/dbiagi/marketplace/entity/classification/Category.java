package org.dbiagi.marketplace.entity.classification;

import org.dbiagi.marketplace.entity.BaseEntity;
import org.dbiagi.marketplace.model.classification.CategoryInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Category extends BaseEntity implements CategoryInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    @ManyToOne
    private Category parent;

    @ManyToMany(mappedBy = "parent")
    private List<Category> children;

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public Category getParent() {
        return parent;
    }

    @Override
    public void setParent(CategoryInterface parent) {
        this.parent = (Category) parent;
    }

    @Override
    public Collection<? extends CategoryInterface> getChildren() {
        return children;
    }

    @Override
    public void setChildren(Collection<? extends CategoryInterface> children) {
        this.children = new ArrayList<>();
        children.forEach(c -> this.children.add((Category) c));
    }

    @Override
    public void addChild(CategoryInterface child) {
        if (child instanceof Category && !children.contains(child)) {
            child.setParent(this);

            children.add((Category) child);
        }
    }
}
