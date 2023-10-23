import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * This is a utility class which stores methods which are required throughout the program
 */
public final class Utility {


    /**
     * Scanner which is used to reads in user's input.
     */
    private static final Scanner s = new Scanner(System.in);

    /**
     * Random which is used to generate random numbers
     */
    private static final Random rand = new Random();

    /**
     * HashMap which converts direction to vector
     */
    public static final HashMap<String, Coordinate> TurnToVector = new HashMap<>() {{
        put("N", new Coordinate(0, -1));
        put("E", new Coordinate(1, 0));
        put("S", new Coordinate(0, 1));
        put("W", new Coordinate(-1, 0));
    }};


    /**
     * Generates a random number between 0 and the upper bound given (excluding)
     *
     * @param maxNum integer representing the upper bound (excluding) to generate a random integer from
     * @return integer between 0 and maxNum - 1.
     */
    public static int getRandNum(int maxNum) {
        return rand.nextInt(maxNum);
    }


    /**
     * Prints a question to the user and receives their input
     *
     * @param question String representing question to ask user
     * @return String representing user input
     */
    public static String getInput(String question) {
        System.out.println(question);
        return s.nextLine();
    }
}
