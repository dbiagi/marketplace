package org.dbiagi.marketplace.core.response;

public class Resource<T> {
    private T data;

    public Resource() {
    }

    public Resource(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
