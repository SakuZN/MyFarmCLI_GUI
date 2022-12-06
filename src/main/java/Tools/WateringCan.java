package Tools;

import main.Tools;

import java.util.Objects;

/**
 * This class represents the Watering Can tool
 * Extends the Tools class
 */
public class WateringCan extends Tools {

    /**
     * Constructor for WateringCan class
     * Calls the super constructor to set all the watering can tool properties
     */
    public WateringCan() {
        super("Watering Can", 0, 0.5);
    }

    /**
     * Checks if the tile is plowed and contains a planted seed
     * @return true if tile is plowed and planted, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Planted");}

    /**
     * Gets the description of the tool
     * @return the description of the tool
     */
    @Override
    public String getToolDescription() {
        String description = "Adds to the total number of times a planted seed has been watered.\n"
                + "Can only be performed on an plowed tile with planted seed.";
        return description;
    }
}
