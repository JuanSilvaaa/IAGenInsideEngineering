package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Simulates PayPal charge execution.
 */
public class PaypalPaymentMethod implements StorePaymentMethod {

    @Override
    public ProcessedPayment execute(PaymentRequest request) {
        return new ProcessedPayment(
            "PAYPAL",
            StorePaymentStatus.SUCCESS,
            PaymentIdGenerator.next("PP"),
            request.getAmount(),
            request.getCustomerId(),
            request.getCustomerName(),
            request.getCustomerEmail(),
            request.getProductId(),
            "PayPal payment approved"
        );
    }
}
