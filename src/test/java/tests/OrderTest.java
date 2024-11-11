package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import classes.Order;

public class OrderTest {

    @Test
    public void testOrderCreation() {
        Order order = new Order(1, true);
        assertEquals(1, order.getId());
        assertTrue(order.isUrgent());
    }

    @Test
    public void testOrderUrgency() {
        Order normalOrder = new Order(2, false);
        assertFalse(normalOrder.isUrgent());
    }

    @Test
    public void testCompareTo() {
        Order urgentOrder = new Order(1, true);
        Order normalOrder = new Order(2, false);
        assertTrue(urgentOrder.compareTo(normalOrder) < 0);
    }
}
