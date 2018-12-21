package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Context extends BaseEntity {
    @NotEmpty
    private String name;

    private boolean enabled = true;

    public Context(){}

    public Context(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
