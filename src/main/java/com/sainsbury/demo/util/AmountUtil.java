package com.sainsbury.demo.util;

import com.sainsbury.demo.common.Amount;

import java.math.BigDecimal;

public class AmountUtil {

    public static Amount percentage(Amount base, double percentage) {
        if (base == null) {
            return Amount.ZERO;
        }
        BigDecimal percentageValue = base.getPrice().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100));
        return new Amount(percentageValue, true);
    }
}
