package org.dbiagi.marketplace.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Setting extends BaseEntity {
    @Column(name = "setting_key")
    @NotEmpty
    private String key;

    @Column(name = "setting_value")
    @NotEmpty
    private String value;

    public Setting() {
    }

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
