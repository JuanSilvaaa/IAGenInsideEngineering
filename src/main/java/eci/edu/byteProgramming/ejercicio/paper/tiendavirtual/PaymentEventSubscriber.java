package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Observer contract for reacting to payment events.
 */
public interface PaymentEventSubscriber {

    void onPaymentSuccess(ProcessedPayment processedPayment);

    void onPaymentFailure(ProcessedPayment processedPayment);
}
