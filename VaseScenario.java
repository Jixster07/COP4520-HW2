import java.util.*;
import java.util.concurrent.*;

public class VaseScenario{

    int N;
    Guest guests[];
    BlockingQueue<Guest> queue;
    Semaphore semaphore;
    boolean enablePrinting;
    public static void main(String[] args){
        VaseScenario scenario = new VaseScenario();
    }

    public VaseScenario(){
        System.out.println("\nPlease specify N: ");
        Scanner sc = new Scanner(System.in);
        int input;
        String printInput;

        input = sc.nextInt();
        while (input < 1 ){
            System.out.println("Enter an integer greater than 0");
            input = sc.nextInt();
        }

        do{
            System.out.println("Enable simulation printing? (Y/N)");
            printInput = sc.next().toLowerCase();
        }while (!printInput.equals("y") && !printInput.equals("n"));

        if (printInput.equals("y")){
            enablePrinting = true;
        } else{
            enablePrinting = false;
        }
        sc.close();

        System.out.println("==========================================================");

        long time1 = System.nanoTime();

        // Init variables
        this.N = input;
        this.guests = new Guest[N];
        this.queue = new LinkedBlockingQueue<Guest>(N);
        
        for (int i = 0; i < N; i++){
            guests[i] = new Guest(i);
            queue.add(guests[i]);
        }
        
        while (!queue.isEmpty()){
            Guest g = queue.remove();
            if (g.timesLooked == 0) 
                g.start();
            else g.run();

            try {
                g.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printSim("");
        }
        

        long time2 = System.nanoTime();
        System.out.println("Total execution time " + (time2-time1)/1000000000.0 + " seconds");
    }

    private void printSim(String s){
        if (enablePrinting)
            System.out.println(s);
    }

    private class Guest extends Thread {
        
        int i;
        int timesLooked;
        Random random;

        public Guest(int i){
            this.i = i;
            this.timesLooked = 0;
            this.random = new Random();
        }

        private void lookAtVase(){
            timesLooked++;
            printSim("Guest " + i + " admires the vase");
        }
        @Override
        public void run(){
            lookAtVase();
            
            float rval = random.nextFloat();
            if (rval < (0.5 / timesLooked)){
                printSim("Guest " + i + " gets back in line");
                queue.add(this);
            }else{
                printSim("Guest " + i + " wanders away");
            }

        }
    }
}