package final_project;

import java.util.Collection;
import java.util.HashMap;

public class Round {

    private final StockMarket stockMarket;
    private volatile HashMap<Thread, Integer> investors = new HashMap<>();

    public Round(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }
    
    public synchronized void addInvestor() {
        investors.put(Thread.currentThread(), 0);
    }

    public synchronized void investorFinished() {
        investors.replace(Thread.currentThread(), 1);
    }

    private synchronized boolean isAllDone() {
        Collection<Integer> investorStatus = investors.values();
        for (Integer integer : investorStatus) {
            if(integer == 0) {
                return false;
            } 
        }
        return true;
    }

    public synchronized void endRound() {
        if(isAllDone()) {
            stockMarket.endRound();
        }
    }
    
}
