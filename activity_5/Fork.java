package activity_5;

public class Fork implements IFork{

    private volatile boolean isInUse = false;
    
    public synchronized void acquire() {
        while(isInUse) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        isInUse = true;
    }

    public synchronized void release() {
        isInUse = false;
        notifyAll();
    }
}
