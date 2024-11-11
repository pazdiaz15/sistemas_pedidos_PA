package classes;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class OrderProcessing {
    private final ExecutorService executor;

    public OrderProcessing(ExecutorService executor) {
        this.executor = executor;
    }

    public void processOrder(Order order) {
        // Manda todas las tareas paralelas
        Future<String> paymentFuture = executor.submit(new Payment(order));
        Future<String> packagingFuture = executor.submit(new Packaging(order));
        Future<String> shippingFuture = executor.submit(new Shipping(order));

        handleTask(paymentFuture);
        handleTask(packagingFuture);
        handleTask(shippingFuture);
    }

    private void handleTask(Future<String> future) {
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Task interrupted: " + e.getMessage());
        }
    }
}
