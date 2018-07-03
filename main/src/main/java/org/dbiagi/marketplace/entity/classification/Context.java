package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.TemporalInfo;
import org.dbiagi.marketplace.model.classification.ContextInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Context implements ContextInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    private boolean enabled = true;

    private TemporalInfo temporalInfo;

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
