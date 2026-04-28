package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Notification module that stores sent email messages.
 */
public class NotificationSubscriber implements PaymentEventSubscriber {
    private final List<String> sentMessages = new ArrayList<>();

    @Override
    public void onPaymentSuccess(ProcessedPayment processedPayment) {
        sentMessages.add("SUCCESS -> to=" + processedPayment.getCustomerEmail()
            + " tx=" + processedPayment.getTransactionId());
    }

    @Override
    public void onPaymentFailure(ProcessedPayment processedPayment) {
        sentMessages.add("FAILED -> to=" + processedPayment.getCustomerEmail()
            + " tx=" + processedPayment.getTransactionId());
    }

    public List<String> getSentMessages() {
        return Collections.unmodifiableList(sentMessages);
    }
}
