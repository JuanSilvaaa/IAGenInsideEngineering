package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.math.BigDecimal;

/**
 * Immutable event payload published after payment processing.
 */
public class ProcessedPayment {
    private final String methodType;
    private final StorePaymentStatus status;
    private final String transactionId;
    private final BigDecimal amount;
    private final String customerId;
    private final String customerName;
    private final String customerEmail;
    private final String productId;
    private final String message;

    public ProcessedPayment(
        String methodType,
        StorePaymentStatus status,
        String transactionId,
        BigDecimal amount,
        String customerId,
        String customerName,
        String customerEmail,
        String productId,
        String message
    ) {
        this.methodType = methodType;
        this.status = status;
        this.transactionId = transactionId;
        this.amount = amount;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.message = message;
    }

    public String getMethodType() {
        return methodType;
    }

    public StorePaymentStatus getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
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

    public String getMessage() {
        return message;
    }
}
