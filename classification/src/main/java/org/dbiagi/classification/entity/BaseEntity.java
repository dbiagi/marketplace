package org.dbiagi.classification.entity;

import org.dbiagi.classification.model.BaseModel;

import java.util.Date;

public abstract class BaseEntity implements BaseModel {
    private Date createdAt;
    private Date updatedAt;

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
