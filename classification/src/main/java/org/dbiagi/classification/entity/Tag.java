package org.dbiagi.classification.entity;

import org.dbiagi.classification.model.ContextInterface;
import org.dbiagi.classification.model.TagInterface;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Tag extends BaseEntity implements TagInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String slug;

    @ManyToOne
    private Context context;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public ContextInterface getContext() {
        return context;
    }

    @Override
    public void setContext(ContextInterface context) {
        this.context = (Context) context;
    }
}
