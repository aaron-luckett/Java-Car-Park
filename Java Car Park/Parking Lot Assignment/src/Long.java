/**
 * Long vehicle class that extends from the vehicle class
 * @author Aaron Luckett
 * @version 5
 */

public class Long extends Vehicle {

    public Long(){
        size = VehicleType.Long;
    }

    /**
     * Constructor for long
     * @param lp license plate
     */
    public Long(String lp){
        licensePlate = lp;
        specifiedZone = 1;
    }

    /**
     * Checks to see if it can fit in the spot
     * @param ps
     * @return
     */
    @Override
    public boolean canFitInSpot(ParkingSpot ps) {
        return ps.getvType() == VehicleType.Long;
    }
}
