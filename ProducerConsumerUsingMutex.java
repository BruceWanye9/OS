import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumer {
    static final int CAPACITY = 10;
    private final Queue<Integer> queue = new LinkedList<>();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {
                while (queue.size() == CAPACITY) {
                    wait();
                }
                System.out.println("Produced: " + value);
                queue.add(value++);
                notify();
                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (queue.isEmpty()) {
                    wait();
                }
                int value = queue.poll();
                System.out.println("Consumed: " + value);
                notify();
                Thread.sleep(1000);
            }
        }
    }
}

public class ProducerConsumerUsingmutex {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread producerThread = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producerThread.start();
        consumerThread.start();
    }
}
