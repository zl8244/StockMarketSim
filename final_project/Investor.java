package final_project;

import java.util.HashMap;

public class Investor extends Thread{

    /** The amount of money available to the Investor */
    private volatile double money;

    /** Amount of money each Investor is trying to obtain. By default, the goal amount is double the starting amount. */
    private final double moneyGoal;

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
        this.stockMarket = stockMarket;
    }

    public void buyStock(Stock s) {
        //add stock to boughtStocks and costPerStock
    }

    public void sellStock(Stock s) {
        //remove stock from boughtStocks and costPerStock
    }

    public void run() {
        //check boughtStocks for stock
        //check if selling stock will turn in profit using costPerStock
        //look for stock that can be purchased
        //get in line to purchase stock
        //wait
        System.out.println(getName() + " created with " + money);
    }
}
