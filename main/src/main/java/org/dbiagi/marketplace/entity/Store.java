package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dbiagi.marketplace.model.StoreInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Store extends BaseEntity implements StoreInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String cnpj;

    @NotEmpty
    private String name;

    @NotNull(groups = RegistrationGroup.class)
    private String address;

    private String neighborhood;

    private String number;

    @NotNull(groups = RegistrationGroup.class)
    private String zipCode;

    private String website;

    private String phone;

    private String cellphone;

    @OneToMany(orphanRemoval = true, mappedBy = "store", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    @Valid
    private List<User> users = new ArrayList<>();

    private int maxAttentandsCount = 0;

    @Enumerated(EnumType.STRING)
    private StoreTypeEnum type;

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.setStore(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getMaxAttentandsCount() {
        return maxAttentandsCount;
    }

    public void setMaxAttentandsCount(int maxAttendantsCount) {
        this.maxAttentandsCount = maxAttendantsCount;
    }

    public StoreTypeEnum getType() {
        return type;
    }

    @Override
    public void setType(Object type) {
        setType((StoreTypeEnum) type);
    }

    public void setType(StoreTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("StoreInterface[id=%d, name=%s]", id, name);
    }

    public enum StoreTypeEnum {
        STORE,
        RESELLER
    }

    public interface RegistrationGroup {
    }
}
