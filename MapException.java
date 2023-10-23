/**
 * A class which creates objects of exception type which help identify different types of errors loading in the maps
 * Accomodates for adding a useful message to direct towards the error.
 */
public class MapException extends Exception {

    /**
     * Passes on a message about an error with a map
     *
     * @param message String explaining what is wrong with the map
     */
    public MapException(String message) {
        super(message);
    }
}
