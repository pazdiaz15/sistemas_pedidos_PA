package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import classes.Order;
import classes.Payment;

public class PaymentTest {
    @Test
    public void testPaymentTask() throws Exception {
        Order order = new Order(1, false);
        Payment payment = new Payment(order);
        String result = payment.call();
        assertEquals("Payment processed for Order ID: 1", result);
    }
    
}
