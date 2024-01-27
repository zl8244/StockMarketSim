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

    public void run() {
        System.out.println("" + name + " has arrived at the bridge.");
        int counter = 0;
        while(counter < time) {
            if(counter == 0) {
                System.out.println("" + name + " is starting to cross.");
            } else {
                System.out.println('\t' + name + '\s' + counter + " seconds.");
            }
            counter++;
            Thread.yield();
        }
        System.out.println("" + name + " leaves at " + city + ".");
    }

    public static void main(String[] args) {
        Woolie w1 = new Woolie("tester1", 10, "Merctan");
        Woolie w2 = new Woolie("tester2", 10, "Sictine");
        Woolie w3 = new Woolie("tester3", 15, "Merctan");
        Woolie w4 = new Woolie("tester4", 20, "Sictine");
        w1.start();
        w2.start();
        w3.start();
        w4.start();
    }
}
