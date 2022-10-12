package Tools;

import main.Tools;

import java.util.Objects;

public class Plow extends Tools {

    public Plow() {
        super("Plow", 0, 0.5);
    }

    /**
     * Checks if the tile is unplowed
     * @return true if tile is unplowed, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Unplowed");}

    @Override
    public String toolDescription() {
        String description = "Converts an unplowed tile to a plowed tile.\n"
                + "Can only be performed on an unplowed tile.";
        return description;
    }
}
