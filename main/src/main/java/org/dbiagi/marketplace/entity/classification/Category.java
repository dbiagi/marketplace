package org.dbiagi.marketplace.entity.classification;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.dbiagi.marketplace.entity.Timestampable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    private Timestampable timestampable = new Timestampable();

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
