/**
 * Coach class, a vehicle type
 */

public class Coach extends Vehicle{

    public Coach(){
        size = VehicleType.Coach;
    }

    /**
     * Constructor for a coach
     * @param lp - license plate
     */
    public Coach(String lp){
        licensePlate = lp;
        specifiedZone = 2;
        receiptCode = "";
    }

    /**
     * Checks to see if spot is in an allowed zone
     * @param ps the parking spot
     * @return if it can fit
     */
    @Override
    public boolean canFitInSpot(ParkingSpot ps) {
        return ps.getvType() == VehicleType.Coach;   //Checks if spot is type coach
    }
}
