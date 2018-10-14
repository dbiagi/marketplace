package org.dbiagi.marketplace.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Embeddable
@Data
public class Timestampable {
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }
}
