package org.dbiagi.marketplace.entity.classification;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    @ManyToOne
    @JsonDeserialize(as = Category.class)
    private Category parent;

    @ManyToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @ManyToOne
    private Context context;

    public void addChild(Category child) {
        if (!children.contains(child)) {
            child.setParent(this);

            children.add(child);
        }
    }
}
