/**
 * The main method, runs the game
 */
public class Main {


    /**
     * Runs the game
     *
     * @param args arguments received from commandline, not used
     */
    public static void main(String[] args) {
        GameLogic logic = new GameLogic(1, 1); // Initialises the map
        logic.init(); // Initialises the game settings
        logic.loopGame(); // Loops through the game
    }
}
