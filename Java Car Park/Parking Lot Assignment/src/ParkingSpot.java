/**
 * Class for parking spots in the parking lot zones
 * @author Aaron Luckett
 * @version 4
 */

public class ParkingSpot {

    private Vehicle vehicle;
    private Zone zone;
    private VehicleType vType;
    private int row;
    private int spotID;
    private int zoneNum;

    /**
     * Constructor for parking spot
     * @param z zone
     * @param r row
     * @param id id of spot
     */
    public ParkingSpot(Zone z, int r, int id){
        zone = z;
        row = r;
        id = spotID;
    }

    /**
     * Gets vehicle type the spot can have
     * @return vehicle type
     */
    public VehicleType getvType() {
        return vType;
    }

    /**
     * Checks to see if the spot is empty
     * @return if empty
     */
    public boolean isEmpty(){
        return(vehicle == null); //If null == empty
    }

    /**
     * Removes vehicle from the spot
     * @param ID receipt code of the vehicle
     */
    public void removeVehicle(String ID){
        if(!isEmpty()) {
            String vCode = vehicle.getReceiptCode();
            if (vCode.equals(ID)) {
                vehicle.removeFromSpot(this); //Removes from spot
                vehicle = null;
            }
        }
    }

    /**
     * Parks the vehicle in that spot
     * @param ve vehicle
     * @return if spot is empty or not
     */
    public boolean park(Vehicle ve){
        if (!isEmpty()){
            return false;
        } else {
            vehicle = ve;
            vehicle.parkInSpot(this);
            return true;
        }
    }

    /**
     * Calculates the payment for the stay
     * @return The amount needed to be paid
     */
    private float calculatePayment(){
        float charge = (vehicle.calculateStay() * zone.getUnitCharge()); //Works out the cost
        System.out.println();
        System.out.println(charge + " UNITS");
        System.out.println();
        return charge;


    }

    /**
     * Checks the codes and reutrns how much customer needs to pay
     * @param code receipt code of the vehicle
     * @return The amount required to pay
     */
    public float collectVehicle(String code){
        float payment = 0;
        if(!isEmpty()){                                    //Checks if spot is empty
            String vCode = vehicle.getReceiptCode();
            if(vCode.equals(code)){                              //Checks if code name match
                System.out.println("Matching vehicle found");
                payment = calculatePayment();
            }
        }
        return payment;
    }


    /**
     * To string for the spot
     * @return spot information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(!isEmpty()){
            sb.append(vehicle.getLicensePlate());   //Displays the license plate of the vehicle in that spot
        } else {
            sb.append("EMPTY");      //If no vehicle then empty is printed
        }
        sb.append("  ");

        return sb.toString();

    }


}
