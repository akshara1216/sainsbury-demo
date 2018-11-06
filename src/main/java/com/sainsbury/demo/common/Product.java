package com.sainsbury.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonPropertyOrder({ "title", "kcal_per_100g", "unit_price", "description" })
public class Product {

    private String title;
    private Integer calories;
    private Amount price;
    private String description;

    @JsonProperty ("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("kcal_per_100g")
    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @JsonIgnore
    public Amount getPrice() {
        return price;
    }

    public void setPrice(Amount price) {
        this.price = price;
    }

    @JsonProperty ("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("unit_price")
    public double getRawPrice() {
        return getPrice().getPrice().doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }
        Product otherProduct  = (Product) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(getTitle(), otherProduct.getTitle());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getTitle());
        return builder.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("calories", calories)
                .append("price", price)
                .append("description", description).toString();
    }
}
