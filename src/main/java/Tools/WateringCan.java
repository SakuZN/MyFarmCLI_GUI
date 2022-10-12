package Tools;

import main.Tools;

import java.util.Objects;

public class WateringCan extends Tools {

    public WateringCan() {
        super("Watering Can", 0, 0.5);
    }

    /**
     * Checks if the tile is plowed and contains a planted seed
     * @return true if tile is plowed and planted, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Planted");}

    @Override
    public String toolDescription() {
        String description = "Adds to the total number of times a planted seed has been watered.\n"
                + "Can only be performed on an plowed tile with planted seed.";
        return description;
    }
}
