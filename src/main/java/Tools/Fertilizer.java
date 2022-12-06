package Tools;

import main.Tools;

import java.util.Objects;

/**
 * This class represents the Fertilizer tool
 * Extends the Tools class
 */
public class Fertilizer extends Tools {

    /**
     * Constructor for Fertilizer class
     * Calls the super constructor to set all the fertilizer tool properties
     */
    public Fertilizer () {
        super("Fertilizer", 10, 4);
    }

    /**
     * Checks if the tile is plowed and contains a planted seed
     * @return true if tile is plowed and planted, else false
     */
    @Override
    public boolean useTool(String tileStatus) {
        return Objects.equals(tileStatus, "Planted");
    }

    /**
     * Gets the description of the tool
     * @return the description of the tool
     */
    @Override
    public String getToolDescription() {
        String description = "Adds to the total number of times a tile with planted seed has been applied with " +
                "fertilizer.\n"
                + "Can only be performed on a plowed tile with planted seed.";
        return description;
    }
}
