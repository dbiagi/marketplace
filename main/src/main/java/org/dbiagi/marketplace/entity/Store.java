package org.dbiagi.marketplace.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Store extends BaseEntity {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String name;

    private String address;

    private String neighborhood;

    private String number;

    private String zipCode;

    private String website;

    private String phone;

    private String cellphone;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        STORE,
        RESELLER
    }

    public interface RegistrationGroup {
    }
}
