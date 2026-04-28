package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class RentalReceiptFormatterTest {

    @Test
    void shouldRenderReceiptWithExpectedValues() {
        VideoClubRentalService service = new VideoClubRentalService(
            VideoClubRentalService.defaultCatalog(),
            new PricingStrategyFactory()
        );
        RentalReceipt receipt = service.rentMovies(MembershipType.PREMIUM, Arrays.asList(1, 3));

        String rendered = new RentalReceiptFormatter().format(receipt);

        assertTrue(rendered.contains("Cliente: Premium"));
        assertTrue(rendered.contains("Interestellar (Fisica) - $8.000"));
        assertTrue(rendered.contains("Inception (Digital) - $5.000"));
        assertTrue(rendered.contains("Subtotal: $13.000"));
        assertTrue(rendered.contains("Descuento (20%): $2.600"));
        assertTrue(rendered.contains("Total a pagar: $10.400"));
    }
}
