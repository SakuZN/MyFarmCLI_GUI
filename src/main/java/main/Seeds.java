package main;

import java.util.Random;

public class Seeds {

    private String seedType;

    private String seedName;
    private int harvestTime;

    private int waterNeeds;
    private int bonusWater;

    private int fertilizerNeeds;
    private int bonusFertilizer;

    private int minProduce;
    private int maxProduce;

    private Random random = new Random();
    private int actualProduce;
    private int seedCost;
    private int baseSell;
    private double xpYield;

    private int readyHarvest = 0;
    private int waterCount = 0;
    private int bonusWC = 0;
    private int fertilizerCount = 0;
    private int bonusFC = 0;
    // 0 - healthy, 1 - withered
    private int seedWithered = 0;

    private int isCurrentlyPlanted = 0;
    private int xCoord;
    private int yCoord;

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
        if (!this.seedType.equals("Flower"))
            this.actualProduce = random.nextInt(minProduce, maxProduce);
        else
            this.actualProduce = 1;
    }

    //Constructor used for when buying seed in store
    public Seeds (Seeds seed, String farmerType) {
        seedType = seed.getSeedType();
        seedName = seed.getSeedName();
        harvestTime = seed.getHarvestTime();
        waterNeeds = seed.getWaterNeeds();
        bonusWater = seed.getBonusWater() + setExtraBonusWC(farmerType);
        fertilizerNeeds = seed.getFertilizerNeeds();
        bonusFertilizer = seed.getBonusFertilizer() + setExtraBonusFC(farmerType);
        minProduce = seed.getMinProduce();
        maxProduce = seed.getMaxProduce();
        seedCost = seed.getSeedCost();
        baseSell = seed.getBaseSell();
        xpYield = seed.getXpYield();

        if (!this.seedType.equals("Flower"))
            this.actualProduce = random.nextInt(minProduce, maxProduce);
        else
            this.actualProduce = 1;

        readyHarvest = 0;
        waterCount = 0;
        fertilizerCount = 0;

    }

    public void grow() { readyHarvest++;}

    public void waterseed() {
        if (waterCount < waterNeeds)
            waterCount++;
        else {
            bonusWC++;
            if (bonusWC >= getBonusWater())
                bonusWC = getBonusWater();
        }

    }

    public void fertilizeseed() {
        if (fertilizerCount < fertilizerNeeds)
            fertilizerCount++;
        else {
            bonusFC++;
            if (bonusFC >= getBonusFertilizer())
                bonusFC = getBonusFertilizer();
        }

    }

    public boolean canHarvestseed() {
        return readyHarvest == harvestTime && waterCount == waterNeeds && fertilizerCount == fertilizerNeeds;
    }

    public void checkIfWither() {
        if  ((readyHarvest == harvestTime && (waterCount != waterNeeds || fertilizerCount != fertilizerNeeds))
                || readyHarvest > harvestTime )
            seedWithered = 1;
    }

    public String getSeedType() {return seedType;}

    public String getSeedName() {return seedName;}

    public int getHarvestTime() {return harvestTime;}
    public int getDaysHarvested() {return readyHarvest;}

    public int getWaterNeeds() {return waterNeeds;}
    public int getWaterCount() {return waterCount;}

    public int getBonusWater() {return bonusWater;}
    public int getBonusWC() {return bonusWC;}
    private int setExtraBonusWC(String farmertype) {
        return switch (farmertype) {
            case "Distinguished Farmer" -> 1;
            case "Legendary Farmer" -> 2;
            default -> 0;
        };
    }

    public int getFertilizerNeeds() {return fertilizerNeeds;}
    public int getFertilizerCount() {return fertilizerCount;}

    public int getBonusFertilizer() {return bonusFertilizer;}
    public int getBonusFC() {return bonusFC;}

    private int setExtraBonusFC(String farmertype) {
        if ("Legendary Farmer".equals(farmertype))
                return 1;
        return 0;
    }

    public int getMinProduce() {return minProduce;}

    public int getMaxProduce() {return maxProduce;}

    public int getActualProduce() {return actualProduce;}

    public int getSeedCost() {return seedCost;}

    public int getBaseSell() {return baseSell;}

    public double getXpYield() {return xpYield;}

    public int getSeedStatus() {return isCurrentlyPlanted;}

    public int getSeedWithered() {return seedWithered;}

    public void setSeedStatus (int status) {isCurrentlyPlanted = status;}

    public int getxCoord() {return xCoord;}

    public int getyCoord() {return yCoord;}

    public void setxCoord(int value) {xCoord = value;}

    public void setyCoord(int value) {yCoord = value;}

    //boolean method for plant tree with special condition
    public boolean canPlantTree(int xCoord, int yCoord, int[][] lotTile){
        try {
            //Try check all sides if free of objects or within bounds of farm lot
            if (lotTile[xCoord][yCoord] == 1 && lotTile[xCoord + 1][yCoord] < 2 && lotTile[xCoord - 1][yCoord] < 2
                    && lotTile[xCoord][yCoord - 1] < 2 && lotTile[xCoord][yCoord + 1] < 2
                    && lotTile[xCoord + 1][yCoord - 1] < 2 && lotTile[xCoord + 1][yCoord + 1] < 2
                    && lotTile[xCoord - 1][yCoord - 1] < 2 && lotTile[xCoord - 1][yCoord + 1] < 2)
                    return true;
            else
                return false;
            //when outside the bounds of farm lot, catch exception and return false
        } catch(Exception e){
            return false;
        }

    }
    private double harvestTotal (String farmerType) {
        return switch (farmerType) {
            case "Registered Farmer" -> getActualProduce() * (getBaseSell() + 1);
            case "Distinguished Farmer" -> getActualProduce() * (getBaseSell() + 2);
            case "Legendary Farmer" -> getActualProduce() * (getBaseSell() + 4);
            default -> getActualProduce() * (getBaseSell());
        };
    }
    private double waterBonus (String farmerType) {
        return harvestTotal(farmerType) * 0.2 * (getWaterCount() + getBonusWater() - 1);
    }
    private double fertilizerBonus (String farmerType) {
        return harvestTotal(farmerType) * 0.5 * (getFertilizerCount() + getBonusFertilizer());
    }

    public double finalHarvestPrice (String farmerType, String seedType) {
        if (seedType.equals("Flower"))
            return (harvestTotal(farmerType) + waterBonus(farmerType) + fertilizerBonus(farmerType)) * 1.1;
        return harvestTotal(farmerType) + waterBonus(farmerType) + fertilizerBonus(farmerType);
    }

    public void showSeedInfo() {
        System.out.println("\n" + getSeedName());
        System.out.println("Purchase price: $" + getSeedCost());
        System.out.println("Products Produced: " + getMinProduce() + "-" + getMaxProduce());
        System.out.println("Sell Price per piece: $" + getBaseSell());
        System.out.println("Harvest Time: " + getHarvestTime() + " days");
        System.out.println("Water Needs (bonus limit): " + getWaterNeeds() + " (" + getBonusWater() + ")");
        System.out.println("Fertilizer Needs (bonus limit): " + getFertilizerNeeds() + " (" + getBonusFertilizer() +
                ")");
        System.out.println("Experience yield: " + getXpYield() + "xp");
    }

    public void showPlantedSeedInfo() {
        System.out.println("Seed name: " + getSeedName());
        System.out.println("Seed type: " + getSeedType());
        System.out.printf("Planted on (%d, %d)\n", getxCoord() + 1, getyCoord() + 1);
        System.out.println("Days until harvest: " + (getHarvestTime() - getDaysHarvested()));
        System.out.printf("Has been watered (%d) times (bonus: %d),  needs (%d)\n",
                getWaterCount(), getBonusFC(), getWaterNeeds());
        System.out.printf("Has been fertilized (%d) times (bonus: %d), needs (%d)\n\n",
                getFertilizerCount(), getBonusFC(),
                getFertilizerNeeds());
    }

    public String getSeedInfo() {
        if (!getSeedType().equals("Fruit Tree"))
            return  getSeedName() + "\n" +
                    "Purchase price: $" + getSeedCost() + "\n" +
                    "Products Produced: " + getMinProduce() + "-" + getMaxProduce() + "\n" +
                    "Sell Price per piece: $" + getBaseSell() + "\n" +
                    "Harvest Time: " + getHarvestTime() + " days" + "\n" +
                    "Water Needs (bonus limit): " + getWaterNeeds() + " (" + getBonusWater() + ")" + "\n" +
                    "Fertilizer Needs (bonus limit): " + getFertilizerNeeds() + " (" + getBonusFertilizer() + ")" + "\n" +
                    "Experience yield: " + getXpYield() + "xp";
        else //When seed is of type FruitTree, show different info
            return  getSeedName() + "\n" +
                    "Purchase price: $" + getSeedCost() + "\n" +
                    "Products Produced: " + getMinProduce() + "-" + getMaxProduce() + "\n" +
                    "Sell Price per piece: $" + getBaseSell() + "\n" +
                    "Harvest Time: " + getHarvestTime() + " days" + "\n" +
                    "Water Needs (bonus limit): " + getWaterNeeds() + " (" + getBonusWater() + ")" + "\n" +
                    "Fertilizer Needs (bonus limit): " + getFertilizerNeeds() + " (" + getBonusFertilizer() + ")" + "\n" +
                    "Experience yield: " + getXpYield() + "xp" +
                    "\n\nSPECIAL CONDITION:\nThis seed must be planted on a tile where tiles on its adjacent and" +
                    " diagonal side are free of objects";
    }

    public String getPlantedSeedInfo () {
        return "Seed name: " + getSeedName() + "\n" +
                "Seed type: " + getSeedType() + "\n" +
                "Planted on (" + (getxCoord()) + ", " + (getyCoord()) + ")\n" +
                "Days until harvest: " + (getHarvestTime() - getDaysHarvested()) + "\n" +
                "Has been watered (" + getWaterCount() + ") times (bonus: "
                + getBonusWC() + "),  needs (" + getWaterNeeds() + ")\n" +
                "Has been fertilized (" + getFertilizerCount() + ") times (bonus: "
                + getBonusFC() + "), needs (" + getFertilizerNeeds() + ")\n\n";
    }

    public String getPlantedSeedFertilizedInfo(){
        return "Has been fertilized (" + getFertilizerCount() + ") times (bonus: "
                + getBonusFC() + "), needs (" + getFertilizerNeeds() + ")\n";
    }
    public String getPlantedSeedWaterInfo() {
        return "Has been watered (" + getWaterCount() + ") times (bonus: "
                + getBonusWC() + "),  needs (" + getWaterNeeds() + ")\n";
    }

    public void harvestInfo(String farmerType) {
        System.out.println("\nSeed name: " + getSeedName());
        System.out.println("Seed type: " + getSeedType());
        System.out.printf("Planted on (%d, %d)\n", getxCoord() + 1, getyCoord() + 1);
        System.out.printf("Has been watered (%d) times, bonus (%d)\n", getWaterCount(),
                getBonusWC());
        System.out.printf("Has been fertilized (%d) times, bonus (%d)\n\n",
                getFertilizerCount(),
                getBonusFC());
        System.out.println("Products produced: " + getActualProduce());
        System.out.println("Final harvest price: " +
                finalHarvestPrice(farmerType, getSeedType()));
    }
    public String getHarvestInfo(String farmerType) {
        return "\nSeed name: " + getSeedName() + "\n" +
                "Seed type: " + getSeedType() + "\n" +
                "Planted on (" + (getxCoord()) + ", " + (getyCoord()) + ")\n\n" +
                "Has been watered (" + getWaterCount() + ") times, bonus (" + getBonusWC() + ")\n" +
                "Has been fertilized (" + getFertilizerCount() + ") times, bonus (" + getBonusFC() + ")\n\n" +
                "Products produced: " + getActualProduce() + "\n" +
                "Final harvest price: " + finalHarvestPrice(farmerType, getSeedType());
    }
}
