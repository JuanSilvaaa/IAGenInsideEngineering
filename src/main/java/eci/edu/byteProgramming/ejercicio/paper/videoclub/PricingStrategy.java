package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;

/**
 * Pricing abstraction for membership-based discount policies.
 */
public interface PricingStrategy {

    BigDecimal calculateDiscount(BigDecimal subtotal);
}
