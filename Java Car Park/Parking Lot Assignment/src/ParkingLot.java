import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


/**
 * Class for the main parking lot
 * @author Aaron Luckett
 * @version 6
 */
public class ParkingLot {

    private Scanner scan;
    private Zone[] zones;
    private final int numOfZones = 5;
    private ArrayList<Attendant> availableAttendants;
    private ArrayList<ExitToken> exitTokens;
    private Admin admin;
    private final int totalSpots = 32;

    /**
     * Constructor for the parking lot
     */
    public ParkingLot(){
        zones = new Zone[numOfZones];

        for (int count = 0; count < numOfZones; count++){
            zones[count] = new Zone(count, totalSpots);               //Loads 5 zones with 32 parking spots each
        }
        exitTokens = new ArrayList<>();                                //Loads all exit tokens not used and available attendants for that day
        availableAttendants = new ArrayList<>();
        admin = new Admin();

        try {
            loadTokens();
        } catch (IOException e){
            System.err.println("Unexpected error");
            System.err.println(e.getMessage());
        }

    }


    /**
     * Goes to store a vehicle by going through each zone
     * @param veh vehicle and type
     */
    public void parkInSpot(Vehicle veh){
        int zoneNum;

        for (zoneNum = 0; zoneNum < zones.length; zoneNum ++) {
            if (veh.checkZone(zoneNum)) {                                        //Loops through zones to look for spot
                break;
            }
        }

        if(zones[zoneNum].parkVehicle(veh)){
            displayDetails(veh, zoneNum);
        } else{
            System.out.println("Sorry this zone is full so we are unable to park the car");
        }

    }

    /**
     * Calculates the time and puts it in a neat format
     * @param milliseconds time when parked in the lot
     * @return
     */
    private String calculateTime(long milliseconds){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //Format used
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());


    }

    /**
     * Displays the details about the vehicle stored
     * @param veh vehicle stored
     * @param zoneNum zone it is stored in
     */
    private void displayDetails(Vehicle veh, int zoneNum){
        System.out.println("Vehicle with license plate " + veh.getLicensePlate() +  " will be parked in Zone " + ++zoneNum);
        System.out.println("ReceiptCode is " + veh.generateReceiptCode());
        System.out.println("The vehicle was stored at " + calculateTime(veh.getEntryTime())); //Displays info about the parked vehicle
        System.out.println();
    }

    /**
     * Checks a specific spot in a specific zone
     * @param z zone number
     * @param veh vehicle and type
     */
    public void checkSpace(int z, Vehicle veh){
        scan = new Scanner(System.in);
       int zone = z - 1;    //As zone is an array
       String s = "";
       int space = -1;

       System.out.println("Please enter the spot number you would like to check: ");
       do {
           s = scan.nextLine();
           space = Integer.parseInt(s);
           if((space < 1) || (space > totalSpots )){
               System.out.println("Input must be between 1 and " + totalSpots);
           }
       } while((space < 1) || (space > totalSpots ));

       if(zones[zone].checkSpace(space)){
           if(veh.checkZone(zone)) {
               zones[zone].parkInSpecificSpot(space, veh);                   //Stores vehicle in that zone and space
               displayDetails(veh, zone);
           } else {
               System.out.println("However this vehicle cannot go in that zone");        //If vehicle does not match the zone type it cannot be stored there
           }
       }


    }

    /**
     * Seaches for vehicle with that receipt code and returns the amount reuqired to pay
     * @param receiptCode code of parked vehicle that was given when parked
     * @return the amount reuired to pay
     */
    public float collectVehicle(String receiptCode){
        float payment = 0;
        for(int i = 0; i < zones.length; i++){
            payment = zones[i].collectVehicle(receiptCode);
            if(payment != 0){
                break;
            }

        }
        return payment;
    }

    /**
     * Removes the vehicle form the parking lot
     * @param ID Receipt code of the vehicle
     */
    public void removeVehicle(String ID){
        for(int i = 0; i < zones.length; i++){
            zones[i].removeVehicle(ID);
        }
    }

    /**
     * loads the tokens of vehicles yet to exit
     * @throws IOException
     */
    private void loadTokens() throws IOException{
        try (FileReader fr = new FileReader("tokens.txt"); //File used
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            exitTokens.clear();

            infile.useDelimiter("\r?\n|\r");  //Delimiter used

            while (infile.hasNext()) {
                ExitToken e = new ExitToken();
                e.load(infile);
                exitTokens.add(e);
            }
        }
    }

    /**
     * Provides an exit token with a random token id
     * @return the token generated
     */
    public ExitToken provideExitToken(){
        int random = (int)(Math.random() * 160 + 1);  //Random token id generator
        ExitToken token = new ExitToken(random);
        exitTokens.add(token);       //Adds to the array list
        System.out.println(exitTokens);
        return token;
    }

    /**
     * Checks to see if a token with that id exists
     * @param tokenID id of token entered
     * @return is token exists
     */
    public boolean exitBuilding(int tokenID){
        boolean exists = false;
        for (ExitToken exitToken: exitTokens){
            if (tokenID == (exitToken.getTokenID())){   //Compares token ids
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * Calculates the exit time of the driver
     * @param tokenID id of the token
     * @return if time is less than 15 minutes
     */
    public boolean checkExitTime(int tokenID){
        boolean inTime = false;
        for(ExitToken exit: exitTokens){
            if(tokenID == exit.getTokenID()){
                inTime = exit.checkExitTime(tokenID);
                exitTokens.remove(exit);
                break;
            }
        }
        return inTime;
    }

    /**
     * Checks for an available attendant
     * @return if an attendant is available
     */
    public boolean checkForAttendant(){
        boolean anyAvailable = true;
       loadAttendants();
       if(availableAttendants.isEmpty()) {  //Checks to see if array list is empty
           anyAvailable = false;
       }
       return anyAvailable;
    }

    /**
     * Gets an attendant from the array list
     */
    public void grabAttendant(){
        int randomIndex = (int) (Math.random() * availableAttendants.size());
        Attendant a = availableAttendants.get( randomIndex );                //Gets a random attendant
        System.out.println("Attendant " + a.getName() + " will park your vehicle");
    }


    /**
     * Loads the available attendants
     */
    private void loadAttendants(){
        availableAttendants = admin.checkForAvailableAttendants();
        //System.out.println(availableAttendants);
    }

    /**
     * String builder for parking lot
     */
    public void StringBuilder() {
        StringBuilder results = new StringBuilder("Data in Parking Lot is: \n");
        int zoneNum = 1;
        for (int i = 0; i < numOfZones; i++) {
            results.append("Zone " + zoneNum + "\n" + zones[i] + "\n" + "\n");  //Goes through and displays each zone separately
            zoneNum++;
        }
        System.out.println(results);
    }

    /**
     * Saves the tokens
     * @throws IOException
     */
    public void saveTokens() throws IOException{
        String outfileName = "tokens.txt"; //file used
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            for (ExitToken e : exitTokens) {
                e.save(outfile);
            }
        }
    }



    public void saveParkingLot(){}

}
