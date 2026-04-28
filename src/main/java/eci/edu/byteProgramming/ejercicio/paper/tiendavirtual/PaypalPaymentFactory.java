package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Abstract factory implementation for PayPal flow.
 */
public class PaypalPaymentFactory implements StorePaymentFactory {

    @Override
    public StorePaymentMethod createMethod() {
        return new PaypalPaymentMethod();
    }

    @Override
    public StorePaymentValidator createValidator() {
        return new PaypalPaymentValidator();
    }

    @Override
    public String getMethodType() {
        return "PAYPAL";
    }
}
