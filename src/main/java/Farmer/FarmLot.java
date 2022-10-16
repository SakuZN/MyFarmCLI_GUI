package Farmer;

import main.Seeds;
import main.Tools;
import Tools.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class FarmLot {

    /*
     * 0 - Unplowed
     * 1 - Plowed
     * 2 - Planted
     * 3 - Withered
     * 4 - Rock
     */
    private final String[] LOTSTATE = {"Unplowed", "Plowed", "Planted", "Withered", "Rock"};

    private int[][] lotTiles;

    private ArrayList<Seeds> seeds = new ArrayList<>();
    private ArrayList<Seeds> plantedSeeds = new ArrayList<>();
    private ArrayList<Seeds> harvestSeeds = new ArrayList<>();
    private ArrayList<Tools> tools = new ArrayList<>();
    private String lotName;

    Random random = new Random();

    public FarmLot(String lotName) {
        this.lotName = lotName;
        lotTiles = new int [10][5];
        for (int i = 0; i < lotTiles.length; i++)
            for (int j = 0; j < lotTiles[i].length; j++) {
                int dist = random.nextInt(1, 100);
                if (dist < 70)
                    lotTiles[i][j] = 0;
                else
                    lotTiles[i][j] = 4;
            }

        tools.add(new Plow());
        tools.add(new WateringCan());
        tools.add(new Fertilizer());
        tools.add(new Pickaxe());
        tools.add(new Shovel());
    }


    //Add bought seed to current inventory
    public void increaseSeed (Seeds seed, String farmerType) {seeds.add(new Seeds(seed, farmerType));}
    public String getSeedsString(String seedString, ArrayList<Seeds> seeds) {
        int index = 0;
        StringBuilder seedStringBuilder = new StringBuilder(seedString);
        for(Seeds seed: seeds)
        {
            index++;
            seedStringBuilder.append(index).append(". ").append(seed.getSeedName()).append("\n");
        }
        seedString = seedStringBuilder.toString();
        return seedString;
    }

    public ArrayList<Seeds> getSeeds() {return seeds;}

    public ArrayList<Seeds> getPlantedSeeds() {return plantedSeeds;}

    public ArrayList<Seeds> getHarvestSeeds() {return harvestSeeds;}

    public ArrayList<Tools> getTools() {return tools;}

    public int getWitheredPlantIndex (ArrayList<Seeds> plantedSeeds, int xCoord, int yCoord) {
        int index = 0;

        for (Seeds wither: plantedSeeds) {
            if (wither.getSeedWithered() == 1 && wither.getxCoord() == xCoord && wither.getyCoord() == yCoord)
                return index;
            index++;
        }
        return -1;
    }
    public int getPlantedSeedIndex (ArrayList<Seeds> plantedSeeds, int xCoord, int yCoord) {
        int index = 0;

        for (Seeds seed: plantedSeeds) {
            if (seed.getSeedWithered() == 0 && seed.getxCoord() == xCoord && seed.getyCoord() == yCoord)
                return index;
            index++;
        }
        return -1;
    }

    Predicate<Seeds> canHarvest = Seeds::canHarvestseed;

    /**
     * 'o' - unplowed
     * '*' - plowed
     * '#' - plowed and planted
     * '-' - withered
     * 'x' - rock
     */
    public void printLot() {
        for (int[] ints : lotTiles) {
            for (int anInt : ints) {
                if (anInt == 0)
                    System.out.print("o" + " ");
                else if (anInt == 1)
                    System.out.print("*" + " ");
                else if (anInt == 2)
                    System.out.print("#" + " ");
                else if (anInt == 3)
                    System.out.print("-" + " ");
                else
                    System.out.print("x" + " ");
            }
            System.out.println();
        }
    }
    public int[][] getLotTiles () {return lotTiles;}

    public int getPlowedTiles() {
        int noOfPlowedTiles = 0;

        for (int [] rows : lotTiles)
            for (int cols : rows )
                if (cols == 1)
                    noOfPlowedTiles++;

        return noOfPlowedTiles;
    }
    public int getRocks() {
        int noOfRocks = 0;

        for (int [] rows : lotTiles)
            for (int cols : rows )
                if (cols == 4)
                    noOfRocks++;

        return noOfRocks;
    }
    //Tracks the number of seeds planted ready to be harvested
    public int getHarvestCount() {
        int harvestCount = 0;
        for (Seeds seed : getPlantedSeeds())
            if (seed.canHarvestseed())
                harvestCount++;

        return harvestCount;
    }


    public boolean lotWithered() {
        int noOfAliveTiles = 0;
        for (int [] rows : lotTiles)
            for (int cols : rows )
                if (cols == 0 || cols == 1 || cols == 2)
                    noOfAliveTiles++;
        return noOfAliveTiles == 0;
    }

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
    public void setTileStatus (int xCoord, int yCoord, int tileStatus) {lotTiles[xCoord][yCoord] = tileStatus;}

    public String getFarmName() { return lotName; }
}
