package ThreadIntroduction;

public class TwoThreadTest {

    public static void main(String[] args) {
        SimpleThread Hi = new SimpleThread("Hi");
        SimpleThread Ho = new SimpleThread("Ho");
        Hi.start();
        Ho.start();
    }
}