public interface PaymentProcessor {
    void processPayment(double amount);
}

public class PayPalPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processed payment of $" + amount + " via PayPal.");
    }
}

public class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processed payment of $" + amount + " via Credit Card.");
    }
}

public class PaymentHandler {
    private PaymentProcessor paymentProcessor;

    public PaymentHandler(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void processPayment(double amount) {
        paymentProcessor.processPayment(amount);
    }
}

/**
 * Module responsible for managing payment processing functionalities.
 * 
 * Classes:
 * - PaymentProcessor: Interface defining methods for payment processing.
 * - PayPalPaymentProcessor: Implements PaymentProcessor for PayPal payments.
 * - CreditCardPaymentProcessor: Implements PaymentProcessor for credit card payments.
 * 
 * Responsibilities:
 * - Encapsulates logic for processing different payment methods using specific processors.
 * - Provides a unified interface (PaymentProcessor) for handling various payment transactions.
 * - Promotes high cohesion by grouping payment-related functionalities together within this module.
 * - Each payment processor focuses on specific payment methods, maintaining a clear separation of concerns.
 * 
 * Coupling:
 * - Low coupling is achieved by utilizing the PaymentProcessor interface, which acts as a contract
 *   for the system's payment processing. The system interacts with payment processors via this interface,
 *   allowing for easy integration of new payment methods without altering the core system logic.
 * - The system relies on the PaymentProcessor interface rather than concrete implementation classes, promoting
 *   loose coupling and enabling interchangeable usage of different payment processors.
 * 
 * Note: This module achieves high cohesion by organizing payment processing functionality 
 * into cohesive units (PaymentProcessor implementations) with each unit having a well-defined responsibility.
 * It promotes reusability by allowing addition of new payment processors without modifying existing logic, 
 * ensuring a scalable and maintainable payment system.
 */