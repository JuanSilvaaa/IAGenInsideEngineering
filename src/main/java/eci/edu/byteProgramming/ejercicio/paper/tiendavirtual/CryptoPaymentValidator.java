package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.math.BigDecimal;

/**
 * Validation rules for crypto payment requests.
 */
public class CryptoPaymentValidator implements StorePaymentValidator {

    @Override
    public boolean isValid(PaymentRequest request) {
        String walletAddress = request.getMetadata().get("walletAddress");
        String cryptoType = request.getMetadata().get("cryptoType");
        String balanceText = request.getMetadata().get("walletBalance");

        if (walletAddress == null || walletAddress.length() < 26 || cryptoType == null || cryptoType.isBlank()) {
            return false;
        }

        try {
            BigDecimal balance = new BigDecimal(balanceText);
            return balance.compareTo(request.getAmount()) >= 0;
        } catch (Exception exception) {
            return false;
        }
    }
}
