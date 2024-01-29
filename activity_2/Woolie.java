package activity_2;

public class Woolie extends Thread{
    private String name;
    private int time;
    private String city;

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

    public static void main(String[] args) {
        Woolie w1 = new Woolie("tester1", 5, "Merctan");
        Woolie w2 = new Woolie("tester2", 5, "Sictine");
        Woolie w3 = new Woolie("tester3", 10, "Merctan");
        Woolie w4 = new Woolie("tester4", 17, "Sictine");
        w1.start();
        w2.start();
        w3.start();
        w4.start();
    }
}
