package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.util.HashMap;
import java.util.Map;

/**
 * Stock module that reacts to successful payments.
 */
public class InventorySubscriber implements PaymentEventSubscriber {
    private final Map<String, Integer> stockByProduct = new HashMap<>();

    public InventorySubscriber(Map<String, Integer> initialStock) {
        if (initialStock != null) {
            stockByProduct.putAll(initialStock);
        }
    }

    @Override
    public void onPaymentSuccess(ProcessedPayment processedPayment) {
        String productId = processedPayment.getProductId();
        Integer stock = stockByProduct.get(productId);
        if (stock != null && stock > 0) {
            stockByProduct.put(productId, stock - 1);
        }
    }

    @Override
    public void onPaymentFailure(ProcessedPayment processedPayment) {
        // No inventory update on failed payment.
    }

    public int getStock(String productId) {
        return stockByProduct.getOrDefault(productId, 0);
    }
}
