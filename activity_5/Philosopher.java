package activity_5;

public class Philosopher extends Thread{
    private final int id;
    private final Fork left;
    private final Fork right;
    private final boolean rHanded;
    private final int nTimes;
    private final long thinkMillis;
    private final long eatMillis;

    public Philosopher(int id, Fork left, Fork right, boolean rHanded, int nTimes, long thinkMillis, long eatMillis) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.rHanded = rHanded;
        this.nTimes = nTimes;
        this.thinkMillis = thinkMillis;
        this.eatMillis = eatMillis;
    }

    private synchronized void runHelper() {
        int thinkTime = (int)(Math.random() * (thinkMillis+1));
        System.out.println("Philosopher " + id + " thinks for " + thinkTime + " time units.");
        try {
            Thread.sleep(thinkTime*1000);
        } catch (InterruptedException e) {
        }
        if(rHanded) {
            System.out.println("Philosopher " + id + " goes for right fork.");
            right.acquire();
            System.out.println("Philosopher " + id + " has right fork.");
            Thread.yield();
        }
        System.out.println("Philosopher " + id + " goes for left fork.");
        left.acquire();
        System.out.println("Philosopher " + id + " has left fork.");
        Thread.yield();
        if(!rHanded) {
            System.out.println("Philosopher " + id + " goes for right fork.");
            right.acquire();
            System.out.println("Philosopher " + id + " has right fork.");
            Thread.yield();
        }
        int eatTime = (int)(Math.random() * (eatMillis+1));
        System.out.println("Philosopher " + id + " eats for " + eatTime + " time units.");
        try {
            Thread.sleep(eatTime*1000);
        } catch (InterruptedException e) {
        }
        if(rHanded) {
            System.out.println("Philosopher " + id + " releases right fork.");
            right.release();
        } else {
            System.out.println("Philosopher " + id + " releases left fork.");
            left.release();
        }
    }
    
    public void run() {
        if(nTimes == 0) {
            while(true) {
                runHelper();
            }
        } else {
            for(int i = 0; i < nTimes; i++) {
                runHelper();
            }
        }
    }
}
