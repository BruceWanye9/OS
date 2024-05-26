import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Chopstick {
    private final Lock lock = new ReentrantLock();

    public boolean pickUp() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }
}
class Philosopher implements Runnable {
    private final int id;
    private final Chopstick leftChopstick;
    private final Chopstick rightChopstick;

    Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Thinking
                System.out.println("Philosopher " + id + " is thinking.");

                // Pick up chopsticks
                if (leftChopstick.pickUp()) {
                    if (rightChopstick.pickUp()) {
                        // Eating
                        System.out.println("Philosopher " + id + " is eating.");
                        // Simulating eating time
                        Thread.sleep(1000);

                        // Put down chopsticks
                        leftChopstick.putDown();
                        rightChopstick.putDown();
                    } else {
                        leftChopstick.putDown();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
 class DiningPhilosophers {
    public static void main(String[] args) {
        Chopstick[] chopsticks = new Chopstick[5];
        Philosopher[] philosophers = new Philosopher[5];

        // Initialize chopsticks
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Chopstick();
        }

        // Initialize philosophers
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % 5]);
            new Thread(philosophers[i]).start();
        }
    }
}
