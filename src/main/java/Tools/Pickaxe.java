package Tools;

import main.Tools;

import java.util.Objects;

/**
 * This class represents the Pickaxe tool
 * Extends the Tools class
 */
public class Pickaxe extends Tools {

    /**
     * Constructor for Pickaxe class
     * Calls the super constructor to set all the pickaxe tool properties
     */
    public Pickaxe() {
        super("Pickaxe", 50, 15);
    }

    /**
     * Checks if the tile contains rock
     * @return true if tile contains rock, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Rock");}

    /**
     * Gets the description of the tool
     * @return the description of the tool
     */
    @Override
    public String getToolDescription() {
        String description = "Removes a rock from a tile.\n"
                + "Can only be applied to tiles with a rock.";
        return description;
    }
}
