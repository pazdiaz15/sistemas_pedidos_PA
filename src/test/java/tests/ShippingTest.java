package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import classes.Order;
import classes.Shipping;

public class ShippingTest {
    @Test
    public void testShippingTask() throws Exception {
        Order order = new Order(1, false);
        Shipping shipping = new Shipping(order);
        String result = shipping.call();
        assertEquals("Order shipped: Order ID: 1", result);
    }
    
}
