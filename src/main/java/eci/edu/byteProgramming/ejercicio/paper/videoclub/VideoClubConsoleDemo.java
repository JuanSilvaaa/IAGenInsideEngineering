package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.util.Arrays;
import java.util.List;

/**
 * Simple console demo for Problem #1.
 */
public class VideoClubConsoleDemo {

    public static void main(String[] args) {
        VideoClubRentalService service = new VideoClubRentalService(
            VideoClubRentalService.defaultCatalog(),
            new PricingStrategyFactory()
        );

        MembershipType membership = MembershipType.PREMIUM;
        List<Integer> selectedMovies = Arrays.asList(1, 3);

        RentalReceipt receipt = service.rentMovies(membership, selectedMovies);
        RentalReceiptFormatter formatter = new RentalReceiptFormatter();

        System.out.println(formatter.format(receipt));
    }
}
