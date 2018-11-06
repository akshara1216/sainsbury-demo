package com.sainsbury.demo.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "results", "total" })
public class SainsburyDemoResponse {

    @JsonProperty ("results")
    private List<Product> products;

    @JsonProperty ("total")
    private Total total;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }
}
