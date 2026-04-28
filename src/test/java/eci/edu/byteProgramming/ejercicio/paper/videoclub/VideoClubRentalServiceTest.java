package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoClubRentalServiceTest {

    private VideoClubRentalService service;

    @BeforeEach
    void setUp() {
        service = new VideoClubRentalService(
            VideoClubRentalService.defaultCatalog(),
            new PricingStrategyFactory()
        );
    }

    @Test
    void shouldCalculatePremiumDiscountUsingSampleCase() {
        RentalReceipt receipt = service.rentMovies(MembershipType.PREMIUM, Arrays.asList(1, 3));

        assertEquals(new BigDecimal("13000"), receipt.getSubtotal());
        assertEquals(new BigDecimal("2600"), receipt.getDiscount());
        assertEquals(new BigDecimal("10400"), receipt.getTotal());
    }

    @Test
    void shouldCalculateBasicMembershipWithoutDiscount() {
        RentalReceipt receipt = service.rentMovies(MembershipType.BASICA, Arrays.asList(1, 4));

        assertEquals(new BigDecimal("14000"), receipt.getSubtotal());
        assertEquals(BigDecimal.ZERO, receipt.getDiscount());
        assertEquals(new BigDecimal("14000"), receipt.getTotal());
    }

    @Test
    void shouldFailWhenMovieIsNotAvailable() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> service.rentMovies(MembershipType.PREMIUM, Collections.singletonList(2))
        );

        assertEquals("Movie is not available: El Padrino", exception.getMessage());
    }

    @Test
    void shouldFailWhenMovieSelectionIsEmpty() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.rentMovies(MembershipType.BASICA, Collections.emptyList())
        );

        assertEquals("At least one movie must be selected", exception.getMessage());
    }

    @Test
    void shouldFailWhenSameMovieIsSelectedTwice() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.rentMovies(MembershipType.BASICA, Arrays.asList(1, 1))
        );

        assertEquals("Duplicate movie id in request: 1", exception.getMessage());
    }

    @Test
    void shouldUpdateAvailabilityAfterSuccessfulRental() {
        service.rentMovies(MembershipType.BASICA, Collections.singletonList(1));

        Movie movie = service.getCatalog().stream().filter(item -> item.getId() == 1).findFirst().orElseThrow();
        assertFalse(movie.isAvailable());
    }
}
