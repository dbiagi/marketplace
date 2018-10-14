package org.dbiagi.marketplace.entity.classification;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Context {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    private boolean enabled = true;

    public Context(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
