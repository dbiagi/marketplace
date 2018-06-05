package org.dbiagi.classification.model;

import java.util.Date;

public interface BaseModel {
    int getId();

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);
}
