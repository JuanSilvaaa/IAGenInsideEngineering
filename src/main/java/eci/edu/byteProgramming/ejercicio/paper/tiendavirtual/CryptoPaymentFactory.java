package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Abstract factory implementation for crypto flow.
 */
public class CryptoPaymentFactory implements StorePaymentFactory {

    @Override
    public StorePaymentMethod createMethod() {
        return new CryptoPaymentMethod();
    }

    @Override
    public StorePaymentValidator createValidator() {
        return new CryptoPaymentValidator();
    }

    @Override
    public String getMethodType() {
        return "CRYPTO";
    }
}
