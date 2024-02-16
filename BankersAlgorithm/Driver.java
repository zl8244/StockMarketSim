package BankersAlgorithm;

public class Driver {

    final private static int bankResources = 5;
    final private static int numClients = 4;
    final private static int c1nUnits = 2;
    final private static int c1nRequests = 2;
    final private static long c1minSleepMillis = 1000;
    final private static long c1maxSleepMillis = 2000;
    
    public static void main(String[] args) {
        Banker bank = new Banker(bankResources);
        Client c1 = new Client("test", bank, 2, 2, 1000, 2000);
    }
}
