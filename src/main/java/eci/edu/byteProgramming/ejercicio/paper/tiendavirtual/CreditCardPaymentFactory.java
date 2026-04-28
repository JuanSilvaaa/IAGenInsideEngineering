package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Abstract factory implementation for credit card flow.
 */
public class CreditCardPaymentFactory implements StorePaymentFactory {

    @Override
    public StorePaymentMethod createMethod() {
        return new CreditCardPaymentMethod();
    }

    @Override
    public StorePaymentValidator createValidator() {
        return new CreditCardPaymentValidator();
    }

    @Override
    public String getMethodType() {
        return "CREDIT_CARD";
    }
}
