import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class contains the map information, including the map name, gold required to win, and the map structure.
 */
public class Map {

    /**
     * ArrayList of strings which represents the maps. Each string represents a row
     */
    private ArrayList<String> map = new ArrayList<>();

    /**
     * String representing the map name
     */
    private String mapName;

    /**
     * Integer representing the gold required to beat the game (From human's prospective)
     */
    private int goldRequired;

    /**
     * Integer representing the minimum number of spawnable spaces the map should contain
     */
    private int minimumRequiredNumOfSpaces;


    /**
     * The constructor receives the minimum number of spaces the map should accomodate for (for placing bots and players)
     *
     * @param minimumRequiredNumOfSpaces integer representing the number of spaces the map should accommodate for
     */
    public Map(int minimumRequiredNumOfSpaces) {
        this.minimumRequiredNumOfSpaces = minimumRequiredNumOfSpaces;
    }


    /**
     * Getter which returns the gold required to win
     *
     * @return integer representing the gold required to win
     */
    protected int getGoldRequired() {
        return goldRequired;
    }


    /**
     * Getter which returns the name of the map
     *
     * @return String representing the name of the map
     */
    protected String getMapName() {
        return mapName;
    }


    /**
     * Gets the number of rows in the map
     *
     * @return integer representing the number of rows in the map
     */
    public int getYDim() {
        return this.map.size();
    }


    /**
     * Gets the number of columns in row y
     *
     * @param y Integer representing the row number in the map
     * @return integer representing the number of columns in the given row
     */
    public int getXDim(int y) {
        return this.map.get(y).length();
    }


    /**
     * Receives a vector and checks whether it is within the map or not
     *
     * @param vector Coordinate type representing the vector to check if is in map or not
     * @return boolean representing whether the vector is within the map or not.
     */
    public boolean vectorInMap(Coordinate vector) {
        return ((0 <= vector.y && vector.y < this.map.size()) && (0 <= vector.x && vector.x < this.map.get(vector.y).length()));
    }


    /**
     * Receives a vector and returns the tile in that coordinate.
     *
     * @param vector Coordinate type representing the vector to check
     * @return character representing the tile type in that coordinate.
     */
    public char getTile(Coordinate vector) {
        return this.map.get(vector.y).charAt(vector.x);
    }


    /**
     * Gets a coordinate and a tile and checks whether the type of tile in the coordinate is the same as the type of tile sent
     *
     * @param position Coordinate representing the coordinates of the tile to check
     * @param tile     character representing the type of tile to check
     * @return boolean of whether the tile is the same type of tile as the one in the coordinates sent
     */
    public boolean checkTile(Coordinate position, char tile) {
        return vectorInMap(position) && getTile(position) == tile;
    }


    /**
     * Changes the tile in coordinates position into the tile given
     *
     * @param position Coordinate representing the coordinates of the tile to change
     * @param tile     character representing the type of tile to change to
     */
    protected void changeTile(Coordinate position, char tile) {
        String str = this.map.get(position.y);
        this.map.set(position.y, str.substring(0, position.x) + tile + str.substring(position.x + 1));
    }


    /**
     * attempts to read the next line and return it using the scanner received.
     *
     * @param scnr Scanner which attempts to read in the next line
     * @return String representing the next line if there is one.
     */
    public String readLine(Scanner scnr) {
        if (scnr.hasNextLine()) return scnr.nextLine();
        return null;
    }


    /**
     * Takes in a line and the meta data string and checks whether the line contains information of that meta data,
     * if so, it returns the meta data
     *
     * @param line          String read from file
     * @param metaDataTitle String that the line should start with
     * @return String representing the line after removing the metadata title
     * @throws MapException If the line is null, the metadata title is missing or if there is no value after the title.
     */
    private String readMetaData(String line, String metaDataTitle) throws MapException {
        if ("".equals(line)) {
            throw new MapException("Missing " + metaDataTitle);
        }
        int firstSpace = line.indexOf(" ");
        if (firstSpace == -1 || firstSpace == line.length() - 1 || !metaDataTitle.equals(line.substring(0, firstSpace))) {
            throw new MapException("Fix the line of " + metaDataTitle);
        }
        return line.substring(firstSpace + 1);
    }


    /**
     * Attempts to read the map, returning it if successful, otherwise throwing a
     * MapException error with description as to what went wrong
     *
     * @param scnr Scanner reading in the file
     * @return ArrayList of strings which stores each row of the map as a string.
     * @throws MapException If there is an in invalid character in the map or if there is not enough spaces for the players and bots.
     */
    private ArrayList<String> readMap(Scanner scnr) throws MapException {
        ArrayList<String> tempMap = new ArrayList<>();
        int tempSpaceNum = 0;
        while (scnr.hasNextLine()) {
            String line = scnr.nextLine();
            for (String character : line.split("")) { //loops through all the characters in a line checking if they are all valid
                if (!".#GE".contains(character)) { // Checks if a character is an invalid character
                    scnr.close();
                    throw new MapException("Invalid character, fix the map.");
                }
                if (!"#G".contains(character)) { // Checks if a character is spawnable (By both humans and bots)
                    tempSpaceNum += 1;
                }
            }
            tempMap.add(line);
        }
        if (tempSpaceNum < this.minimumRequiredNumOfSpaces) {
            throw new MapException("Not enough spaces for players and bots");
        }
        return tempMap;
    }


    /**
     * Validates whether a given file contains a valid map or not, if not then it throws a MapException with the reasoning as to why not.
     *
     * @param f file to read map from
     * @throws MapException If any cases from from readMetaData and readMap, if file is not found or the win argument
     *                      does not contain a valid integer
     */
    protected void validateMap(File f) throws MapException {
        int tempSpaceNum = 0;
        String name;
        int goldToWin;
        ArrayList<String> tempMap;
        try {
            Scanner scnr = new Scanner(f);
            name = readMetaData(readLine(scnr), "name"); // Checks if the name of the maps exists
            String stringGoldToWin = readMetaData(readLine(scnr), "win"); // Checks if the gold to win exists
            goldToWin = Integer.parseInt(stringGoldToWin);
            if (goldToWin < 0) {
                throw new NumberFormatException();
            }
            tempMap = readMap(scnr);
        } catch (FileNotFoundException e) {
            throw new MapException("File not found!");
        } catch (NumberFormatException e) {
            throw new MapException("Fix win argument!");
        }
        this.mapName = name;
        this.goldRequired = goldToWin;
        this.map = tempMap;
        this.minimumRequiredNumOfSpaces = tempSpaceNum;
    }
}
