package final_project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Round {

    /** Reference to the StockMarket object */
    private final StockMarket stockMarket;

    /** Variable that stores the current round number */
    private volatile int roundNum;

    /** 
     * A hashmap of the Investor threads
     * @Key The Investor thread
     * @Value 1 if Investor is finished, 0 otherwise
     */
    private volatile HashMap<Thread, Integer> investors = new HashMap<>();

    public Round(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
        roundNum = 1;
    }

    public int getRoundNum() {
        return roundNum;
    }
    
    /**
     * Adds the current Investor thread to the hashmap with a value of 0
     */
    public synchronized void addInvestor() {
        investors.put(Thread.currentThread(), 0);
    }

    /**
     * Changes the value mapping of the current Investor thread to 1
     */
    public synchronized void investorFinished() {
        investors.replace(Thread.currentThread(), 1);
    }

    /**
     * Returns if all the Investor mappings in the hashmap is 1
     * @return If all the Investor mappings in the hashmap is 1
     */
    private synchronized boolean isAllDone() {
        Collection<Integer> investorStatus = investors.values();
        for (Integer integer : investorStatus) {
            if(integer == 0) {
                return false;
            } 
        }
        return true;
    }

    /**
     * Checks if all the Investor Threads are done running for now and call stockMarket's endRound function
     */
    public synchronized void endRound() {
        if(isAllDone()) {
            Set<Thread> investorSet = investors.keySet();
            for (Thread thread : investorSet) {
                investors.replace(thread, 0);
            }
            stockMarket.endRound();
            roundNum++;
            System.out.println("Round " + roundNum);
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
    
}
