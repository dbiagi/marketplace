package org.dbiagi.marketplace.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Setting extends BaseEntity {
    @Column(name = "setting_key")
    @NotNull
    @NotEmpty
    private String key;

    @Column(name = "setting_value")
    @NotNull
    @NotEmpty
    private String value;

    public Setting() {
    }

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
