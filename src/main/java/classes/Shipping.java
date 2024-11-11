package classes;

import java.util.concurrent.Callable;

public class Shipping implements Callable<String> {
    private final Order order;

    public Shipping(Order order) {
        this.order = order;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Shipping order " + order);
        Thread.sleep(100);
        return "Order shipped: " + order;
    }
}

