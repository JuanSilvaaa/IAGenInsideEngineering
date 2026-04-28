package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;

/**
 * Immutable rented movie line item.
 */
public class RentalItem {
    private final String title;
    private final MovieFormat format;
    private final BigDecimal unitPrice;

    public RentalItem(String title, MovieFormat format, BigDecimal unitPrice) {
        this.title = title;
        this.format = format;
        this.unitPrice = unitPrice;
    }

    public String getTitle() {
        return title;
    }

    public MovieFormat getFormat() {
        return format;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
