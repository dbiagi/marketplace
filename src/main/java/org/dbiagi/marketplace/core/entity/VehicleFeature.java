package org.dbiagi.marketplace.core.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class VehicleFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vehicle.TypeEnum type;

    public VehicleFeature() {
    }

    public VehicleFeature(String name, Vehicle.TypeEnum type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle.TypeEnum getType() {
        return type;
    }

    public void setType(Vehicle.TypeEnum type) {
        this.type = type;
    }
}
