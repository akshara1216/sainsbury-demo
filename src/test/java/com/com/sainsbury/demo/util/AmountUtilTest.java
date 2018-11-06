package com.com.sainsbury.demo.util;

import com.sainsbury.demo.common.Amount;
import com.sainsbury.demo.util.AmountUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(BlockJUnit4ClassRunner.class)
public class AmountUtilTest {

    @Test
    @Ignore
    public void testWithNullValue() {
        Amount total = null;
        Assert.assertEquals(Amount.ZERO, AmountUtil.percentage(total, 10));
    }

    @Test
    @Ignore
    public void testWithZeroValue() {
        System.out.println("1");
        Amount total = Amount.ZERO;
        Assert.assertEquals(Amount.ZERO, AmountUtil.percentage(total, 10));
    }

    @Test
    @Ignore
    public void testWith10Percent() {
        System.out.println("2");
        Amount total = new Amount(new BigDecimal(100.00), true);
        Amount expected = new Amount(new BigDecimal(10.00), true);
        Assert.assertEquals(expected, AmountUtil.percentage(total, 10));
    }

    @Test
    @Ignore
    public void testWith30Percent() {
        System.out.println("3");
        Amount total = new Amount(new BigDecimal(100.00), true);
        Amount expected = new Amount(new BigDecimal(30.00), true);
        Assert.assertEquals(expected, AmountUtil.percentage(total, 30));
    }

    @Test
    @Ignore
    public void testWith40Percent() {
        System.out.println("4");
        Amount total = new Amount(new BigDecimal(250.50), true);
        Amount expected = new Amount(new BigDecimal(100.20), true);
        Assert.assertEquals(expected, AmountUtil.percentage(total, 40));
    }

    /*@Test
    public void testWith20Percent() {
        Amount total = new Amount(new BigDecimal(5.00), true);
        Amount expected = new Amount(new BigDecimal(0.83), true);
        System.out.println(AmountUtil.percentage(total, 20));
        Assert.assertEquals(expected, AmountUtil.percentage(total, 20));
    }*/
}
