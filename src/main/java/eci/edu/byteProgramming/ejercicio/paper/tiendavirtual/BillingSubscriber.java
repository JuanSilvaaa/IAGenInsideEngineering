package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Billing module that stores invoices after successful payments.
 */
public class BillingSubscriber implements PaymentEventSubscriber {
    private final List<String> invoices = new ArrayList<>();

    @Override
    public void onPaymentSuccess(ProcessedPayment processedPayment) {
        String invoice = "INV-" + processedPayment.getTransactionId()
            + " | customer=" + processedPayment.getCustomerName()
            + " | amount=" + processedPayment.getAmount()
            + " | product=" + processedPayment.getProductId();
        invoices.add(invoice);
    }

    @Override
    public void onPaymentFailure(ProcessedPayment processedPayment) {
        // No invoice for failed payments.
    }

    public List<String> getInvoices() {
        return Collections.unmodifiableList(invoices);
    }
}
