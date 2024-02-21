package BankersAlgorithm;

public class Driver {

    // Total amount of resources the bank has
    final private static int bankResources = 4;
    // The number of clients
    final private static int numClients = 4;
    // Min number of units the client can claim
    final private static int minNUnits = 1;
    // Max number of units the client can claim
    final private static int maxNUnits = 4;
    // Min number of requests the client can make
    final private static int minRequests = 1;
    // Max number of requests the client can make
    final private static int maxRequests = 10;
    // Min time the client can sleep in milliseconds
    final private static long minSleepMillis = 1000;
    // Max time the client can sleep in milliseconds
    final private static long maxSleepMillis = 2000;
    
    public static void main(String[] args) {
        Banker bank = new Banker(bankResources);
        Client[] clients = new Client[numClients];
        for(int i = 0; i < numClients; i++) {
            int nUnits = (int)(Math.random()*((maxNUnits-minNUnits)+minNUnits))+1;
            int nRequests = (int)(Math.random()*((maxRequests-minRequests)+minRequests))+1;
            String name = "Client " + (i+1);
            clients[i] = new Client(name, bank, nUnits, nRequests, minSleepMillis, maxSleepMillis);
        }
        for (Client client : clients) {
            client.start();
        }
        for (Client client : clients) {
            try {
                client.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
