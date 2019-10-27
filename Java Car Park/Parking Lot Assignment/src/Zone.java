import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for the zones in the parking lot
 * @author AAron Luckett
 * @version 6
 */
public class Zone {

    private ParkingSpot[] parkingSpots;
    private VehicleType zoneType;
    private int availableSpots = 0;
    private final static int spotsPerRow = 8;
    private float unitCharge;

    public Zone(){}

    /**
     * Constructor for zone
     * @param zoneNum the zone number
     * @param totalSpots total spots in the zone
     */
    public Zone(int zoneNum, int totalSpots){
        availableSpots = totalSpots;
        parkingSpots = new ParkingSpot[totalSpots];


                try {
                    unitCharge = CalcUnitCharges(zoneNum+1);  //Gets the unit charge for that zone
                } catch (FileNotFoundException e){
                    System.err.println("File does not exist");
                } catch (IOException e){
                    System.err.println("Unexpected error");
                    System.err.println(e.getMessage());
                }

        for (int count = 0; count < totalSpots; count++){
            int row;
            row = count / spotsPerRow;

            parkingSpots[count] = new ParkingSpot(this, row, count);
        }

    }

    /**
     * Gets the unit charge of that specified zone
     * @param zoneNum zone number
     * @return the price of that zone per hour
     * @throws FileNotFoundException
     * @throws IOException
     */
    private float CalcUnitCharges(float zoneNum) throws FileNotFoundException, IOException {
        float price = 0;
        FileReader fr = new FileReader("prices.txt"); //File used
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br);

            infile.useDelimiter("\r?\n|\r");
            while(infile.hasNext()) {
                float zone = infile.nextFloat();
                if(zone == (zoneNum)){ //Cehcks zone numbers match
                    price = infile.nextFloat();
                    break;
                }
            }
        return price;
    }

    /**
     * Parks vehicle in that zone
     * @param v vehicle and type
     * @return
     */
    public boolean parkVehicle(Vehicle v){
        boolean succ = true;
        for (int i = 0; i< availableSpots; i++) {
            succ = parkingSpots[i].park(v);             //Searches parking spots in zones
            if(succ){
                break;
            }
        }
        return  succ;

    }

    /**
     * parks in a spot in a zone
     * @param spot spot wanting to be checked
     * @param v vehicle and type
     */
    public void parkInSpecificSpot(int spot, Vehicle v){
        spot = --spot; //Because psot is in an array format
        parkingSpots[spot].park(v);
    }

    /**
     * Checks a spot in a zone to see if it empty
     * @param space space wanting to be checked
     * @return if spot is free
     */
    public boolean checkSpace(int space){
        boolean isFree = true;
        int spaceID = space - 1;
        if (parkingSpots[spaceID].isEmpty()){
            System.out.println("Space is available"); //Checks to see if spot is empty
        } else {
            System.out.println("Sorry this space is not available");
            isFree = false;
        }
        return isFree;

    }

    /**
     * Checks to see if vehicle with specific receipt code is in a spot
     * @param code receipt code of vehicle
     * @return payment for that vehicle's stay
     */
    public float collectVehicle(String code){
        float payment = 0;
        for(int i = 0; i < availableSpots; i++){
            payment = parkingSpots[i].collectVehicle(code);  //Searches through the spots to find matching vehicle
            if(payment != 0){
                break;
            }
        }
        return payment;
    }

    /**
     * Removes vehicle from that spot
     * @param ID receipt code
     */
    public void removeVehicle(String ID){
        for(int i = 0; i < availableSpots; i++){
            parkingSpots[i].removeVehicle(ID);
        }
    }

    /**
     * Information on the parking spots
     * @return info on the spots
     */
    public String toString(){
            StringBuilder sb = new StringBuilder();

            for (int count = 0; count < parkingSpots.length; count++){
                if ((count % spotsPerRow) == 0){  //For every 8 spaces a new line will be made
                    sb.append("\n");
                }

                sb.append(parkingSpots[count]); //Makes the format easier to read and understand
            }


            return sb.toString();
        }

    /**
     * Gets the unit charge of the zone
     * @return unit charge of the specified zone
     */
    public float getUnitCharge() {
        return unitCharge;
    }
}
