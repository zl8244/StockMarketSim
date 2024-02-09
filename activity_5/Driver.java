package activity_5;

public class Driver {

    private static boolean minusL = false;
    private static int np;
    private static int nt;
    private static int tm;
    private static int em;

    private static String argsHelper(String[] args, int index, String defaultValue) {
        if(args.length > index) {
            if(index == 0 && args[0].equals("-l")) {
                return "true";
            }
            return args[index];
        }
        return defaultValue;
    }
    
    public static void main(String[] args) {
        minusL = Boolean.parseBoolean(argsHelper(args, 0, "false"));
        if(minusL){
            np = Integer.parseInt(argsHelper(args, 1, "4"));
            nt = Integer.parseInt(argsHelper(args, 2, "10"));
            tm = Integer.parseInt(argsHelper(args, 3, "0"));
            em = Integer.parseInt(argsHelper(args, 4, "0"));
        } else {
            np = Integer.parseInt(argsHelper(args, 0, "4"));
            nt = Integer.parseInt(argsHelper(args, 1, "10"));
            tm = Integer.parseInt(argsHelper(args, 2, "0"));
            em = Integer.parseInt(argsHelper(args, 3, "0"));
        }
        Fork[] forks = new Fork[np];
        for(int i = 0; i < np; i++) {
            forks[i] = new Fork();
        }
        Philosopher[] philosophers = new Philosopher[np];
        for (int i = 0; i < np; i++) {
            if(!minusL) {
                philosophers[i] = new Philosopher(i+1, forks[(np+i-1)%np], forks[i], true, nt, tm, em);
            } else {
                if((i+1)%2 == 0) {
                    philosophers[i] = new Philosopher(i+1, forks[(np+i-1)%np], forks[i], true, nt, tm, em);
                } else {
                    philosophers[i] = new Philosopher(i+1, forks[(np+i-1)%np], forks[i], false, nt, tm, em);
                }
            }
        }
        for(int i = 0; i < np; i++) {
            philosophers[i].start();
        }
    }
}
