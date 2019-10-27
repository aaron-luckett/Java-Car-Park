/**
 * High vehicle class extends from vehicle
 * @author AAron Luckett
 * @version 5
 */

public class High extends Vehicle {


    public High(){
        size = VehicleType.High;
    }

    /**
     * Constructor for High
     * @param lp license plate
     */
    public High(String lp){
        licensePlate = lp;
        specifiedZone = 0;
    }

    /**
     * Checks to see if the vehicle can fit
     * @param ps parking spot
     * @return if can fit
     */
    @Override
    public boolean canFitInSpot(ParkingSpot ps) {
        return ps.getvType() == VehicleType.High;  //Checks to see if types match
    }
}
