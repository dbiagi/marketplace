package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Wither;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements UserDetails {
    @NotEmpty
    @Size(min = 3, max = 255)
    private String name;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String username;

    @NotNull
    @Email
    @Size(min = 3, max = 255)
    private String email;

    @Size(max = 255)
    @NotNull
    private String password;

    @Transient
    private String plainPassword;

    @Size(max = 30)
    private String phone;

    @Size(max = 30)
    private String cellphone;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Store store;

    private boolean enabled = true;

    private boolean expired = false;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        STORE_OWNER,
        STORE_ATTENDANT
    }
}

