package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility for deterministic transaction id format generation.
 */
public final class PaymentIdGenerator {

    private PaymentIdGenerator() {
    }

    public static String next(String prefix) {
        long timestamp = System.currentTimeMillis();
        int random = ThreadLocalRandom.current().nextInt(1000, 10000);
        return prefix + "-" + timestamp + "-" + random;
    }
}
