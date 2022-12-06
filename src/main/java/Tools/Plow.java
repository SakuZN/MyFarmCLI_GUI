package Tools;

import main.Tools;

import java.util.Objects;

/**
 * This class represents the Plow tool
 * Extends the Tools class
 */
public class Plow extends Tools {

    /**
     * Constructor for Plow class
     * Calls the super constructor to set all the plow tool properties
     */
    public Plow() {
        super("Plow", 0, 0.5);
    }

    /**
     * Checks if the tile is unplowed
     * @return true if tile is unplowed, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Unplowed");}

    /**
     * Gets the description of the tool
     * @return the description of the tool
     */
    @Override
    public String getToolDescription() {
        String description = "Converts an unplowed tile to a plowed tile.\n"
                + "Can only be performed on an unplowed tile.";
        return description;
    }
}
