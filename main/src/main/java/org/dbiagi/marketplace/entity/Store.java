package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String email;

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

    private Timestampable timestampable = new Timestampable();

    @OneToMany(orphanRemoval = true, mappedBy = "store", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    @Valid
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Type type;

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.setStore(this);
        }
    }

    public enum Type {
        STORE,
        RESELLER
    }

    public interface RegistrationGroup {
    }
}
