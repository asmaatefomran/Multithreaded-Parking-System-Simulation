public class CustomCountingSemaphore {

    private int permits; 
    private final Object lock = new Object(); 

    public CustomCountingSemaphore(int permits) {
        if (permits < 0) {
            throw new IllegalArgumentException("Permits cannot be negative.");
        }
        this.permits = permits;
    }

    public void acquire() throws InterruptedException {
        synchronized (lock) {
            while (permits == 0) {
                lock.wait(); 
            }
            permits--; 
        }
    }

    public void release() {
        synchronized (lock) {
            permits++; 
            lock.notify(); 
        }
    }

    public int availablePermits() {
        return permits;
    }
    
}
