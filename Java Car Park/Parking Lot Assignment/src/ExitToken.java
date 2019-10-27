import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class for an exit token needed to exit the lot
 * @author Aaron Luckett
 * @version 5
 */

public class ExitToken {

    private int tokenID;
    private long timeReceived;

    /**
     * Constructor for the exit token
     * @param count random generated number that will be used for the token id
     */
   public ExitToken(int count){
       tokenID = count;
       timeReceived = System.currentTimeMillis();
   }


   public ExitToken(){

   }


    /**
     * Gets the token ID
     * @return the id of the token
     */
    public int getTokenID() {
        return tokenID;
    }

    /**
     * Loads information about the token
     * @param infile file used
     */
    public void load(Scanner infile){
        tokenID = infile.nextInt();
        timeReceived = infile.nextLong();
    }

    /**
     * Saves token information
     * @param out file used
     */
    public void save(PrintWriter out){
        if (out == null)
            throw new IllegalArgumentException("outfile must not be null");

        out.println(tokenID);
        out.println(timeReceived);
    }

    /**
     * Checks the time difference between receiving the token and exiting the car park
     * Checks to see if it below 15 minutes
     * @param tokenID - Id of the token
     * @return if time is under 15 minutes or not
     */
    public boolean checkExitTime(int tokenID){
       int timeToExit = 900000;      //15 minutes in milliseconds
       boolean inTime = true;

       long exitTime = System.currentTimeMillis();
       exitTime = (exitTime - timeReceived);           //Calculates exit time
       //System.out.println(exitTime);

       if(exitTime > timeToExit){
           inTime = false;
       }

       return inTime;

    }

    /**
     * To string for the token
     * @return the token details
     */
    @Override
    public String toString() {
        return "ID " + tokenID + " time received " + timeReceived;
    }
}
