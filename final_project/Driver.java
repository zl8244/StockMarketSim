package final_project;

import java.util.Scanner;

public class Driver {

    /** Number of Stocks to be generated */
    private static int numStock = 2;

    /** Number of Investors to be generated */
    private static int numInvestors = 2;

    /** Minimum value that Stocks should be initialized with */
    private static double minInitialStockValue = 10;

    /** Maximum value that the Stocks should be initialized with */
    private static double maxInitialStockValue = 11;

    /** Minimum money each Investor should be initialized with */
    private static double minMoney = 100;

    /** Maximum money each Investor should be initialized with */
    private static double maxMoney = 200;

    /**
     * Helper method to round num to nearest hundredth
     * @param num the number to be rounded
     * @return the number rounded to the nearest hundredth
     */
    private static double round(double num) {
        long factor = (long) Math.pow(10, 2);
        num = num * factor;
        long tmp = Math.round(num);
        return (double) tmp / factor;
    }

    /**
     * Helper method to handle the user input for setting parameters
     */
    private static void handleUserInput() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        System.out.println("Welcome to the Stock Market!");
        System.out.println("Before we begin, we need some answers from you.");

        // Handles how many stock will be generated
        System.out.print("How many stocks are part of this stock market: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            numStock = Integer.parseInt(input);
        }
        
        // Handles how many investors will be generated
        System.out.print("How many investors are in this stock market: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            numInvestors = Integer.parseInt(input);
        }

        // Handles the lower bound of value for each stock
        System.out.print("What is the minimum value all stocks should be initialized with: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            minInitialStockValue = Double.parseDouble(input);
        }

        // Handles the upper bound of value for each stock
        System.out.print("What is the maximum value all stocks should be initialized with: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            maxInitialStockValue = Double.parseDouble(input);
        }

        // Handles the lower bound of money for each investor
        System.out.print("What is the minimum money each investor should be initialized with: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            minMoney = Double.parseDouble(input);
        }

        // Handles the upper bound of money for each investor
        System.out.print("What is the maximum money each investor should be initizlized with: ");
        input = scan.nextLine();
        if(!input.equals("")) {
            maxMoney = Double.parseDouble(input);
        }

        scan.close();
    }

    public static void main(String[] args) {
        handleUserInput();

        // Create the Stocks
        Stock[] stocks = new Stock[numStock];
        for(int i = 0; i < numStock; i++) {
            double randomValue = (Math.random() * (maxInitialStockValue-minInitialStockValue))+minInitialStockValue;
            randomValue = round(randomValue);
            stocks[i] = new Stock(randomValue);
        }
        for (Stock stock : stocks) {
            System.out.println("Stock created with " + stock.getValue());
        }

        
        StockMarket stockMarket = new StockMarket(stocks);
        Round round = new Round(stockMarket);

        // Create the Investors
        Investor[] investors = new Investor[numInvestors];
        for(int i = 0; i < numInvestors; i++) {
            String name = "Investor " + (i+1);
            double randomValue = (Math.random() * (maxMoney-minMoney))+minMoney;
            randomValue = round(randomValue);
            investors[i] = new Investor(name, randomValue, stockMarket, round);
        }
        for (Investor investor : investors) {
            investor.start();
        }
        for (Investor investor : investors) {
            try {
                investor.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
