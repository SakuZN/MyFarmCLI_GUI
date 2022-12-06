package main;

import java.util.Random;

/**
 * This class is the parent class for all seeds.
 * Important class that is always used in the game.
 * Accessed and modified by the farmer.
 */
public class Seeds {

    /**The type of seed. */
    private String seedType;

    /**The name of the seed. */
    private String seedName;

    /**Days needed for harvest. */
    private int harvestTime;

    /**The amount of water needed for the seed. */
    private int waterNeeds;

    /**The amount of bonus water the seed can be watered. */
    private int bonusWater;

    /**The amount of fertilizer needed for the seed. */
    private int fertilizerNeeds;

    /**The amount of bonus fertilizer the seed can be fertilized. */
    private int bonusFertilizer;

    /**The minimum produce amount of the seed. */
    private int minProduce;

    /**The maximum produce amount of the seed. */
    private int maxProduce;

    /**The actual produce from range (minProduce, maxProduce). */
    private int actualProduce;

    /**The amount of money the seed is worth. */
    private int seedCost;

    /**The amount of money the produce is worth. */
    private int baseSell;

    /**XP yield from harvesting this seed. */
    private double xpYield;

    /**Seed state wherein: 1 - ready to be harvested, 0 - not ready to be harvested. */
    private int readyHarvest = 0;

    /**Counts how many times the seed has been watered. */
    private int waterCount = 0;

    /**Counts how many times the seed has been watered above its needed count, limited by the bonus limit. */
    private int bonusWC = 0;

    /**Counts how many times the seed has been fertilized. */
    private int fertilizerCount = 0;

    /**Counts how many times the seed has been fertilized above its needed count, limited by the bonus limit. */
    private int bonusFC = 0;

    /**Seed state wherein: 1 - Seed has withered, 0 - Seed is healthy. */
    private int seedWithered = 0;

    /**X-Coordinate location of the seed in the tile. */
    private int xCoord;

    /**Y-Coordinate location of the seed in the tile. */
    private int yCoord;

    /**
     * Constructor for Seeds class
     * Default values are initialized depending on the seed specification
     * @param seedType Seed type
     * @param seedName Seed name
     * @param harvestTime Harvest time
     * @param waterNeeds Water needs
     * @param bonusWater Bonus water
     * @param fertilizerNeeds Fertilizer needs
     * @param bonusFertilizer Bonus fertilizer
     * @param minProduce Minimum produce
     * @param maxProduce Maximum produce
     * @param seedCost Seed cost
     * @param baseSell Base sell
     * @param xpYield XP yield
     */
    public Seeds(String seedType, String seedName, int harvestTime, int waterNeeds, int bonusWater, int fertilizerNeeds,
                 int bonusFertilizer, int minProduce, int maxProduce, int seedCost, int baseSell, double xpYield){
        this.seedType = seedType;
        this.seedName = seedName;
        this.harvestTime = harvestTime;
        this.waterNeeds = waterNeeds;
        this.bonusWater = bonusWater;;
        this.fertilizerNeeds = fertilizerNeeds;
        this.bonusFertilizer = bonusFertilizer;
        this.minProduce = minProduce;
        this.maxProduce = maxProduce;
        this.seedCost = seedCost;
        this.baseSell = baseSell;
        this.xpYield = xpYield;
    }

    /**
     * Constructor used when buying from store or transferring seeds from inventory
     * @param seed Seed to be bought or transferred
     */
    public Seeds (Seeds seed) {
        this.seedType = seed.getSeedType();
        this.seedName = seed.getSeedName();
        this.harvestTime = seed.getHarvestTime();
        this.waterNeeds = seed.getWaterNeeds();
        this.bonusWater = seed.getBonusWater();
        this.fertilizerNeeds = seed.getFertilizerNeeds();
        this.bonusFertilizer = seed.getBonusFertilizer();
        this.minProduce = seed.getMinProduce();
        this.maxProduce = seed.getMaxProduce();
        this.seedCost = seed.getSeedCost();
        this.baseSell = seed.getBaseSell();
        this.xpYield = seed.getXpYield();

        Random random = new Random();
        if (!this.seedType.equals("Flower"))
            this.actualProduce = random.nextInt(minProduce, maxProduce + 1);
        else
            this.actualProduce = 1;

        this.readyHarvest = 0;
        this.waterCount = 0;
        this.fertilizerCount = 0;

    }

    /**
     * Grows the seed by incrementing the readyHarvest variable
     */
    public void grow() { readyHarvest++;}

    /**
     * Water the seed by incrementing the waterCount variable
     * If the seed already met waterNeeds, increment bonusWC
     * If bonusWC also reached bonusWater, prevent further increment
     */
    public void waterSeed() {
        if (waterCount < waterNeeds)
            waterCount++;
        else {
            bonusWC++;
            if (bonusWC >= getBonusWater())
                bonusWC = getBonusWater();
        }
    }

    /**
     * Fertilize the seed by incrementing the fertilizerCount variable
     * If the seed already met fertilizerNeeds, increment bonusFC
     * If bonusFC also reached bonusFertilizer, prevent further increment
     */
    public void fertilizeSeed() {
        if (fertilizerCount < fertilizerNeeds)
            fertilizerCount++;
        else {
            bonusFC++;
            if (bonusFC >= getBonusFertilizer())
                bonusFC = getBonusFertilizer();
        }

    }

    /**
     * Get the seed type
     * @return Seed type
     */
    public String getSeedType() {return seedType;}

    /**
     * Get the seed name
     * @return Seed name
     */
    public String getSeedName() {return seedName;}

    /**
     * Get the harvest time
     * @return Harvest time
     */
    public int getHarvestTime() {return harvestTime;}

    /**
     * Get days left to harvest
     * @return Days left to harvest
     */
    public int getDaysHarvested() {return readyHarvest;}

    /**
     * Get the water needs
     * @return Water needs
     */
    public int getWaterNeeds() {return waterNeeds;}

    /**
     * Get the bonus water
     * @return Bonus water
     */
    public int getWaterCount() {return waterCount;}

    /**
     * Get the bonus water limit
     * @return Bonus water limit
     */
    public int getBonusWater() {return bonusWater;}

    /**
     * Get bonus water count
     * @return Bonus water count
     */
    public int getBonusWC() {return bonusWC;}

    /**
     * Get the fertilizer needs
     * @return Fertilizer needs
     */
    public int getFertilizerNeeds() {return fertilizerNeeds;}

    /**
     * Get the fertilizer count
     * @return fertilizer count
     */
    public int getFertilizerCount() {return fertilizerCount;}

    /**
     * Get the bonus fertilizer limit
     * @return Bonus fertilizer limit
     */

    public int getBonusFertilizer() {return bonusFertilizer;}

    /**
     * Get the bonus fertilizer count
     * @return Bonus fertilizer count
     */
    public int getBonusFC() {return bonusFC;}

    /**
     * Get the minimum produce
     * @return Minimum produce
     */
    public int getMinProduce() {return minProduce;}

    /**
     * Get the maximum produce
     * @return Maximum produce
     */
    public int getMaxProduce() {return maxProduce;}

    /**
     * Get the actual produce from range (minProduce, maxProduce)
     * @return Actual produce
     */
    public int getActualProduce() {return actualProduce;}

    /**
     * Get the seed cost
     * @return Seed cost
     */
    public int getSeedCost() {return seedCost;}

    /**
     * Get the base sell or base price from selling the produce of seeds
     * @return Base sell price
     */
    public int getBaseSell() {return baseSell;}

    /**
     * Get the XP yield
     * @return XP yield
     */
    public double getXpYield() {return xpYield;}

    /**
     * get the state of the seed, 1 is withered, 0 is not withered
     * @return State of the seed
     */
    public int getSeedWithered() {return seedWithered;}

    /**
     * Get the x-coordinate of the seed
     * @return X-coordinate of the seed
     */
    public int getxCoord() {return xCoord;}

    /**
     * Get the y-coordinate of the seed
     * @return Y-coordinate of the seed
     */
    public int getyCoord() {return yCoord;}

    /**
     * Get the detailed information of the seed
     * @return Detailed String information of the seed
     */
    public String getSeedInfo() {
        if (!getSeedType().equals("Fruit Tree"))
            return  String.format("""
                    Seed Name: %s
                    Seed Type: %s
                    
                    Purchase Price: $%d
                    Products per Harvest: (%d - %d)
                    Sell Price Per Piece: $%d
                    
                    Harvest Time: %d days
                    Water Needs: %d (Bonus limit: %d)
                    Fertilizer Needs: %d (Bonus limit: %d)
                    Experience Yield: %.1f
                    """, getSeedName(), getSeedType(), getSeedCost(), getMinProduce(), getMaxProduce(), getBaseSell(),
                    getHarvestTime(), getWaterNeeds(), getBonusWater(), getFertilizerNeeds(), getBonusFertilizer(),
                    getXpYield());
        else //When seed is of type FruitTree, show different info
            return  String.format("""
                    Seed Name: %s
                    Seed Type: %s
                    
                    Purchase Price: $%d
                    Products per Harvest: (%d - %d)
                    Sell Price Per Piece: $%d
                    
                    Harvest Time: %d days
                    Water Needs: %d (Bonus limit: %d)
                    Fertilizer Needs: %d (Bonus limit: %d)
                    Experience Yield: %.1f
                    
                    SPECIAL CONDITION: Must be planted on a tile where\040
                    its 3x3 area is free of objects.
                            """, getSeedName(), getSeedType(), getSeedCost(), getMinProduce(), getMaxProduce(), getBaseSell(),
                    getHarvestTime(), getWaterNeeds(), getBonusWater(), getFertilizerNeeds(), getBonusFertilizer(),
                    getXpYield());
    }

    /**
     * Get detailed information of the planted seed
     * @return Detailed String information of the planted seed
     */
    public String getPlantedSeedInfo () {
        return String.format( """
                Seed Name: %s
                Seed Type: %s
                Planted on: (%d, %d)
                Days until harvest: %d
                Water Count: (%d | %d), bonus: (%d | %d)
                Fertilizer Count: (%d | %d), bonus: (%d | %d)
                """,getSeedName(), getSeedType(), getxCoord(), getyCoord(), (getHarvestTime() - getDaysHarvested()),
                getWaterCount(), getWaterNeeds(), getBonusWC(), getBonusWater(),
                getFertilizerCount(), getFertilizerNeeds(), getBonusFC(), getBonusFertilizer());
    }

    /**
     * Get the information of the harvest or produce of the seed
     * @param farmerType Farmer type
     * @return String information of the harvest or produce of the seed
     */
    public String getHarvestInfo(String farmerType) {
        return String.format("""
                Seed Name: %s
                Seed Type: %s
                Planted on: (%d, %d)
                Has been watered: %d times (Bonus: %d)
                Has been fertilized: %d times (Bonus: %d)
                Products produced: %d
                Final harvest price: $%.2f
                
                Gained %.1f XP!
                """, getSeedName(), getSeedType(), getxCoord(), getyCoord(), getWaterCount(), getBonusWC(),
                getFertilizerCount(), getBonusFC(), getActualProduce(), finalHarvestPrice(farmerType, getSeedType())
                , getXpYield());
    }

    /**
     * Set the seeds x-coordinate in the lot tile
     * @param value X-coordinate of the seed
     */
    public void setxCoord(int value) {xCoord = value;}

    /**
     * Set the seeds y-coordinate in the lot tile
     * @param value Y-coordinate of the seed
     */
    public void setyCoord(int value) {yCoord = value;}

    /**
     * Used to discount the seed cost when buying from store depending on player's farmer type
     * @param farmerType Farmer type
     */
    protected void setDiscountSeedCost(String farmerType) {
        switch (farmerType) {
            case "Registered Farmer" -> seedCost -= 1;
            case "Distinguished Farmer" -> seedCost -= 2;
            case "Legendary Farmer" -> seedCost -= 3;
        }
    }

    /**
     * Used to increase bonus fertilizer limit when buying from store depending on player's farmer type
     * @param farmerType Farmer type
     */
    protected void setExtraBonusFC(String farmerType) {
        if (farmerType.equals("Legendary Farmer"))
            bonusFertilizer += 1;
    }

    /**
     * Used to increase bonus water limit when buying from store depending on player's farmer type
     * @param farmerType Farmer type
     */
    protected void setExtraBonusWC(String farmerType) {
        switch (farmerType) {
            case "Distinguished Farmer" -> bonusWater += 1;
            case "Legendary Farmer" -> bonusWater += 2;
        }
    }

    /**
     * Checks if seed can be harvested
     * @return True if seed can be harvested, false if not
     */
    public boolean canHarvestSeed() {
        return readyHarvest == harvestTime && waterCount == waterNeeds && fertilizerCount == fertilizerNeeds;
    }

    /**
     * Checks if seed is withered
     * If withered, set seed withered state to 1
     */
    public void checkIfWither() {
        if  ((readyHarvest == harvestTime && (waterCount != waterNeeds || fertilizerCount != fertilizerNeeds))
                || readyHarvest > harvestTime )
            seedWithered = 1;
    }

    /**
     * Method used only by seed type "Fruit Tree" to check if the 3x3 area is free of objects
     * @param xCoord X-coordinate of the seed
     * @param yCoord Y-coordinate of the seed
     * @param lotTile Lot tile status of the surrounding 3x3 area
     * @return True if 3x3 area is free of objects, false if not
     */
    public boolean canPlantTree(int xCoord, int yCoord, int[][] lotTile){
        try {
            //Try check all sides if free of objects or within bounds of farm lot
            return lotTile[xCoord][yCoord] == 1 && lotTile[xCoord + 1][yCoord] < 2 && lotTile[xCoord - 1][yCoord] < 2
                    && lotTile[xCoord][yCoord - 1] < 2 && lotTile[xCoord][yCoord + 1] < 2
                    && lotTile[xCoord + 1][yCoord - 1] < 2 && lotTile[xCoord + 1][yCoord + 1] < 2
                    && lotTile[xCoord - 1][yCoord - 1] < 2 && lotTile[xCoord - 1][yCoord + 1] < 2;
            //when outside the bounds of farm lot, catch exception and return false
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Method to calculate the final harvest price of the seed
     * Formula: (HarvestTotal + WaterBonus + FertilizerBonus) * FarmerTypeBonus
     * If seed is of type "Flower", multiply the given formula by * 1.1
     * @param farmerType Farmer type, affects seed properties that affect final harvest price
     * @param seedType Seed type
     * @return Final harvest price of the seed
     */
    public double finalHarvestPrice (String farmerType, String seedType) {
        if (seedType.equals("Flower"))
            return (harvestTotal(farmerType) + waterBonus(farmerType) + fertilizerBonus(farmerType)) * 1.1;
        else
            return harvestTotal(farmerType) + waterBonus(farmerType) + fertilizerBonus(farmerType);
    }

    /**
     * Calculates the harvest total of the seed
     * Formula: ProductsProduced * (HarvestPrice + FarmerTypeBonus)
     * @param farmerType Farmer type, affects seed properties that affect final harvest price
     * @return Harvest total of the seed
     */
    private double harvestTotal (String farmerType) {
        return switch (farmerType) {
            case "Registered Farmer" -> getActualProduce() * (getBaseSell() + 1);
            case "Distinguished Farmer" -> getActualProduce() * (getBaseSell() + 2);
            case "Legendary Farmer" -> getActualProduce() * (getBaseSell() + 4);
            default -> getActualProduce() * (getBaseSell());
        };
    }

    /**
     * Calculates the water bonus of the seed
     * Formula: HarvestTotal * 0.2 * (WaterCount + BonusWater - 1)
     * @param farmerType Farmer type, affects seed properties that affect final harvest price
     * @return Water bonus computed
     */
    private double waterBonus (String farmerType) {
        return harvestTotal(farmerType) * 0.2 * (getWaterCount() + getBonusWater() - 1);
    }

    /**
     * Calculates the fertilizer bonus of the seed
     * Formula: HarvestTotal * 0.5 * (FertilizerCount + BonusFertilizer)
     * @param farmerType Farmer type, affects seed properties that affect final harvest price
     * @return Fertilizer bonus computed
     */
    private double fertilizerBonus (String farmerType) {
        return harvestTotal(farmerType) * 0.5 * (getFertilizerCount() + getBonusFertilizer());
    }

    /**
     * Overrides the toString method to return the seed's name
     * @return Seed's name
     */
    @Override
    public String toString() {
        return seedName;
    }

}
