package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Application service that coordinates movie selection and rental calculation.
 */
public class VideoClubRentalService {
    private final List<Movie> catalog;
    private final PricingStrategyFactory pricingStrategyFactory;

    public VideoClubRentalService(List<Movie> catalog, PricingStrategyFactory pricingStrategyFactory) {
        if (catalog == null || catalog.isEmpty()) {
            throw new IllegalArgumentException("Catalog cannot be null or empty");
        }
        if (pricingStrategyFactory == null) {
            throw new IllegalArgumentException("Pricing strategy factory is required");
        }

        this.catalog = catalog;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }

    public static List<Movie> defaultCatalog() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Interestellar", MovieFormat.FISICA, new BigDecimal("8000"), true));
        movies.add(new Movie(2, "El Padrino", MovieFormat.FISICA, new BigDecimal("7000"), false));
        movies.add(new Movie(3, "Inception", MovieFormat.DIGITAL, new BigDecimal("5000"), true));
        movies.add(new Movie(4, "Matrix", MovieFormat.DIGITAL, new BigDecimal("6000"), true));
        return movies;
    }

    public List<Movie> getCatalog() {
        return List.copyOf(catalog);
    }

    public RentalReceipt rentMovies(MembershipType membershipType, List<Integer> movieIds) {
        validateRequest(membershipType, movieIds);

        Set<Integer> uniqueIds = new HashSet<>();
        List<RentalItem> rentalItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (Integer movieId : movieIds) {
            if (!uniqueIds.add(movieId)) {
                throw new IllegalArgumentException("Duplicate movie id in request: " + movieId);
            }

            Movie movie = findMovie(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie id does not exist: " + movieId));

            if (!movie.isAvailable()) {
                throw new IllegalStateException("Movie is not available: " + movie.getTitle());
            }

            movie.markAsRented();
            rentalItems.add(new RentalItem(movie.getTitle(), movie.getFormat(), movie.getPrice()));
            subtotal = subtotal.add(movie.getPrice());
        }

        PricingStrategy pricingStrategy = pricingStrategyFactory.create(membershipType);
        BigDecimal discount = pricingStrategy.calculateDiscount(subtotal);
        BigDecimal total = subtotal.subtract(discount);

        return new RentalReceipt(membershipType, rentalItems, subtotal, discount, total);
    }

    private void validateRequest(MembershipType membershipType, List<Integer> movieIds) {
        if (membershipType == null) {
            throw new IllegalArgumentException("Membership type is required");
        }
        if (movieIds == null || movieIds.isEmpty()) {
            throw new IllegalArgumentException("At least one movie must be selected");
        }
    }

    private Optional<Movie> findMovie(Integer movieId) {
        return catalog.stream().filter(movie -> movie.getId() == movieId).findFirst();
    }
}
