package activity_3;

public class WoolieTest {
    
    public static void main(String[] args) {
        Bridge b = new Bridge();
        Woolie w1 = new Woolie("tester1", 5, "Merctan", b);
        Woolie w2 = new Woolie("tester2", 5, "Sictine", b);
        Woolie w3 = new Woolie("tester3", 10, "Merctan", b);
        Woolie w4 = new Woolie("tester4", 17, "Sictine", b);
        w1.start();
        w2.start();
        w3.start();
        w4.start();
    }
}