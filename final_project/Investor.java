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

    public Investor(String name, double money, StockMarket stockMarket) {
        setName(name);
        this.money = money;
        moneyGoal = money*2;
        budget = round(money/2);
        this.stockMarket = stockMarket;
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

    public void buyStock(Stock s) {
        //add stock to boughtStocks and costPerStock
    }

    public void sellStock(Stock s) {
        //remove stock from boughtStocks and costPerStock
    }

    public void run() {
        /*
         * 1) Look through costPerStock and compare the price investor bought stock for against the current value of the stock
         *   a) If the current value of the stock is greater than the price stored, investor sells
         *     a1) Get the number of the stock investor has by looking through boughtStocks
         *     a2) Sell all of it
         *   b) If the Stock is busy wait until Stock has space
         * 2) Look through all stocks listed by stock market
         *   a) If there exists a stock that fits within the investor's budget, buy as much as they can
         *   b) If the Stock is busy wait until Stock has space
         * 3) Repeat Step 2 until investor exhausts budget
         * 4) Wait for all Stocks to change value then repeat until moneyGoal has been reached
         */
        System.out.println(getName() + " created with " + money + " and a goal of " + moneyGoal);
        ArrayList<Stock> stocksInBudget = stockMarket.findStocks(budget);
        System.out.println(getName() + " Budget: " + budget);
        for (Stock stock : stocksInBudget) {
            System.out.println(getName() + " Stock: " + stock.getValue());
        }
    }
}
