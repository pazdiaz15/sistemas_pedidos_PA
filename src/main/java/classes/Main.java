package classes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Clase principal que gestiona y procesa pedidos utilizando un pool de hilos.
 */
public class Main {
    private final ExecutorService executor;
    private final Queue<Order> urgentQueue;
    private final Queue<Order> normalQueue;
    private static final int URGENT_THRESHOLD = 3;
    private final List<Long> urgentProcessingTimes = new ArrayList<>();
    private final List<Long> normalProcessingTimes = new ArrayList<>();

    /**
     * Constructor que inicializa un objeto Main con un tamaño especificado para el pool de hilos.
     * 
     * @param poolSize el tamaño del pool de hilos.
     */
    public Main(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
        this.urgentQueue = new LinkedList<>();
        this.normalQueue = new LinkedList<>();
    }

     /**
     * Procesa una lista de pedidos separándolos en colas de urgentes y normales,
     * y luego los procesa según la urgencia.
     * 
     * @param orders una lista de pedidos a procesar.
     */
    public void processOrders(List<Order> orders) {
        // Separar pedidos urgentes y normales
        for (Order order : orders) {
            if (order.isUrgent()) {
                urgentQueue.add(order);
            } else {
                normalQueue.add(order);
            }
        }

        OrderProcessing orderProcessing = new OrderProcessing(executor);

        int urgentCounter = 0;
        while (!urgentQueue.isEmpty() || !normalQueue.isEmpty()) {
            // Procesar 3 pedidos urgentes primero
            if (urgentCounter < URGENT_THRESHOLD && !urgentQueue.isEmpty()) {
                processOrderWithTiming(orderProcessing, urgentQueue.poll(), true);
                urgentCounter++;
            } 
            // Procesar 1 pedido normal después de 3 urgentes
            else if (!normalQueue.isEmpty()) {
                processOrderWithTiming(orderProcessing, normalQueue.poll(), false);
                urgentCounter = 0;  
            } else {
                urgentCounter = 0; 
            }
        }
    }

    /**
     * Procesa un pedido y registra el tiempo de procesamiento.
     * 
     * @param orderProcessing la instancia de OrderProcessing para procesar el pedido.
     * @param order el pedido a procesar.
     * @param isUrgent verdadero si el pedido es urgente, falso en caso contrario.
     */
    private void processOrderWithTiming(OrderProcessing orderProcessing, Order order, boolean isUrgent) {
        long startTime = System.currentTimeMillis();
        orderProcessing.processOrder(order); 
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        if (isUrgent) {
            urgentProcessingTimes.add(totalTime);
        } else {
            normalProcessingTimes.add(totalTime);
        }
    }

    /**
     * Cierra el servicio de ejecutores, esperando su terminación.
     */
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    /**
     * Imprime los tiempos promedio de procesamiento de los pedidos urgentes y normales.
     */
    public void printAverageProcessingTimes() {
        double averageUrgent = urgentProcessingTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        double averageNormal = normalProcessingTimes.stream().mapToLong(Long::longValue).average().orElse(0);

        System.out.println("\nTiempo promedio de procesamiento de pedidos urgentes: " + averageUrgent + " ms");
        System.out.println("Tiempo promedio de procesamiento de pedidos normales: " + averageNormal + " ms");
    }

    /**
     * Método principal que inicializa pedidos, los procesa e imprime los tiempos de procesamiento.
     * 
     * @param args argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Crear 100 pedidos
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            boolean isUrgent = i % 10 == 0;  // 1 de cada 10 pedidos es urgente
            orders.add(new Order(i, isUrgent));
        }

        // Crear un pool de hilos
        int poolSize = 10; 

        Main system = new Main(poolSize);
        long startTime = System.currentTimeMillis();  // Tiempo de inicio del proceso
        system.processOrders(orders);  // Procesar los pedidos
        system.shutdown();
        long endTime = System.currentTimeMillis();  // Tiempo de fin del proceso

        system.printAverageProcessingTimes();
        System.out.println("Tiempo total para procesar todos los pedidos: " + (endTime - startTime) + " ms");
    }
}
