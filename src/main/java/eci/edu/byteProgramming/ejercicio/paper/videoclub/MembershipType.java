package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;

/**
 * Membership configuration with discount percentage.
 */
public enum MembershipType {
    BASICA(BigDecimal.ZERO),
    PREMIUM(new BigDecimal("0.20"));

    private final BigDecimal discountRate;

    MembershipType(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }
}
