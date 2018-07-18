package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.model.classification.ContextInterface;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode()
@Entity
@Data
public class Context implements ContextInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    private boolean enabled = true;

    public Context() {
    }

    public Context(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
