package Tools;

import main.Tools;

import java.util.Objects;

/**
 * This class represents the Shovel tool
 * Extends the Tools class
 */
public class Shovel extends Tools {

    /**
     * Constructor for Shovel class
     * Calls the super constructor to set all the shovel tool properties
     */
    public Shovel() {
        super("Shovel", 7, 2);
    }

    /**
     * Checks if the tile contains withered harvest
     * @return true if tile contains withered harvest, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Withered");}

    /**
     * Gets the description of the tool
     * @return the description of the tool
     */
    @Override
    public String getToolDescription() {
        String description = "Removes a withered plant from a tile.\n"
                + "Can be used on any tile with varying effects.";
        return description;
    }
}
