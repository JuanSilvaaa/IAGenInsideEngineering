package eci.edu.byteProgramming.ejercicio.paper.util;

/**
 * Factory contract used by ECIPayment to build concrete payment methods.
 */
public interface PaymentFactory {

    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
