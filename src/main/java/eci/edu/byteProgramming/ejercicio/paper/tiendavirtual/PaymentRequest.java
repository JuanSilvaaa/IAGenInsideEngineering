package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

/**
 * Input data required by the payment flow.
 */
public class PaymentRequest {
    private final BigDecimal amount;
    private final String customerId;
    private final String customerName;
    private final String customerEmail;
    private final String productId;
    private final Map<String, String> metadata;

    public PaymentRequest(
        BigDecimal amount,
        String customerId,
        String customerName,
        String customerEmail,
        String productId,
        Map<String, String> metadata
    ) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (isBlank(customerId) || isBlank(customerName) || isBlank(customerEmail) || isBlank(productId)) {
            throw new IllegalArgumentException("Customer and product data are required");
        }

        this.amount = amount;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.metadata = metadata == null ? Collections.emptyMap() : Map.copyOf(metadata);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getProductId() {
        return productId;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String requireMetadata(String key) {
        String value = metadata.get(key);
        if (isBlank(value)) {
            throw new IllegalArgumentException("Missing required metadata: " + key);
        }
        return value;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
