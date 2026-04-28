package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Abstract factory for a payment method and its validator.
 */
public interface StorePaymentFactory {

    StorePaymentMethod createMethod();

    StorePaymentValidator createValidator();

    String getMethodType();
}
