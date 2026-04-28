package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a rentable movie in the catalog.
 */
public class Movie {
    private final int id;
    private final String title;
    private final MovieFormat format;
    private final BigDecimal price;
    private boolean available;

    public Movie(int id, String title, MovieFormat format, BigDecimal price, boolean available) {
        if (id <= 0) {
            throw new IllegalArgumentException("Movie id must be greater than zero");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Movie title cannot be empty");
        }
        if (format == null) {
            throw new IllegalArgumentException("Movie format is required");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Movie price must be greater than zero");
        }

        this.id = id;
        this.title = title;
        this.format = format;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieFormat getFormat() {
        return format;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void markAsRented() {
        if (!available) {
            throw new IllegalStateException("Movie is not available");
        }
        this.available = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
