package Tools;

import main.Tools;

import java.util.Objects;

public class Shovel extends Tools {

    public Shovel() {
        super("Shovel", 7, 2);
    }

    /**
     * Checks if the tile contains withered harvest
     * @return true if tile contains withered harvest, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Withered");}

    @Override
    public String toolDescription() {
        String description = "Removes a withered plant from a tile.\n"
                + "Can be used on any tile with varying effects.";
        return description;
    }
}
