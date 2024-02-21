package BankersAlgorithm;

public class Client extends Thread {
    private final Banker banker;
    private final int nUnits;
    private final int nRequests;
    private final long minSleepMillis;
    private final long maxSleepMillis;
    
    public Client(String name, Banker banker, int nUnits, int nRequests, long minSleepMillis, long maxSleepMillis) {
        setName(name);
        this.banker = banker;
        this.nUnits = nUnits;
        this.nRequests = nRequests;
        this.minSleepMillis = minSleepMillis;
        this.maxSleepMillis = maxSleepMillis;
    }

    public void run() {
        banker.setClaim(nUnits);
        for(int i = 0; i < nRequests; i++) {
            if(banker.remaining() == 0) {
                banker.release(nUnits);
            } else {
                int remainingClaim = banker.remaining();
                int randomRequest = (int)(Math.random() * (remainingClaim))+1;
                banker.request(randomRequest);
            }
            long random = (long)(Math.random() * (maxSleepMillis - minSleepMillis)+minSleepMillis);
            try {
                Thread.sleep(random);
            } catch (InterruptedException e) {
            }
        }
        int allocated = banker.allocated();
        if(allocated > 0) {
            banker.release(allocated);
        }
    }
}
