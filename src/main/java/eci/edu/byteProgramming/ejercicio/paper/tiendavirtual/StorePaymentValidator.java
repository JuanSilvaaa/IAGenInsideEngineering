package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Contract for payment data validation.
 */
public interface StorePaymentValidator {

    boolean isValid(PaymentRequest request);
}
