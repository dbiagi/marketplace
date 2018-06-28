package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.dbiagi.marketplace.model.ListingInterface;
import org.dbiagi.marketplace.model.StoreInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity implements StoreInterface {

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

    public interface RegistrationGroup {
    }
}
