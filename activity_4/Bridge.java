package activity_4;

public class Bridge {

    private int num;
    
    public synchronized void enterBridge() {
        if(num == 3) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        } else {
            num++;
        }
    }

    public synchronized void leaveBridge() {
        num--;
        notifyAll();
    }
}