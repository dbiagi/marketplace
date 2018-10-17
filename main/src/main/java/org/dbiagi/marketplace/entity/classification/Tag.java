package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    @ManyToOne
    @NotNull
    private Context context;
}
