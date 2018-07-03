package org.dbiagi.marketplace.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key")
    @NotNull
    @NotEmpty
    private String key;

    @Column(name = "setting_value")
    @NotNull
    @NotEmpty
    private String value;

    private TemporalInfo temporalInfo;

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
