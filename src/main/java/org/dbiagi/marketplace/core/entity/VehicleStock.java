package org.dbiagi.marketplace.core.entity;

import org.dbiagi.marketplace.geolocation.entity.Location;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class VehicleStock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @ManyToMany
    @JoinTable(name = "vehiclestock_x_features")
    private List<VehicleFeature> features = new ArrayList<>();

    @Min(1)
    @Max(999)
    private int quantity;

    @NotNull
    private Double price;

    @Size(min = 3, max = 10)
    private String plate;

    @NotNull
    private boolean used;

    private int mileage;

    @Size(min = 3, max = 50)
    private String color;

    @Size(min = 3, max = 1000)
    private String observation;

    @NotNull
    private int fabricationYear;

    @NotNull
    private int modelYear;

    private boolean negotiating;

    private Double discount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vehicle.FuelEnum fuel;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToOne(fetch = FetchType.LAZY)
    private Location location;

    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<VehicleFeature> getFeatures() {
        return features;
    }

    public void addFeature(VehicleFeature feature) {
        if (!features.contains(feature)) {
            features.add(feature);
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getFabricationYear() {
        return fabricationYear;
    }

    public void setFabricationYear(int fabricationYear) {
        this.fabricationYear = fabricationYear;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public boolean isNegotiating() {
        return negotiating;
    }

    public void setNegotiating(boolean negotiating) {
        this.negotiating = negotiating;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Vehicle.FuelEnum getFuel() {
        return fuel;
    }

    public void setFuel(Vehicle.FuelEnum fuel) {
        this.fuel = fuel;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public enum StatusEnum {
        AVAILABLE,
        IN_PROGRESS,
        CLOSED,
    }
}
