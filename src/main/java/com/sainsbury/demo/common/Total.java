package com.sainsbury.demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "gross", "vat" })
public class Total {

    private Amount total;
    private Amount vatAmount;

    @JsonProperty ("gross")
    public double getGrossTotal() {
        return getTotal().getPrice().doubleValue();
    }

    @JsonProperty ("vat")
    public double getVat() {
        return getVatAmount().getPrice().doubleValue();
    }

    @JsonIgnore
    public Amount getTotal() {
        return total;
    }

    public void setTotal(Amount total) {
        this.total = total;
    }

    @JsonIgnore
    public Amount getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Amount vatAmount) {
        this.vatAmount = vatAmount;
    }
}
