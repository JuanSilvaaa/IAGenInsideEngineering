package eci.edu.byteProgramming.ejercicio.paper.videoclub;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Formats money values as Colombian pesos style, e.g. $10.400.
 */
public final class MoneyFormatter {
    private static final DecimalFormat FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        FORMAT = new DecimalFormat("#,###", symbols);
        FORMAT.setGroupingUsed(true);
    }

    private MoneyFormatter() {
    }

    public static String format(BigDecimal amount) {
        BigDecimal normalized = amount.setScale(0, RoundingMode.HALF_UP);
        return "$" + FORMAT.format(normalized);
    }
}
