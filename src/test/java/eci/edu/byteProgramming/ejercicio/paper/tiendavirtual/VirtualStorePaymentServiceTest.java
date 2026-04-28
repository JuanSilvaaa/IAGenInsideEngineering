package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VirtualStorePaymentServiceTest {

    private VirtualStorePaymentService paymentService;
    private InventorySubscriber inventorySubscriber;
    private BillingSubscriber billingSubscriber;
    private NotificationSubscriber notificationSubscriber;

    @BeforeEach
    void setUp() {
        paymentService = new VirtualStorePaymentService();
        inventorySubscriber = new InventorySubscriber(Map.of("LAPTOP001", 5));
        billingSubscriber = new BillingSubscriber();
        notificationSubscriber = new NotificationSubscriber();

        paymentService.addSubscriber(inventorySubscriber);
        paymentService.addSubscriber(billingSubscriber);
        paymentService.addSubscriber(notificationSubscriber);
    }

    @Test
    void shouldProcessCreditCardAndNotifyAllSubscribers() {
        PaymentRequest request = buildCreditCardRequest();

        ProcessedPayment result = paymentService.processPayment(new CreditCardPaymentFactory(), request);

        assertEquals(StorePaymentStatus.SUCCESS, result.getStatus());
        assertEquals(4, inventorySubscriber.getStock("LAPTOP001"));
        assertEquals(1, billingSubscriber.getInvoices().size());
        assertEquals(1, notificationSubscriber.getSentMessages().size());
        assertTrue(notificationSubscriber.getSentMessages().get(0).startsWith("SUCCESS"));
    }

    @Test
    void shouldRejectInvalidPaypalAndKeepModulesConsistent() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("paypalEmail", "bad-email");
        metadata.put("authToken", "short");

        PaymentRequest request = new PaymentRequest(
            new BigDecimal("99"),
            "CUST-002",
            "Carlos Ruiz",
            "carlos@example.com",
            "LAPTOP001",
            metadata
        );

        ProcessedPayment result = paymentService.processPayment(new PaypalPaymentFactory(), request);

        assertEquals(StorePaymentStatus.FAILED, result.getStatus());
        assertEquals(5, inventorySubscriber.getStock("LAPTOP001"));
        assertEquals(0, billingSubscriber.getInvoices().size());
        assertEquals(1, notificationSubscriber.getSentMessages().size());
        assertTrue(notificationSubscriber.getSentMessages().get(0).startsWith("FAILED"));
    }

    @Test
    void shouldRejectCryptoWithInsufficientBalance() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("walletAddress", "0x12345678901234567890123456");
        metadata.put("cryptoType", "BTC");
        metadata.put("walletBalance", "50");

        PaymentRequest request = new PaymentRequest(
            new BigDecimal("100"),
            "CUST-003",
            "Ana Gomez",
            "ana@example.com",
            "LAPTOP001",
            metadata
        );

        ProcessedPayment result = paymentService.processPayment(new CryptoPaymentFactory(), request);
        assertEquals(StorePaymentStatus.FAILED, result.getStatus());
    }

    @Test
    void shouldAllowAddingNewObserverWithoutChangingCore() {
        AtomicInteger successEvents = new AtomicInteger();
        paymentService.addSubscriber(new PaymentEventSubscriber() {
            @Override
            public void onPaymentSuccess(ProcessedPayment processedPayment) {
                successEvents.incrementAndGet();
            }

            @Override
            public void onPaymentFailure(ProcessedPayment processedPayment) {
            }
        });

        paymentService.processPayment(new CreditCardPaymentFactory(), buildCreditCardRequest());

        assertEquals(1, successEvents.get());
    }

    private PaymentRequest buildCreditCardRequest() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("cardNumber", "4111111111111111");
        metadata.put("cvv", "123");
        metadata.put("expiration", "12/28");

        return new PaymentRequest(
            new BigDecimal("1200"),
            "CUST-001",
            "Maria Perez",
            "maria@example.com",
            "LAPTOP001",
            metadata
        );
    }
}
