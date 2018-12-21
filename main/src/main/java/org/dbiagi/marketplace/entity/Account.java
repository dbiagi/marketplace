package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Account extends BaseEntity implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

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

    public enum Type{
        VISITOR,
        SPONSOR
    }
}

