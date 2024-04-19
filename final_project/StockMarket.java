package final_project;

public class StockMarket {

    private Stock[] stocks;

    public StockMarket(Stock[] stocks) {
        this.stocks = stocks;
    }

    public synchronized Stock findStock() {
        //do something
        return null;
    }

    public void buyStock(Stock s) {
        //do something
    }

    public void sellStock(Stock s) {
        //do something
    }
}
