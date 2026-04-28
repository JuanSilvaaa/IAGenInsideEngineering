package eci.edu.byteProgramming.ejercicio.paper.tiendavirtual;

import java.util.ArrayList;
import java.util.List;

/**
 * Core payment service that remains agnostic of payment internals.
 */
public class VirtualStorePaymentService {
    private final List<PaymentEventSubscriber> subscribers = new ArrayList<>();

    public void addSubscriber(PaymentEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(PaymentEventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public ProcessedPayment processPayment(StorePaymentFactory paymentFactory, PaymentRequest request) {
        StorePaymentValidator validator = paymentFactory.createValidator();
        if (!validator.isValid(request)) {
            ProcessedPayment failed = new ProcessedPayment(
                paymentFactory.getMethodType(),
                StorePaymentStatus.FAILED,
                PaymentIdGenerator.next("FAIL"),
                request.getAmount(),
                request.getCustomerId(),
                request.getCustomerName(),
                request.getCustomerEmail(),
                request.getProductId(),
                "Validation failed for " + paymentFactory.getMethodType()
            );
            notifyFailure(failed);
            return failed;
        }

        ProcessedPayment processedPayment = paymentFactory.createMethod().execute(request);

        if (processedPayment.getStatus() == StorePaymentStatus.SUCCESS) {
            notifySuccess(processedPayment);
        } else {
            notifyFailure(processedPayment);
        }

        return processedPayment;
    }

    private void notifySuccess(ProcessedPayment processedPayment) {
        for (PaymentEventSubscriber subscriber : subscribers) {
            subscriber.onPaymentSuccess(processedPayment);
        }
    }

    private void notifyFailure(ProcessedPayment processedPayment) {
        for (PaymentEventSubscriber subscriber : subscribers) {
            subscriber.onPaymentFailure(processedPayment);
        }
    }
}
