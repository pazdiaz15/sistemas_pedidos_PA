package classes;

public class Order implements Comparable<Order> {
    private final int id;
    private final boolean isUrgent;

    public Order(int id, boolean isUrgent) {
        this.id = id;
        this.isUrgent = isUrgent;
    }

    public int getId() {
        return id;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    @Override
    public int compareTo(Order o) {
        return Boolean.compare(o.isUrgent, this.isUrgent);
    }

    @Override
    public String toString() {
        return "Order ID: " + id + (isUrgent ? " (Urgent)" : "");
    }
}
