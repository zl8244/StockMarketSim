package activity_5;

public class Fork implements IFork{

    private volatile boolean isInUse = false;
    
    public synchronized void acquire() {
        if(isInUse) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        } else {
            isInUse = true;
        }
    }

    public synchronized void release() {
        isInUse = false;
        notifyAll();
    }
}
