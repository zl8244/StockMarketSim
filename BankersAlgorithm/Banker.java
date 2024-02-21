package BankersAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class Banker {

    private final int totalResources;
    private volatile int resourceLeft;
    private volatile Map<Thread, Integer> claims;
    private volatile Map<Thread, Integer> loans;

    public Banker(int nUnits) {
        totalResources = nUnits;
        resourceLeft = totalResources;
        claims = new HashMap<>();
        loans = new HashMap<>();
    }

    public synchronized void setClaim(int nUnits) {
        if(claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > totalResources) {
            System.exit(1);
        }
        claims.put(Thread.currentThread(), nUnits);
        loans.put(Thread.currentThread(), 0);
        System.out.println("Thread " + Thread.currentThread().getName() + " sets a claim for " + nUnits + " units.");
    }

    private void merge(int[][] arr, int l, int m, int r) {
        int lSize = m - l + 1;
        int rSize = r - m;
        int[][] lArr = new int[lSize][2];
        int[][] rArr = new int[rSize][2];
        for(int i = 0; i < lSize; i++) {
            lArr[i][0] = arr[l+i][0];
            lArr[i][1] = arr[l+i][1];
        }
        for(int i = 0; i < rSize; i++) {
            rArr[i][0] = arr[m+1+i][0];
            rArr[i][1] = arr[m+1+i][1];
        }
        int i = 0;
        int j = 0;
        int index = l;
        while(i < lSize && j < rSize) {
            if(lArr[i][1] <= rArr[j][1]) {
                arr[index][0] = lArr[i][0];
                arr[index][1] = lArr[i][1];
                i++;
            } else {
                arr[index][0] = rArr[j][0];
                arr[index][1] = rArr[j][1];
                j++;
            }
            index++;
        }

        while(i < lSize) {
            arr[index][0] = lArr[i][0];
            arr[index][1] = lArr[i][1];
            i++;
            index++;
        }
        while(j < rSize) {
            arr[index][0] = rArr[j][0];
            arr[index][1] = rArr[j][1];
            j++;
            index++;
        }
    }

    private void mergeSort(int[][] arr, int l, int r) {
        if(l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m+1, r);
            merge(arr, l, m, r);
        }
    }

    private synchronized boolean isSafe(int nUnits) {
        Map<Thread, Integer> tempLoan = new HashMap<>();
        tempLoan.putAll(loans);
        tempLoan.replace(Thread.currentThread(), loans.get(Thread.currentThread()) + nUnits);
        Integer[] claimArray = claims.values().toArray(Integer[]::new);
        Integer[] loanArray = tempLoan.values().toArray(Integer[]::new);

        // array[i][0] = allocated
        // array[i][1] = remaining claim
        int[][] loanAndRemain = new int[tempLoan.size()][2];
        for(int i = 0; i < loanAndRemain.length; i++) {
            loanAndRemain[i][0] = loanArray[i];
            loanAndRemain[i][1] = claimArray[i] - loanArray[i];
        }
        int tempResourceLeft = resourceLeft - nUnits;
        mergeSort(loanAndRemain, 0, loanAndRemain.length-1);
        for(int i = 0; i < loanAndRemain.length; i++) {
            if(loanAndRemain[i][1] > tempResourceLeft) {
                return false;
            }
            tempResourceLeft += loanAndRemain[i][0];
        }
        return true;
    }

    public synchronized boolean request(int nUnits) {
        if(!claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > remaining()) {
            System.exit(1);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " requests " + nUnits + " units.");
        
        if(isSafe(nUnits)) {
            System.out.println("Thread " + Thread.currentThread().getName() + " has " + nUnits + " units allocated.");
            int currentLoan = loans.get(Thread.currentThread());
            int newLoan = currentLoan + nUnits;
            loans.replace(Thread.currentThread(), newLoan);
            resourceLeft -= nUnits;
            return true;
        } 
        while(true) {
            System.out.println("Thread " + Thread.currentThread().getName() + " waits.");
            try {
                wait();
            } catch (InterruptedException e) {
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " awakened.");
            if(isSafe(nUnits)) {
                System.out.println("Thread " + Thread.currentThread().getName() + " has " + nUnits + " units allocated.");
                int currentLoan = loans.get(Thread.currentThread());
                int newLoan = currentLoan + nUnits;
                loans.replace(Thread.currentThread(), newLoan);
                resourceLeft -= nUnits;
                return true;
            }
        }
    }

    public synchronized void release(int nUnits) {
        if(!claims.containsKey(Thread.currentThread()) || nUnits < 1 || nUnits > allocated()) {
            System.exit(1);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " releases " + nUnits + " units.");
        int currentLoan = loans.get(Thread.currentThread());
        int newLoan = currentLoan - nUnits;
        loans.replace(Thread.currentThread(), newLoan);
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
