package BankersAlgorithm;

public class Client extends Thread {
    private final String name;
    private final Banker banker;
    private final int nUnits;
    private final int nRequests;
    private final long minSleepMillis;
    private final long maxSleepMillis;
    
    public Client(String name, Banker banker, int nUnits, int nRequests, long minSleepMillis, long maxSleepMillis) {
        this.name = name;
        this.banker = banker;
        this.nUnits = nUnits;
        this.nRequests = nRequests;
        this.minSleepMillis = minSleepMillis;
        this.maxSleepMillis = maxSleepMillis;
    }

    public void run() {
        
    }
}
