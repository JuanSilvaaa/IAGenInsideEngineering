package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

/**
 * Validation rules for PayPal payment requests.
 */
public class PaypalPaymentValidator implements StorePaymentValidator {

    @Override
    public boolean isValid(PaymentRequest request) {
        String paypalEmail = request.getMetadata().get("paypalEmail");
        String authToken = request.getMetadata().get("authToken");

        return paypalEmail != null
            && paypalEmail.contains("@")
            && paypalEmail.contains(".")
            && authToken != null
            && authToken.length() >= 12;
    }
}
