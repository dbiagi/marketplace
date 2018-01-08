package org.dbiagi.marketplace.core.entity;

import org.dbiagi.marketplace.geolocation.entity.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String cnpj;

    private String nickname;

    private String name;

    private String address;

    private String neighborhood;

    private String number;

    private String zipCode;

    private String website;

    private String phone;

    private String cellphone;

    private String nextel;

    @ManyToOne
    private Location location;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "store")
    private List<User> users = new ArrayList<>();

    private int maxAttentandsCount = 0;

    @Enumerated(EnumType.STRING)
    private StoreTypeEnum type;

    public static Store build(Consumer<Store> block) {
        Store store = new Store();
        block.accept(store);
        return store;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getNextel() {
        return nextel;
    }

    public void setNextel(String nextel) {
        this.nextel = nextel;
    }

    public int getMaxAttentandsCount() {
        return maxAttentandsCount;
    }

    public void setMaxAttentandsCount(int maxAttentandsCount) {
        this.maxAttentandsCount = maxAttentandsCount;
    }

    public StoreTypeEnum getType() {
        return type;
    }

    public void setType(StoreTypeEnum type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public enum StoreTypeEnum {
        STORE,
        DELEARSHIP
    }
}
