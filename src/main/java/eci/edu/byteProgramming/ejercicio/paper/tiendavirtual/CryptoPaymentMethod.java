package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Simulates crypto transaction execution.
 */
public class CryptoPaymentMethod implements StorePaymentMethod {

    @Override
    public ProcessedPayment execute(PaymentRequest request) {
        return new ProcessedPayment(
            "CRYPTO",
            StorePaymentStatus.SUCCESS,
            PaymentIdGenerator.next("CR"),
            request.getAmount(),
            request.getCustomerId(),
            request.getCustomerName(),
            request.getCustomerEmail(),
            request.getProductId(),
            "Crypto transaction confirmed"
        );
    }
}
