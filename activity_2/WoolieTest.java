package activity_2;

public class WoolieTest {
    
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
