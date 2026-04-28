package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Premium membership applies a 20% discount.
 */
public class PremiumPricingStrategy implements PricingStrategy {
    private static final BigDecimal RATE = new BigDecimal("0.20");

    @Override
    public BigDecimal calculateDiscount(BigDecimal subtotal) {
        return subtotal.multiply(RATE).setScale(0, RoundingMode.HALF_UP);
    }
}
