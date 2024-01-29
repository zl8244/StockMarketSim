package activity_2;

public class Woolie extends Thread{
    private final String name;
    private final int time;
    private final String city;

    public Woolie(String name, int time, String city) {
        this.name = name;
        this.time = time;
        this.city = city;
    }

    private synchronized void cross(int currentTime) {
        try {
            Thread.sleep(1000);
            if(currentTime == time) {
                System.out.println("" + name + " leaves at " + city + ".");
            } else {
                System.out.println('\t' + name + '\s' + currentTime + " seconds.");
            }
        } catch (InterruptedException e) {
        }
    }

    public void run() {
        System.out.println("" + name + " has arrived at the bridge.");
        System.out.println("" + name + " is starting to cross.");
        int counter = 0;
        while(counter < time) {
            counter++;
            cross(counter);
        }
    }
}
