package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Simulates credit card charge execution.
 */
public class CreditCardPaymentMethod implements StorePaymentMethod {

    @Override
    public ProcessedPayment execute(PaymentRequest request) {
        return new ProcessedPayment(
            "CREDIT_CARD",
            StorePaymentStatus.SUCCESS,
            PaymentIdGenerator.next("CC"),
            request.getAmount(),
            request.getCustomerId(),
            request.getCustomerName(),
            request.getCustomerEmail(),
            request.getProductId(),
            "Credit card payment approved"
        );
    }
}
