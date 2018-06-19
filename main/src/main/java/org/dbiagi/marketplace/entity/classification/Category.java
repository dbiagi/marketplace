package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.BaseEntity;
import org.dbiagi.marketplace.model.classification.CategoryInterface;
import org.dbiagi.marketplace.model.classification.ContextInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
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
    private List<Category> children = new ArrayList<>();

    @ManyToOne
    private Context context;

    @Override
    public void setContext(ContextInterface context) {
        this.context = (Context) context;
    }

    @Override
    public void setParent(CategoryInterface parent) {
        this.parent = (Category) parent;
    }

    @Override
    public void addChild(CategoryInterface child) {
        if (child instanceof Category && !children.contains(child)) {
            child.setParent(this);

            children.add((Category) child);
        }
    }
}
