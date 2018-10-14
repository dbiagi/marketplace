package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import org.dbiagi.marketplace.entity.Timestampable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    private Timestampable timestampable = new Timestampable();

    @ManyToOne
    @NotNull
    private Context context;
}
