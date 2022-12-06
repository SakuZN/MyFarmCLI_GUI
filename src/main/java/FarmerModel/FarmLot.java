package FarmerModel;

import main.Seeds;
import main.Tools;
import Tools.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the farm lot. It contains the lot tile which the player interacts with.
 * It also contains the seed and tool inventory.
 */
public class FarmLot {

    /**Array contain the different state a lot can be in.*/
    private final String[] LOTSTATE = {"Unplowed", "Plowed", "Planted", "Withered", "Rock"};

    /**Name of the lot. */
    private final String lotName;

    /**2D array of lot tiles. Each lot tile have different states assigned to it. */
    private int[][] lotTiles;

    /**Arraylist of seeds. */
    private ArrayList<Seeds> seeds = new ArrayList<>();

    /**Arraylist of currently planted seeds. */
    private ArrayList<Seeds> plantedSeeds = new ArrayList<>();

    /**Arraylist of seeds ready to be harvested. */
    private ArrayList<Seeds> harvestSeeds = new ArrayList<>();

    /**Arraylist of tools. */
    private ArrayList<Tools> tools = new ArrayList<>();

    /**
     * Constructor for FarmLot class
     * Lot tiles are randomly generated given the seed from 1 to 100
     * If the generated number is greater than 70, the tile is a rock, else the tile is unplowed.
     * Tools are also added to the inventory
     * @param lotName Name of the lot
     * @param lotTilesFile 2d array of char that represents the lot tiles initial placement
     */
    public FarmLot(String lotName, char[][] lotTilesFile) {
        this.lotName = lotName;
        lotTiles = new int [10][5];
        for (int i = 0; i < lotTiles.length; i++)
            for (int j = 0; j < lotTiles[i].length; j++) {
                if (lotTilesFile[i][j] == 'x')
                    lotTiles[i][j] = 4;
                else
                    lotTiles[i][j] = 0;
            }
        tools.add(new Plow());
        tools.add(new WateringCan());
        tools.add(new Fertilizer());
        tools.add(new Pickaxe());
        tools.add(new Shovel());
    }


    /**
     * Get the name of the lot
     * @return Name of the lot
     */
    public String getFarmName() { return lotName; }

    /**
     * Get the list of seeds
     * @return List of seeds
     */
    public ArrayList<Seeds> getSeeds() {return seeds;}


    /**
     * Get the list of planted seeds
     * @return List of planted seeds
     */
    public ArrayList<Seeds> getPlantedSeeds() {return plantedSeeds;}

    /**
     * Get the list of seeds ready to be harvested
     * @return List of seeds ready to be harvested
     */
    public ArrayList<Seeds> getHarvestSeeds() {return harvestSeeds;}

    /**
     * Get the list of tools
     * @return List of tools
     */
    public ArrayList<Tools> getTools() {return tools;}

    /**
     * Checks for a plant that has withered and return its index in the arraylist of planted seeds
     * @param plantedSeeds Arraylist of planted seeds
     * @param xCoord x-coordinate of the lot tile
     * @param yCoord y-coordinate of the lot tile
     * @return Index of the withered plant in the arraylist of planted seeds
     */
    public int getWitheredPlantIndex (ArrayList<Seeds> plantedSeeds, int xCoord, int yCoord) {
        int index = 0;

        for (Seeds wither: plantedSeeds) {
            if (wither.getSeedWithered() == 1 && wither.getxCoord() == xCoord && wither.getyCoord() == yCoord)
                return index;
            index++;
        }
        return -1;
    }

    /**
     * Checks for a plant that has withered and return number of withered plants
     * @return Number of withered plants
     */
    public int getNumberOfWitheredPlants() {
        int count = 0;
        for (Seeds wither: plantedSeeds) {
            if (wither.getSeedWithered() == 1)
                count++;
        }
        return count;
    }

    /**
     * Checks for a plant and return its index in the arraylist of planted seeds
     * @param plantedSeeds Arraylist of planted seeds
     * @param xCoord x-coordinate of the lot tile
     * @param yCoord y-coordinate of the lot tile
     * @return Index of the plant in the arraylist of planted seeds
     */
    public int getPlantedSeedIndex (ArrayList<Seeds> plantedSeeds, int xCoord, int yCoord) {
        int index = 0;

        for (Seeds seed: plantedSeeds) {
            if (seed.getSeedWithered() == 0 && seed.getxCoord() == xCoord && seed.getyCoord() == yCoord)
                return index;
            index++;
        }
        return -1;
    }

    /**
     * Get the lot tile
     * @return Lot tile
     */
    public int[][] getLotTiles () {return lotTiles;}

    /**
     * Loops through arraylist of planted seeds and counts how many plants are ready to be harvested
     * @return Number of plants ready to be harvested
     */
    public int getHarvestCount() {
        int harvestCount = 0;
        for (Seeds seed : getPlantedSeeds())
            if (seed.canHarvestSeed())
                harvestCount++;

        return harvestCount;
    }

    /**
     * Get the status or state of a specific tile
     * @param xCoord x-coordinate of the lot tile
     * @param yCoord y-coordinate of the lot tile
     * @return Status or state of the lot tile
     */
    public String getTileStatus(int xCoord, int yCoord) {
        int tileStatus = lotTiles[xCoord][yCoord];

        return switch (tileStatus) {
            case 0 -> LOTSTATE[0];
            case 1 -> LOTSTATE[1];
            case 2 -> LOTSTATE[2];
            case 3 -> LOTSTATE[3];
            case 4 -> LOTSTATE[4];
            default -> "Null";
        };

    }

    /**
     * Method to add seed to the inventory
     * Uses seed constructor that initializes the seed's to the parameter seed
     * @param seed Seed to be added to the inventory
     */
    public void increaseSeed (Seeds seed) {seeds.add(new Seeds(seed));}

    /**
     * Sets the lot tile to a specific state
     * @param xCoord x-coordinate of the lot tile
     * @param yCoord y-coordinate of the lot tile
     * @param tileStatus State of the lot tile
     */
    public void setTileStatus (int xCoord, int yCoord, int tileStatus) {lotTiles[xCoord][yCoord] = tileStatus;}

    /**
     * Loops through the planted seeds arraylist and checks if all are withered
     * If yes, that is considered a withered lot, else not
     * @return True if lot withered, false if not
     */
    public boolean lotWithered() {
        //Check if all seeds that are planted are dead
        int witheredCount = 0;
        for (Seeds seed : getPlantedSeeds())
            if (seed.getSeedWithered() == 1)
                witheredCount++;

        return witheredCount == getPlantedSeeds().size() && witheredCount != 0;
    }

}
