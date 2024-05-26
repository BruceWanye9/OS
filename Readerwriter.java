import java.util.concurrent.Semaphore;

class ReaderWriter {
    static Semaphore mutex = new Semaphore(1);
    static Semaphore wrt = new Semaphore(1);
    static int readCount = 0;

    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    wrt.acquire(); // first reader locks writer out
                }
                mutex.release();

                // Reading section
                System.out.println("Reader is reading");
                
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    wrt.release(); // last reader releases writer
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                wrt.acquire();
                
                // Writing section
                System.out.println("Writer is writing");
                
                wrt.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Thread[] readers = new Thread[5]; // 5 readers
        Thread writer = new Thread(new Writer());

        for (int i = 0; i < 5; i++) {
            readers[i] = new Thread(new Reader());
            readers[i].start();
        }

        writer.start();
    }
} import java.util.concurrent.Semaphore;

class ReaderWriter {
    static Semaphore mutex = new Semaphore(1);
    static Semaphore wrt = new Semaphore(1);
    static int readCount = 0;

    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    wrt.acquire(); // first reader locks writer out
                }
                mutex.release();

                // Reading section
                System.out.println("Reader is reading");
                
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    wrt.release(); // last reader releases writer
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                wrt.acquire();
                
                // Writing section
                System.out.println("Writer is writing");
                
                wrt.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Thread[] readers = new Thread[5]; // 5 readers
        Thread writer = new Thread(new Writer());

        for (int i = 0; i < 5; i++) {
            readers[i] = new Thread(new Reader());
            readers[i].start();
        }

        writer.start();
    }
}