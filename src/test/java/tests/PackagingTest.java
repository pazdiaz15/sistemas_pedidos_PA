package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import classes.Order;
import classes.Packaging;

public class PackagingTest {
    @Test
    public void testPackagingTask() throws Exception {
        Order order = new Order(1, false);
        Packaging packaging = new Packaging(order);
        String result = packaging.call();
        assertEquals("Order packaged: Order ID: 1", result);
    }
    
}
