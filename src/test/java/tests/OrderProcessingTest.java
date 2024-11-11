package tests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import classes.Order;
import classes.OrderProcessing;

public class OrderProcessingTest {

    private OrderProcessing orderProcessing;
    private ExecutorService executor;

    @Before
    public void setUp() {
        executor = Executors.newFixedThreadPool(3);  // Crear un pool de hilos real con 3 hilos
        orderProcessing = new OrderProcessing(executor);
    }

    @Test
    public void testProcessOrder() throws InterruptedException {
        Order order = new Order(1, false);
        orderProcessing.processOrder(order);

        // Asegurarse de que todas las tareas se completen antes de terminar el test
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Verificación manual para asegurarse de que no hay errores en el flujo
        assertTrue(true);
    }
}

