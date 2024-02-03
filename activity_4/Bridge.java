package activity_4;

public class Bridge {

    private volatile int num;
    
    public synchronized void enterBridge() {
        while(num == 3) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        num++;
        notifyAll();
    }

    public synchronized void leaveBridge() {
        if(num > 0) {
            num--;
        }
        notifyAll();
    }
}