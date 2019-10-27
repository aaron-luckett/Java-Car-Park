
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class for vehicle
 * This is a super class
 * @author AAron Luckett
 * @version 5
 */
public class Vehicle {
    String licensePlate;
    ArrayList<ParkingSpot> space = new ArrayList<ParkingSpot>();
    VehicleType size;
    int specifiedZone;
    String receiptCode;
    private long entryTime;
    private long exitTime;


    /**
     * Gets the license plate of the vehicle
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Returns the parking spot of the vehicle
     * @return the parking spot
     */
    public ArrayList<ParkingSpot> getSpace() {
        return space;
    }

    /**
     * Parks the vehicle in the spot
     * @param spot the parking spot
     */
    public void parkInSpot(ParkingSpot spot){
        space.add(spot);
    }

    /**
     * Checks the specified zone to see if they match
     * @param z the zone
     * @return if they match
     */
    public boolean checkZone(int z){
        return (z == specifiedZone);
    }

    /**
     * Removes vehicle from that spot
     * @param spot parking spot
     */
    public void removeFromSpot(ParkingSpot spot){
        space.remove(spot);
    }

    /**
     * Sees if vehicle can fit in the spot
     * @param ps parking spot
     * @return if can fit int spot
     */
    public boolean canFitInSpot(ParkingSpot ps)
    {return true;}

    /**
     * Generates a receipt code
     * @return the receipt code generated
     */
    public String generateReceiptCode() {

        receiptCode = new Random().ints(10, 33, 123).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
        return receiptCode; //Randomly generates a receipt code of 10 elements
    }

    /**
     * Gets the receipt code
     * @return the receipt code
     */
    public String getReceiptCode() {
        return receiptCode;
    }

    /**
     * Gets the entry time of the vehicle
     * @return the time
     */
    public long getEntryTime() {
        entryTime = System.currentTimeMillis();
        return entryTime;
    }

    /**
     * Gets the exit time of the vehicle
     * @return the time
     */
    private long getExitTime(){
        exitTime = System.currentTimeMillis();
        return exitTime;
    }

    /**
     * Calculates the stay of the vehicle
     * @return how long the vehicle was in the car park for
     */
    public int calculateStay(){
        int milliToHours = 3600000;
        long milliSecs = (getExitTime() - entryTime);
        int stay = (int) ((milliSecs / milliToHours)+1);
        System.out.println(stay);
        return stay;
    }


}
