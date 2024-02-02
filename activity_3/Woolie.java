package activity_3;

public class Woolie extends Thread{
    private final String name;
    private final int time;
    private final String city;
    private final Bridge bridge;

    public Woolie(String name, int time, String city, Bridge bridge) {
        this.name = name;
        this.time = time;
        this.city = city;
        this.bridge = bridge;
    }

    private void cross(int currentTime) {
        try {
            Thread.sleep(1000);
            System.out.println('\t' + name + '\s' + currentTime + " seconds.");
        } catch (InterruptedException e) {
        }
    }

    public void run() {
        System.out.println("" + name + " has arrived at the bridge.");
        bridge.enterBridge();
        System.out.println("" + name + " is starting to cross.");
        int counter = 0;
        while(counter < time) {
            counter++;
            cross(counter);
        }
        System.out.println("" + name + " leaves at " + city + ".");
        bridge.leaveBridge();
    }
}
