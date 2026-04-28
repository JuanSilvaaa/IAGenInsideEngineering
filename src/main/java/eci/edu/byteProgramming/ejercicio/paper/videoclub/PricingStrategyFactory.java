package eci.edu.byteProgramming.ejercicio.paper.videoclub;

/**
 * Simple factory to resolve the pricing strategy for each membership.
 */
public class PricingStrategyFactory {

    public PricingStrategy create(MembershipType membershipType) {
        if (membershipType == MembershipType.PREMIUM) {
            return new PremiumPricingStrategy();
        }
        return new BasicPricingStrategy();
    }
}
