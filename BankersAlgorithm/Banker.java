package BankersAlgorithm;

import java.util.ArrayList;
import java.util.Map;

public class Banker {

    private final int totalResources;
    private volatile int resourceLeft;
    private Map<Thread, Integer> claims;
    private Map<Thread, Integer> loans;
    private Map<Map<Thread, Integer>, Map<Thread, Integer>> banks;

    public Banker(int nUnits) {
        totalResources = nUnits;
        resourceLeft = totalResources;
    }

    public void setClaim(int nUnits) {
        if(claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > totalResources) {
            System.exit(1);
        }
        claims.put(Thread.currentThread(), nUnits);
        loans.put(Thread.currentThread(), 0);
        System.out.println("Thread " + Thread.currentThread().getName() + " sets a claim for " + nUnits + " units.");
    }

    public boolean request(int nUnits) {
        if(!claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > remaining()) {
            System.exit(1);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " requests " + nUnits + " units.");
        return false;
    }

    public void release(int nUnits) {
        if(!claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > allocated()) {
            System.exit(1);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " releases " + nUnits + " units.");
        resourceLeft += nUnits;
        notifyAll();
    }

    public int allocated() {
        return loans.get(Thread.currentThread());
    }

    public int remaining() {
        int result = claims.get(Thread.currentThread()) - loans.get(Thread.currentThread());
        return result;
    }
}
