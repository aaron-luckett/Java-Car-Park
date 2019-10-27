/**
 * Standard vehicle class extends from vehicle
 */
public class Standard extends Vehicle {

    public Standard(){
        size = VehicleType.Standard;
    }

    /**
     * Constructor for standard
     * @param lp license plate
     */
    public Standard(String lp){
        licensePlate = lp;
        specifiedZone = 3;
    }

    /**
     * Compares to see if vehicle can fit in that spot
     * @param ps parking spot
     * @return if it can fit
     */
    @Override
    public boolean canFitInSpot(ParkingSpot ps) {
        return ps.getvType() == VehicleType.Standard;
    }
}
