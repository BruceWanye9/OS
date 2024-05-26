import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
    private static Semaphore maxDiners = new Semaphore(NUM_PHILOSOPHERS - 1, true);

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            int philosopherId = i;
            new Thread(() -> dine(philosopherId)).start();
        }
    }

    private static void dine(int philosopherId) {
        while (true) {
            try {
                think(philosopherId);
                maxDiners.acquire(); // Philosophers should check if they can eat
                forks[philosopherId].acquire();
                forks[(philosopherId + 1) % NUM_PHILOSOPHERS].acquire();
                eat(philosopherId);
                forks[philosopherId].release();
                forks[(philosopherId + 1) % NUM_PHILOSOPHERS].release();
                maxDiners.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void think(int philosopherId) {
        System.out.println("Philosopher " + philosopherId + " is thinking");
    }

    private static void eat(int philosopherId) {
        System.out.println("Philosopher " + philosopherId + " is eating");
    }
}