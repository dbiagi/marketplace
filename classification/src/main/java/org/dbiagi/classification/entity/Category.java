package org.dbiagi.classification.entity;

import org.dbiagi.classification.model.CategoryInterface;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
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

    private Category parent;

    private List<Category> children;

    @Override
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
