import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class for an attendant that can perform unique acts in the parking lot
 * @author Aaron Luckett
 * @version 5
 */

public class Attendant extends Person {
    private String staffID;
    private boolean isAvailable;

    public Attendant(String nme, String id, boolean ava){
        name = nme;
        staffID = id;
        isAvailable = ava;
    }

    public Attendant(){

    }

    /**
     * Loads the information about the attendant from a file
     * @param infile - file used
     */
    public void load(Scanner infile){
        super.load(infile);     //Calls super class
        staffID = infile.nextLine();
        isAvailable = infile.nextBoolean();
        infile.nextLine();
    }

    /**
     * Saves the information about the attendants
     * @param out - file being saved to
     */
    public void save(PrintWriter out){
        if (out == null)
            throw new IllegalArgumentException("outfile must not be null");

        out.println(name);
        out.println(staffID);
        out.println(isAvailable);
    }

    /**
     * Equals method that compares the name of the attendants
     * @param o - Object being compared
     * @return - if the names match
     */
    public boolean equals(Object o) {
        if (this == o) return true;  // Checks they are the same object
        if (o == null || getClass() != o.getClass()) return false; // Checks they are the same class
        Attendant a = (Attendant) o;
        return Objects.equals(name, a.name); //Compares names
    }

    /**
     * Sets if an attendant is available
     * @param available if attendant is avaailble
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Checks if an atttendant is available
     * @return id attendant is available
     */
    public boolean checkIfAvailable() {
        return isAvailable;
    }

    /**
     * Sets staff ID
     * @param staffID staff ID of attendant
     */
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    /**
     * To String method
     * @return the details about the attendant
     */
    public String toString(){
        String result = "name: " + name + " id " + staffID + " is available " + isAvailable;
        return result;
    }
}
