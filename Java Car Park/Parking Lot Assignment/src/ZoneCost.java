import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Zone cost class
 * @author Aaron Luckett
 * @version 2
 */
public class ZoneCost {
    private int zoneNumber;
    private float zoneCost;

    /**
     * Constructor
     */
    public ZoneCost(){}

    /**
     * Gets the zone number
     * @return zone number
     */
    public int getZoneNumber() {
        return zoneNumber;
    }

    /**
     * Gets the zone cost
     * @return zone cost
     */
    public float getZoneCost() {
        return zoneCost;
    }

    /**
     * Sets the zone cost
     * @param zoneCost cost of the zone
     */
    public void setZoneCost(float zoneCost) {
        this.zoneCost = zoneCost;
    }

    /**
     * Loads the zone and costs to park there
     * @param infile file used
     */
    public void load(Scanner infile){
        zoneNumber = infile.nextInt();
        zoneCost = infile.nextFloat();
    }

    /**
     * Saves the zones and costs to a file
     * @param out file used
     */
    public void save(PrintWriter out){
        if (out == null)
            throw new IllegalArgumentException("outfile must not be null"); //Checks there is something to write

        out.println(zoneNumber);
        out.println(zoneCost);
    }

    /**
     * Checks that the zone number mathc
     * @param o zoneCost
     * @return if their zone numbers match
     */
    public boolean equals(Object o) {
        if (this == o) return true;  // Checks they are the same object
        if (o == null || getClass() != o.getClass()) return false; // Checks classes
        ZoneCost z = (ZoneCost) o;
        return Objects.equals(zoneNumber, z.zoneNumber); //Checks the zone numbers
    }

    /**
     * Displays information on the zone and cost
     * @return info on zones and cost
     */
    public String toString(){
        String result = "The Zone number: " + zoneNumber + " has the unit charge of " + zoneCost;
        return result;
    }
}
