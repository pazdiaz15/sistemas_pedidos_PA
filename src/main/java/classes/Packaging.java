package classes;
import java.util.concurrent.Callable;

public class Packaging implements Callable<String> {
    private final Order order;

    public Packaging(Order order) {
        this.order = order;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Packaging order " + order);
        Thread.sleep(100);
        return "Order packaged: " + order;
    }
}
