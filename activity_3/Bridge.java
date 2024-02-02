package activity_3;

public class Bridge {

    private int num;
    
    public synchronized void enterBridge() {
        if(num == 1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        } else {
            num = 1;
        }
    }

    public synchronized void leaveBridge() {
        num = 0;
        notify();
    }
}