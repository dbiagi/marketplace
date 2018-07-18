package org.dbiagi.marketplace.entity.classification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dbiagi.marketplace.entity.TemporalInfo;
import org.dbiagi.marketplace.model.classification.ContextInterface;
import org.dbiagi.marketplace.model.classification.TagInterface;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode()
@Entity
@Data
public class Tag implements TagInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String slug;

    private TemporalInfo temporalInfo = new TemporalInfo();

    @ManyToOne
    @NotNull
    private Context context;

    @Override
    public void setContext(ContextInterface context) {
        this.context = (Context) context;
    }
}
