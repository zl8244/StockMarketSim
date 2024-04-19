package final_project;

import java.util.LinkedList;

public class Stock {

    /** Cost of the Stock object */
    private volatile double value;

    /** Number of Investors that can interact with Stock at any given time */
    private final int numSlots = 3;

    /** Number of Investors that are currently interacting with the Stock */
    private volatile int investorsAt = 0;

    /** Queue of Investors that are awaiting service */
    private volatile LinkedList<Thread> queue = new LinkedList<>();

    /**
     * Constructs a Stock object with an starting value
     * @param initialValue the starting value of the Stock
     */
    public Stock(double initialValue) {
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
    private void addValue() {
        double temp = (double)(value * Math.random());
        value += round(temp);
    }

    /**
     * Helper method to handle how each Stock decreases in value
     */
    private void subValue() {
        double temp = (double)(value * Math.random());
        value -= round(temp);
    }

    /**
     * Handles how each Stock will change its value at the end of each turn
     */
    public synchronized void changeValue() {
        int random = (int)(Math.random()*101);
        if(random < 50) {
            subValue();
            if(value < 0) {
                value = 0;
            }
        } else {
            addValue();
        }
    }

    /**
     * Returns the value of the Stock
     * @return the value of the Stock
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the number of interactions left to the Stock
     * @return
     */
    public int getNumSlots() {
        return numSlots;
    }

    /**
     * Process investors interacting with Stock
     */
    public void stockBusy() {
        queue.add(Thread.currentThread());
        while(investorsAt == numSlots || !Thread.currentThread().equals(queue.getFirst())) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        investorsAt++;
        queue.removeFirst();
    }

    /**
     * Open a slot for investors to interact with Stock
     */
    public void stockRelease() {
        if(investorsAt > 0) {
            investorsAt--;
        }
        notifyAll();
    }
}
