package com.sainsbury.demo.common;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {

    private BigDecimal price;

    public static Amount ZERO = new Amount(BigDecimal.ZERO, true);

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Amount(BigDecimal priceParam, boolean doesRoundUp) {
        if (doesRoundUp) {
            price = round(priceParam, Constants.ROUND_VALUE);
        } else {
            price = priceParam;
        }
    }

    private static BigDecimal round(BigDecimal value, int places) {
        return value.setScale(places, RoundingMode.HALF_UP);
    }

    public void add(Amount another) {
        if (another == null) {
            return;
        }
        this.price = price.add(another.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Amount)) {
            return false;
        }
        Amount otherAmount  = (Amount) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(getPrice(), otherAmount.getPrice());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getPrice());
        return builder.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("price", price).toString();
    }
}
