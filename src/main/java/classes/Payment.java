package classes;
import java.util.concurrent.Callable;

public class Payment implements Callable<String> {
    private final Order order;

    public Payment(Order order) {
        this.order = order;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Processing payment for " + order);
        Thread.sleep(100); 
        return "Payment processed for " + order;
    }
}
