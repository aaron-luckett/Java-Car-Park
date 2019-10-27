/**
 * MotorBike class that extends from vehicle
 * @version 5
 * @author Aaron Luckett
 */

public class MotorBike extends Vehicle {

    public MotorBike(){
        size = VehicleType.Motorbike;
    }

    /**
     * Constructor for motorbike
     * @param lp license plate
     */
    public MotorBike(String lp){
        licensePlate = lp;
        specifiedZone = 4;
    }

    /**
     * Checks to see if the motor bike can be parked in that zone
     * @param ps the parking spot
     * @return if it can fit
     */
    @Override
    public boolean canFitInSpot(ParkingSpot ps) {
        return ps.getvType() == VehicleType.Motorbike;  //Checks to see if the types match
    }
}
