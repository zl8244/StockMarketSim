package final_project;

import java.util.ArrayList;

public class StockMarket {

    private Stock[] stocks;
    private volatile boolean turnEnd = false;

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

    public synchronized void resetTurnEnd() {
        if(turnEnd) {
            System.out.println("boolean reset");
            turnEnd = false;
        }
    }

    public synchronized void endRound() {
        for (Stock stock : stocks) {
            stock.changeValue();
        }
    }
}
