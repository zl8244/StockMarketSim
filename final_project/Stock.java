package final_project;

import java.util.LinkedList;

public class Stock {

    private final String name;

    /** Cost of the Stock object */
    private volatile double value;

    /** Number of Investors that can interact with Stock at any given time */
    private final int numSlots = 2;

    /** Number of Investors that are currently interacting with the Stock */
    private volatile int investorsAt = 0;

    /** Queue of Investors that are awaiting service */
    private volatile LinkedList<Thread> queue = new LinkedList<>();

    /**
     * Constructs a Stock object with an starting value
     * @param initialValue the starting value of the Stock
     */
    public Stock(String name, double initialValue) {
        this.name = name;
        value = initialValue;
    }

    /**
     * Helper method to round num to nearest hundredth
     * @param num the number to be rounded
     * @return the number rounded to the nearest hundredth
     */
    private double round(double num) {
        long factor = (long) Math.pow(10, 2);
        num = num * factor;
        long tmp = Math.round(num);
        return (double) tmp / factor;
    }

    /**
     * Helper method to handle how each Stock increases in value
     */
    private synchronized void addValue() {
        double temp = value * Math.random();
        value = round(value + temp);
    }

    /**
     * Helper method to handle how each Stock decreases in value
     */
    private synchronized void subValue() {
        double temp = value * Math.random();
        value = round(value - temp);
    }

    /**
     * Handles how each Stock will change its value at the end of each turn
     */
    public synchronized void changeValue() {
        int random = (int)(Math.random()*100) + 1;
        if(random < 50) {
            subValue();
            // Stock value has a hard minimum limit of 1
            if(value <= 1) {
                value = 1;
            }
        } else {
            addValue();
        }
    }

    /**
     * Returns the value of the Stock
     * @return the value of the Stock
     */
    public synchronized double getValue() {
        return value;
    }

    /**
     * Returns the number of interactions left to the Stock
     * @return
     */
    public synchronized int getNumSlots() {
        return numSlots;
    }

    /**
     * Process investors interacting with Stock
     */
    public synchronized void interactWithStock() {
        queue.add(Thread.currentThread());
        while(investorsAt == numSlots || !Thread.currentThread().equals(queue.getFirst())) {
            try {
                System.out.println(Thread.currentThread().getName() + " waits...");
                wait();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " awakens!");
        }
        investorsAt++;
        queue.removeFirst();
    }

    /**
     * Open a slot for investors to interact with Stock
     */
    public synchronized void stockRelease() {
        if(investorsAt > 0) {
            investorsAt--;
        }
        notifyAll();
    }

    public String getName() {
        return name;
    }
}
