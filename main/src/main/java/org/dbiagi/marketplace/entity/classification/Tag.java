package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.TemporalInfo;
import org.dbiagi.marketplace.model.classification.ContextInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Tag  implements TagInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    private TemporalInfo temporalInfo;

    @ManyToOne
    @NotNull
    private Context context;

    @Override
    public void setContext(ContextInterface context) {
        this.context = (Context) context;
    }
}
