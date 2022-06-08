package com.store.cloud.model;

import java.util.Objects;

public class PathParameters {
    private Integer productid;

    public PathParameters(Integer productid) {
        this.productid = productid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    @Override
    public String toString() {
        return "pathParameters{" +
                "productid=" + productid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathParameters that = (PathParameters) o;
        return Objects.equals(productid, that.productid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productid);
    }
}
