package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Contract for executing a specific payment method.
 */
public interface StorePaymentMethod {

    ProcessedPayment execute(PaymentRequest request);
}
