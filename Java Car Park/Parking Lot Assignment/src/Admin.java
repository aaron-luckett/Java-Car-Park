import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Admin class
 * Can alter important details, such as zone prices and available attendants
 * Uses the program before anyone else
 * @author Aaron Luckett
 * @Version 5
 */

public class Admin extends Person {
    private ArrayList <Attendant> attendantPool;
    private ArrayList <ZoneCost> zonesAndCost;

    /**
     * Constructor for Admin
     */
    public Admin(){
        attendantPool = new ArrayList<>();
        zonesAndCost = new ArrayList<>();

        

        try {
            loadAttendants();
        } catch (IOException e){
            System.err.println("Unexpected error");   //Loads the attendants file
            System.err.println(e.getMessage());
        }

        try {
            loadPrices();                             //Loads the prices of zones
        } catch (IOException e){
            System.err.println("Unexpected error");
            System.err.println(e.getMessage());
        }
    }


    /**
     * Loads the file of attendants and their details
     * @throws IOException displays if an error occurs
     */
    private void loadAttendants() throws IOException {
            try (FileReader fr = new FileReader("attendants.txt");
                 BufferedReader br = new BufferedReader(fr);
                 Scanner infile = new Scanner(br)) {

                attendantPool.clear();

                infile.useDelimiter("\r?\n|\r");

                while (infile.hasNext()) {
                    Attendant a = new Attendant();
                    a.load(infile);
                    attendantPool.add(a);
                }
            }

    }

    /**
     * Saves the attendants to the file
     * @throws IOException
     */
    public void saveAttendants() throws IOException {
        String outfileName = "attendants.txt";                   //File used
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            for (Attendant atten : attendantPool) {
                atten.save(outfile);
            }
        }
    }

    /**
     * Adds an attendant to the file
     */
    public void addAttendant(){
        String b = "";
        Attendant a = new Attendant();
        Scanner scan = new Scanner(System.in);

        System.out.println("Please enter the name of the attendant you wish to add");  //Asks admin for the attendants details
        b = scan.nextLine();
        a.setName(b);

        System.out.println("Please enter the ID you wish to give the attendant");
        b = scan.nextLine();
        a.setStaffID(b);

        a.setAvailable(canWork());



        attendantPool.add(a);  //Adds to the attendant pool
        System.out.println(attendantPool);
    }

    /**
     * Decides if the attendant can work for that day and be "Available"
     * @return if the attendant is available for that day
     */
    private boolean canWork(){
        String b = "";
        Scanner scan = new Scanner(System.in);
        boolean isAvail = true;
        System.out.println("Please enter whether they can work today");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
        while(!b.equals("1") && !b.equals("2")) {
            b = scan.nextLine();
            if(b.equals("2")){
                isAvail = false;
            } else if(b.equals("1")) {
            } else {
                System.out.println("Invalid input");
            }
        }
        return isAvail;
    }

    /**
     * Edits if an attendant can work for that day
     */
    public void editAttendant(){
        String ans = attendantName();

        boolean exists = false;
        for(Attendant attenda: attendantPool){
            if(ans.equals(attenda.getName())){  //Searches for attendant by name
               attenda.setAvailable(canWork());
               exists = true;
            }
        }
        if(!exists){
            System.out.println("No attendant with that name exists");
        }
    }

    /**
     * ASsks the admin for the name of the attendant
     * @return the attendants name
     */
    private String attendantName(){
        System.out.print("Please enter the name of the attendant you would like to remove: ");
        String ans = "";
        Scanner scan = new Scanner(System.in);
        ans = scan.nextLine();
        return ans;
    }

    /**
     * Searches for all the available attendants
     * @return an array list of all the attendants that are available for that day
     */
    public ArrayList checkForAvailableAttendants(){
        ArrayList<Attendant> availableA = new ArrayList<>();
        for(Attendant a : attendantPool){
            if(a.checkIfAvailable()){
                availableA.add(a);       //Adds and returns all the available attendants
            }
        }
        return availableA;
    }

    /**
     * Removes an attendant for the attendant pool
     */
    public void removeAttendant(){
        String ans = attendantName();
        boolean exists = false;
        for(Attendant attenda: attendantPool){
            if(ans.equals(attenda.getName())){
                System.out.println("Removed attendant " + ans);        //Checks attendant exists
                attendantPool.remove(ans);
                exists = true;
            }
        }
        if(!exists){
            System.out.println("No attendant with that name exists");
        }
    }

    /**
     * Loads the prices of the zones
     * @throws IOException
     */
    private void loadPrices() throws IOException{
        try (FileReader fr = new FileReader("prices.txt"); //File used
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            zonesAndCost.clear();

            infile.useDelimiter("\r?\n|\r");

            while (infile.hasNext()) {
                ZoneCost z = new ZoneCost();
                z.load(infile);
                zonesAndCost.add(z);
            }
        }
        //System.out.println(zonesAndCost);
    }

    /**
     * Saves the zone prices
     * @throws IOException
     */
    public void savePrices() throws IOException{
        String outfileName = "prices.txt";
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            for (ZoneCost z : zonesAndCost) {
                z.save(outfile);
            }
        }
    }

    /**
     * Changes the price of the zone specified
     */
    public void changePrices(){
        Scanner scan = new Scanner(System.in);
        String ans = "";

        while(!ans.equals("1") && !ans.equals("2") && !ans.equals("3") && !ans.equals("4") && !ans.equals("5")) {
            System.out.println("Please enter the zone number of the zone you wish to change the price of: ");
            ans = scan.nextLine();

            if (!ans.equals("1") && !ans.equals("2") && !ans.equals("3") && !ans.equals("4") && !ans.equals("5")){
                System.out.println("Incorrect input");
            }
        }
        int a = Integer.parseInt(ans);

        for(ZoneCost zc : zonesAndCost){       //Searches for the zone
            if(a == (zc.getZoneNumber())){
                System.out.println("Found zone " + ans + " the current price is " + zc.getZoneCost());

                String newCost = "";
                System.out.println("Please enter the new cost to park in the zone: ");
                newCost = scan.nextLine();
                float n = Float.parseFloat(newCost);
                if(n > 0) {
                    zc.setZoneCost(n);
                    System.out.println(zonesAndCost);   //Sets the new zone cost and returns it to the array list
                } else {
                    System.out.println("Price has to be a positive number");
                }
            }
        }
    }
}
