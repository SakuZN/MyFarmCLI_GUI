package Tools;

import main.Tools;

import java.util.Objects;

public class Pickaxe extends Tools {

    public Pickaxe() {
        super("Pickaxe", 50, 15);
    }

    /**
     * Checks if the tile contains rock
     * @return true if tile contains rock, else false
     */
    @Override
    public boolean useTool(String tileStatus) {return Objects.equals(tileStatus, "Rock");}

    @Override
    public String toolDescription() {
        String description = "Removes a rock from a tile.\n"
                + "Can only be applied to tiles with a rock.";
        return description;
    }
}
