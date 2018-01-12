package org.dbiagi.marketplace.core.entity;

import javax.persistence.*;

@Entity
public class Vehicle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    private String version;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public enum TypeEnum {
        BOAT,
        CAR,
        MOTORCICLE,
        TRUCK,
    }

    public enum FuelEnum {
        DIESEL,
        ETHANOL,
        FLEX,
        GAS
    }

    @Override
    public String toString() {
        return String.format("Vehicle[id=%d, type=%s, brand=%s, model=%s, version=%s]",
                id, type, brand, model, version);
    }
}
