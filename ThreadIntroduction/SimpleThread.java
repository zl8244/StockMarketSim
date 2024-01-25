package ThreadIntroduction;

public class SimpleThread extends Thread{

    private int count;
    private String name;

    public SimpleThread(String name) {
        this.name = name;
        count = 0;
    }
    
    public void run() {
        while(count < 9) {
            count++;
            if(count % 2 == 0) {
                Thread.yield();
            }
            System.out.println("" + count + " " + name);
        }
        System.out.println("Done! " + name);
    }
}
