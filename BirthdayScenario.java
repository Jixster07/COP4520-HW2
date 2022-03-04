import java.util.*;

public class BirthdayScenario{

    int N;
    Guest guests[];
    boolean cupcakeOnPlate; 
    boolean finishFlag;
    boolean enablePrinting;

    public static void main(String[] args){
        BirthdayScenario scenario = new BirthdayScenario();

    }

    private BirthdayScenario(){
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
        this.cupcakeOnPlate = true;
        this.finishFlag = false;


        guests[0] = new Leader(0);

        for (int i = 1; i < N; i++){
            guests[i] = new Guest(i);
        }

        while(!finishFlag){

            printSim("");
            Random random = new Random(); 
            int i = random.nextInt(N);

            printSim("Minotaur chose guest " + (i));

            if (!guests[i].hasEntered)
                guests[i].start();
            else guests[i].run();

            try {
                guests[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long time2 = System.nanoTime();
        System.out.println("Total execution time " + (time2-time1)/1000000000.0 + " seconds");
    }

    private void printSim(String s){
        if (enablePrinting)
            System.out.println(s);
    }

    private class Guest extends Thread {
        
        boolean hasEntered;
        boolean hasEaten;
        int i;
    
        public Guest(int i){
            this.hasEntered = false;
            this.i = i;
        }

        // Replace cupcake
        public void notifyServant(){
            printSim("Guest " + i + " told servant to replace cupcake");
            cupcakeOnPlate = true;
        }

        public void eatCupcake(){
            cupcakeOnPlate = false;
            this.hasEaten = true;
            printSim("Guest " + i + " ate the cupcake");
        }

        public void findExit(){
            printSim("Guest " + i + " found exit");
        }
        @Override
        public void run() {
            findExit();
            if (!hasEaten && cupcakeOnPlate){
                eatCupcake();
            }       
            this.hasEntered = true;   
        }
    }

    private class Leader extends Guest{

        int count;

        public Leader(int i){
            super(i);
            this.count = 0;
        }

        @Override
        public void run() {
                
            findExit();
            if (!cupcakeOnPlate){
                this.count++;
                printSim("Leader increased count to " + count);
                notifyServant();
            }
            if (!this.hasEntered){
                this.count++;
                printSim("Leader increased count to " + count);
            }
            this.hasEntered = true;
            if (this.count == N){
                finishFlag = true;
                System.out.println("Leader notified the minotaur that everyone has entered\n");
            }
        }
    }
}