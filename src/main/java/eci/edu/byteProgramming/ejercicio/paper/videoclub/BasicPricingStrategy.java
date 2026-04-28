package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;

/**
 * Basic membership has no discount.
 */
public class BasicPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal subtotal) {
        return BigDecimal.ZERO;
    }
}
