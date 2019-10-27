import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Main application class where the user of the app can decide what to do. Has command line menu
 * @author Aaron Luckett
 * @version 6.0
 */

public class Application {

    private Scanner scan;
    private ParkingLot parkingLot;
    private Admin admin;


    private void startup(){
        parkingLot = new ParkingLot();
        admin = new Admin();

    }

    /**
     * Prints the opening menu that will perform different tasks depending on who is using the app
     */
    private void printMenu(){
        System.out.println("Please enter action");
        System.out.println("1 - Store Vehicle");
        System.out.println("2 - Collect Vehicle");
        System.out.println("3 - Display Parking Lot");
        System.out.println("4 - Exit Car Park");
        System.out.println("5 - ADMIN PRIVILEGES");
        System.out.println("Q - Quit");
        System.out.println();
    }

    /**
     * This is where the user decides what they want to do
     * Uses a switch statement
     */
    private void runMenu(){
        String response = "";
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine();
            switch (response) {
                case "1":
                    whoToStore();
                    break;
                case "2":
                    collectVehicle();
                    break;
                case "3":
                    displayParkingLot();
                    break;
                case "4":
                    whoToExit();
                    break;
                case "5":
                    adminChoice();
                    break;
                case "Q":
                    break;
                case "q":
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        } while (!(response.equals("Q")) && !(response.equals("q")));      //Only allows user to enter the specified inputs, otherwise it will continue to loop
    }

    /**
     * Prints message to decide who will store the vehicles
     */
    private void printStorageOptions(){
        System.out.println("Who will be storing/collecting the vehicle?");
        System.out.println("1 - Customer");
        System.out.println("2 - Attendant");
        System.out.println("Please note attendants cannot store motorbikes or coaches");
    }

    /**
     * This is where the user decides who they want to store the vehicle
     * @return the input the user has entered
     */
    private String decideWho(){
        String answer = "";

        while ((!answer.equals("1")) && (!answer.equals("2"))) {
            System.out.println("Please enter: ");
            answer = scan.nextLine();
            if ((!answer.equals("1")) && (!answer.equals("2"))){
                System.out.println("Incorrect input");                   //Will loop until 1 or 2 is entered
            }
        }
        return answer;
    }

    /**
     * Will carry out the operation that the user has inputted
     * If an attendant is required it will search for an available one and if one is not found the customer must do it themselves
     */
    private void whoToStore(){
        printStorageOptions();
        String answer = decideWho();
        if (answer.equals("1")){
            autoStorage();                                      //Customers have to use the auto storage method
        } else{
            if(parkingLot.checkForAttendant()) {               //Will check for an attendant an if none are available the customer must do it themselves
                parkingLot.grabAttendant();
                attendantStorage();
            } else {
                System.out.println("Sorry there are no attendants available, you will have to store the vehicle yourself");
                autoStorage();
            }
        }
    }

    /**
     * Displays the vehicle types
     */
    private void displayVehicleTypes(){
        System.out.println("What type of vehicle will you store?");
        System.out.println("1 - Standard");
        System.out.println("2 - High");
        System.out.println("3 - Long");
        System.out.println("4 - Coach");
        System.out.println("5 - Motorbike");
    }

    /**
     *
     * @param licensePlate - license plate of the vehicle
     * @return the vehicle type that needs to be stored
     * Uses switch statement to see what type of vehicle needs to be stored
     */
    private Vehicle decideVehicle(String licensePlate){
        Vehicle v = null;
        String ans = "";
        do {
            displayVehicleTypes();
            scan = new Scanner(System.in);
            ans = scan.nextLine();
            switch (ans) {
                case "1":
                    v = new Standard(licensePlate);
                    break;
                case "2":
                    v = new High(licensePlate);
                    break;
                case "3":
                    v = new Long(licensePlate);
                    break;
                case "4":
                    v = new Coach(licensePlate);
                    break;
                case "5":
                    v = new MotorBike(licensePlate);
                    break;
                case "6":
                    runMenu();
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        } while((!ans.equals("1")) && (!ans.equals("2")) && (!ans.equals("3")) && (!ans.equals("4")) && (!ans.equals("5")) && (!ans.equals("6")));
            return v;
    }

    /**
     * Once vehicle is decided it will search for an empty spot in the zone
     */
    private void autoStorage(){
        System.out.println("Please enter the license plate of the vehicle: ");
        String licensePlate = scan.nextLine();
        Vehicle v = decideVehicle(licensePlate);
        parkingLot.parkInSpot(v);
    }


    /**
     * Allows the attendant to decide if they want to automatically find a spot or search for a specific spot themselves
     */
    private void attendantStorage(){
        String ans = "";
        System.out.println("1 - Search for space");
        System.out.println("2 - Store automatically");
        System.out.println("Please select whether or not you would like to search for a space");
        scan = new Scanner(System.in);

        while((!ans.equals("1")) && (!ans.equals("2"))){
            ans = scan.nextLine();
            if((!ans.equals("1")) && (!ans.equals("2"))){
                System.out.println("Invalid input");
            }
        }
        if (ans.equals("1")){
            searchForSpace();
        } else {
            autoStorageAttendant();
        }
    }

    private Vehicle decideVehicleAttendant(String licensePlate){
        Vehicle v = null;
        String ans = "";
        do {
            displayVehicleTypes();
            ans = scan.nextLine();
            switch (ans) {
                case "1":
                    v = new Standard(licensePlate);
                    break;
                case "2":
                    v = new High(licensePlate);
                    break;
                case "3":
                    v = new Long(licensePlate);
                    break;
                default:
                    System.out.println("You can only store Standard, High and Long vehicles as an attendant"); //Attendants can only store a select type of vehicles
                    break;
            }
        } while((!ans.equals("1")) && (!ans.equals("2")) && (!ans.equals("3")));
        return v;
    }

    /**
     * Automatically finds a space to store the vehicle
     */
    private void autoStorageAttendant(){
        System.out.println("Please enter the license plate of the vehicle: ");
        String licensePlate = scan.nextLine();
        Vehicle v = decideVehicleAttendant(licensePlate);
        parkingLot.parkInSpot(v);
    }

    /**
     * Asks the attendant to type the zone they would like to check and searches that zone
     */
    private void searchForSpace(){
        System.out.println("Please enter the license plate of the vehicle: ");
        String licensePlate = scan.nextLine();

        Vehicle v = decideVehicleAttendant(licensePlate);

        System.out.println("Please enter the zone you would like to check ");
        String an = scan.nextLine();
        while(!an.equals("1") && !an.equals("2") && !an.equals("3") && !an.equals("4") && !an.equals("5")){
            System.out.println("Needs to be between 1 and 5");
            an = scan.nextLine();
        }

        int z = Integer.parseInt(an);
        parkingLot.checkSpace(z, v);


    }

    /**
     * Prints out menu
     */
    private void areYouDisabled(){
        System.out.println("Are you disabled");
        System.out.println("1 - Yes");
        System.out.println("2 - No");
    }

    /**
     * Lets the user decide id they are disabled or not
     * @param requiredU required amount to pay
     * @param ID ID of vehicle
     */
    private void isUserDisabled(float requiredU, String ID){
        areYouDisabled();
        String answer = decideWho();
        if (answer.equals("1")){
            discountPrice(requiredU, ID);                                    //Customers have to use the auto storage method
        } else{
          payForStay(requiredU, ID);
        }
    }

    /**
     * Gives discount to disabled drivers
     * Free is the day is sunday
     * @param requiredU required amount to pay
     * @param ID ID of vehicle
     */
    private void discountPrice(float requiredU, String ID){
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();    //Gets day of the week
        String day = dayOfWeek.toString();
        //System.out.println(dayOfWeek);
        if(day.equals("SUNDAY")){                                 //Checks if the day is sunday
            requiredU = 0;
        } else {
            requiredU = (requiredU / 2);
        }                                                        //Halves the price is not Sunday
        payForStay(requiredU, ID);
    }

    /**
     * Asks for the receipt code that is given when a vehicle is stored and checks a matching one is found
     */
    private void collectVehicle(){
        System.out.println("Please enter your receipt code you received when you parked the vehicle: ");
        scan = new Scanner(System.in);
        String ID = scan.nextLine();
        System.out.println();
        System.out.println(ID);
        float payment = parkingLot.collectVehicle(ID);

        if(payment == 0){
            System.out.println("Sorry no matching receipt was found");      //If no matching receipt code is found nothing happens
            System.out.println();
            System.out.println();
        } else {
            isUserDisabled(payment,ID);
        }
    }

    /**
     * Asks the user to pay for their stay
     * @param requiredUnits The amount required to pay for the stay
     */
    private void payForStay(float requiredUnits, String ID){
        System.out.println("Amount required to pay is " + requiredUnits);
        System.out.println("Please enter the required amount for your stay: ");
        float payment = Float.parseFloat(scan.nextLine());
        if(payment == requiredUnits){
            System.out.println("Thank you");
            parkingLot.removeVehicle(ID);             //If enough or more is paid the vehicle is then removed
            provideToken();
        } else if (payment < requiredUnits){
            System.out.println("Not enough");       //If not enough is paid then they get no exit token
        } else {
            float change = (payment - requiredUnits);
            System.out.println("Thank you, your change is " + change + " UNITS");    //If too much is paid then change is given
            parkingLot.removeVehicle(ID);
            provideToken();
        }
    }

    /**
     * Provides an exit token with a unique ID
     */
    private void provideToken(){
        System.out.println("Please find your vehicle and exit within 15 minutes using the token provided");
        ExitToken token = parkingLot.provideExitToken();
        System.out.println("Your token number is " + token.getTokenID());
        System.out.println();
    }

    /**
     * Decide who will collect the vehicle
     */
    private void whoToExit(){
        printStorageOptions();
        String a = decideWho();

        if(a.equals("2")){
            if(parkingLot.checkForAttendant()) {            //Check for an available attendant
                parkingLot.grabAttendant();
                exitBuilding();
            } else {
                System.out.println("Sorry there are no attendants available, you will have to exit yourself"); //If none are available then the customer has to do it themselves
                exitBuilding();
            }
        } else{
            exitBuilding();
        }
    }

    /**
     * Will get the token ID and, if valid, will calculate the time it has taken to exit the car park
     * If it is above 15 minutes, they will be stuck
     */
    private void exitBuilding(){
        System.out.println("Please enter your token ID: ");
        int tokenID = Integer.parseInt(scan.nextLine());
        boolean exists = parkingLot.exitBuilding(tokenID);               //Check if token exists
        if(exists){
            boolean inTime = parkingLot.checkExitTime(tokenID);         //Calculates exit time
            if(inTime){
                System.out.println("Barrier has been lifted");
                System.out.println("Thank you");
                System.out.println();
            } else {
                System.out.println("Exit has taken too long, please wait for assistance");
            }
        } else{
            System.out.println("No matching token");
        }

    }

    /**
     * Displays the parking lot
     */
    private void displayParkingLot(){
        parkingLot.StringBuilder();
    }

    /**
     * Gives a choice to admin of the parking lot
     */
    private void adminChoice(){
        System.out.println("What would you like to do?");
        String ans = "";
        System.out.println("1 - Change zone prices");
        System.out.println("2 - Add an attendant to attendant pool");
        System.out.println("3 - Remove attendant from attendant pool");
        System.out.println("4 - Change if an attendant is available to work");
        do {
            ans = scan.nextLine();

            switch (ans) {
                case "1":
                    admin.changePrices();
                    break;
                case "2":
                    admin.addAttendant();
                    break;
                case "3":
                    admin.removeAttendant();
                    break;
                case "4":
                    admin.editAttendant();
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        } while (!ans.equals("1") && !ans.equals("2") && !ans.equals("3") && !ans.equals("4"));

    }

    /**
     * Saves files that are opened again when program is run
     */
    private void goodbye(){
        parkingLot.saveParkingLot();

        try{
            admin.saveAttendants();                                      //Saves all of the attendants
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file");
        }

        try{
            admin.savePrices();                                          //Saves all of the zone prices
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file");
        }

        try{
            parkingLot.saveTokens();                                    //Saves all of the exit tokens and their details
        } catch (IOException e) {
            System.err.println("Problem trying to write to a file");
        }



        System.out.println("***********GOODBYE**********");
    }


    public static void main(String args[]) {
        Application app = new Application();
        System.out.println("***********WELCOME**********");
        app.startup();
        app.runMenu();

        app.goodbye();
    }

}
