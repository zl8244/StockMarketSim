package activity_4;

import java.util.LinkedList;

public class Bridge {

    private volatile int num;
    private volatile LinkedList<Thread> queue = new LinkedList<>();
    
    public synchronized void enterBridge() {
        queue.add(Thread.currentThread());
        while(num == 3 || !Thread.currentThread().equals(queue.getFirst())) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        num++;
        queue.remove();
    }

    public synchronized void leaveBridge() {
        if(num > 0) {
            num--;
        }
        notifyAll();
    }
}