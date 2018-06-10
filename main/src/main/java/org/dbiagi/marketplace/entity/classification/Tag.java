package org.dbiagi.marketplace.entity.classification;

import org.dbiagi.marketplace.entity.BaseEntity;
import org.dbiagi.marketplace.model.classification.ContextInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;

import javax.persistence.*;

@Entity
public class Tag extends BaseEntity implements TagInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String slug;

    @ManyToOne
    private Context context;

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
