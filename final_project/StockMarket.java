package final_project;

import java.util.ArrayList;

public class StockMarket {

    private Stock[] stocks;

    public StockMarket(Stock[] stocks) {
        this.stocks = stocks;
    }

    /**
     * Returns an ArrayList of Stocks that are under the designated budget
     * @param budget the budget that the Stocks have to fit under
     * @return an ArrayList of Stocks are in budget
     */
    public synchronized ArrayList<Stock> findStocks(double budget) {
        ArrayList<Stock> stocksInBudget = new ArrayList<>();
        for (Stock stock : stocks) {
            if(stock.getValue() <= budget) {
                stocksInBudget.add(stock);
            }
        }
        return stocksInBudget;
    }

    public void buyStock(Stock s) {
        //do something
    }

    public void sellStock(Stock s) {
        //do something
    }
}
