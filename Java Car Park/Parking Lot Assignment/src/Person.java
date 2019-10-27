import java.util.Scanner;

/**
 * Super class person
 * @author Aaron Luckett
 * @version 3
 */
public class Person {

    String name;

    /**
     * Sets the name of the person
     * @param name name of person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Loads to name to a file
     * @param infile file used
     */
    public void load(Scanner infile){
        name = infile.nextLine();
    }
}
