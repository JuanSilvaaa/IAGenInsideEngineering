package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Console evidence for problem #2 execution.
 */
public class VirtualStoreConsoleDemo {

    public static void main(String[] args) {
        VirtualStorePaymentService paymentService = new VirtualStorePaymentService();

        InventorySubscriber inventory = new InventorySubscriber(Map.of("LAPTOP001", 5));
        BillingSubscriber billing = new BillingSubscriber();
        NotificationSubscriber notifications = new NotificationSubscriber();

        paymentService.addSubscriber(inventory);
        paymentService.addSubscriber(billing);
        paymentService.addSubscriber(notifications);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("cardNumber", "4111111111111111");
        metadata.put("cvv", "123");
        metadata.put("expiration", "12/28");

        PaymentRequest request = new PaymentRequest(
            new BigDecimal("1200"),
            "CUST-001",
            "Maria Perez",
            "maria@example.com",
            "LAPTOP001",
            metadata
        );

        ProcessedPayment processedPayment = paymentService.processPayment(new CreditCardPaymentFactory(), request);

        System.out.println("Status: " + processedPayment.getStatus());
        System.out.println("Transaction: " + processedPayment.getTransactionId());
        System.out.println("Stock LAPTOP001: " + inventory.getStock("LAPTOP001"));
        System.out.println("Invoices: " + billing.getInvoices().size());
        System.out.println("Notifications: " + notifications.getSentMessages().size());
    }
}
