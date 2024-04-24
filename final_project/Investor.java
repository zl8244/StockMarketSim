package final_project;

import java.util.ArrayList;
import java.util.HashMap;

public class Investor extends Thread{

    /** The amount of money available to the Investor */
    private volatile double money;

    /** Amount of money each Investor is trying to obtain. By default, the goal amount is double the starting amount. */
    private final double moneyGoal;

    /** Amount of money each Investor is willing to spend. By default, it should be at least 50% of the investor's money */
    private volatile double budget;

    /** Stock Market reference for commands like buying and selling */
    private final StockMarket stockMarket;

    /** Round reference for round handling */
    private final Round round;

    /**
     * A hashmap of stocks bought
     * @Key Stock
     * @Value Number of that stock purchased
     */
    private final HashMap<Stock, Integer> boughtStocks = new HashMap<>();

    /**
     * A hashmap of average cost per stock
     * @Key Stock
     * @Value Average money spent on buying the stock
     */
    private final HashMap<Stock, Double> costPerStock = new HashMap<>();

    /** A list of stocks that are within budget */
    private volatile ArrayList<Stock> stocksInBudget = new ArrayList<>();

    public Investor(String name, double money, StockMarket stockMarket, Round round) {
        setName(name);
        this.money = money;
        moneyGoal = money*2;
        budget = round(money/2);
        this.stockMarket = stockMarket;
        this.round = round;
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
     * Buy one unit of Stock s
     * @param s the Stock to be bought
     */
    public synchronized void buyStock(Stock s) {

        int numStock = (int)(budget/s.getValue());

        // Simulate interacting with Stock
        s.interactWithStock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Remove the money from the Investor
        System.out.println(getName() + " buys " + numStock + " of " + s.getName() + " for a total of $" + round(s.getValue()*numStock) + "!");
        money -= round(s.getValue()*numStock);
        budget -= round(s.getValue()*numStock);

        // if the key exists, we already own some of the stock
        // otherwise we don't own any
        if(costPerStock.containsKey(s) && boughtStocks.containsKey(s)) {
            double tempAvgCost = costPerStock.get(s);
            int tempNum = boughtStocks.get(s);
            int newNum = tempNum + numStock;
            double newAvgCost = round(((tempAvgCost * tempNum) + (s.getValue()*numStock)) / newNum);
            
            // Update the values about the owned Stock
            costPerStock.replace(s, newAvgCost);
            boughtStocks.replace(s, newNum);
        } else {
            // Add the Stock so it is now owned
            costPerStock.put(s, s.getValue());
            boughtStocks.put(s, numStock);
        }

        // Done interacting with Stock
        s.stockRelease();
    }

    /**
     * Sell all owned Stock s
     * @param s the Stock to be sold
     */
    public synchronized void sellStock(Stock s) {
        // Get the number of Stock we own
        int numStocks = boughtStocks.get(s);

        // Simulate interacting with the Stock
        s.interactWithStock();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Add the money to the Investor
        System.out.println(getName() + " sells " + numStocks + " of " + s.getName() + " for a total of $" + round(s.getValue()*numStocks));
        money += s.getValue() * numStocks;

        // Remove the Stock from the Investor
        boughtStocks.remove(s);
        costPerStock.remove(s);

        // Investor now done interacting with Stock
        s.stockRelease();
    }

    public void run() {    
        round.addInvestor();    
        System.out.println(getName() + " created with $" + money + " and a goal of $" + moneyGoal);

        while (money < moneyGoal) {
            budget = round(money/2);
            stocksInBudget = stockMarket.findStocks(budget);
            boolean tookAction = false;

            if(!costPerStock.isEmpty() && !boughtStocks.isEmpty()) {
                ArrayList<Stock> ownedStocks = new ArrayList<>(costPerStock.keySet());
                for (Stock stock : ownedStocks) {
                    if(stock.getValue() > costPerStock.get(stock)) {
                        sellStock(stock);
                        tookAction = true;
                    }
                }
            } else if(stocksInBudget.size() > 0) {
                Stock cheapestStock = stocksInBudget.get(0);
                for (Stock stock : stocksInBudget) {
                    if(cheapestStock.getValue() > stock.getValue()) {
                        cheapestStock = stock;
                    }
                }
                buyStock(cheapestStock);
                tookAction = true;
            } 
            
            if(!tookAction) {
                System.out.println(getName() + " does nothing for this round.");
            }

            round.investorFinished();
            round.endRound();
            stocksInBudget.clear();
        }

        System.out.println(getName() + " has reached their goal of $" + moneyGoal + "!");
    }
}
