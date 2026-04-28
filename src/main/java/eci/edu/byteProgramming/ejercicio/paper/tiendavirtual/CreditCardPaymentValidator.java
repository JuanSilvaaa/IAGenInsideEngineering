package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Validation rules for credit card payment requests.
 */
public class CreditCardPaymentValidator implements StorePaymentValidator {

    @Override
    public boolean isValid(PaymentRequest request) {
        String cardNumber = request.getMetadata().get("cardNumber");
        String cvv = request.getMetadata().get("cvv");
        String expiration = request.getMetadata().get("expiration");

        return cardNumber != null
            && cardNumber.matches("\\d{13,19}")
            && cvv != null
            && cvv.matches("\\d{3,4}")
            && expiration != null
            && expiration.matches("\\d{2}/\\d{2}");
    }
}
