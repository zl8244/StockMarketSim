package activity_3;

public class Bridge {

    private int num;

    private synchronized int getNum() {
        return num;
    }
    
    public synchronized void enterBridge() {
        if(getNum() != 0) {
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