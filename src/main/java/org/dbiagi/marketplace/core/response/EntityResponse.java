package org.dbiagi.marketplace.core.response;

public class EntityResponse<T> {
    private T data;

    public EntityResponse() {
    }

    public EntityResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
