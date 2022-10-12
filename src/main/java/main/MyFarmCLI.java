package main;

import Farmer.FarmLot;
import Farmer.Farmer;

import java.util.Scanner;

public class MyFarmCLI{

    Farmer player;

    FarmLot playerLot;

    Store seedStore;

    Scanner scanner;

    public void initGame(){
        player = new Farmer("Zach");
        playerLot = new FarmLot("Wow");
        seedStore = new Store();

    }

    public int playerChoice(String options, int numOptions) {
        scanner = new Scanner(System.in);

        int option = -1; // No option chosen
        do
        {
            scanner = new Scanner(System.in);
            System.out.println(options);

            if (scanner.hasNextInt())
            {
                option = scanner.nextInt();
                if (option < 0 || option > numOptions)
                {
                    System.out.println("That was not an option, please try again");
                }
            }
            else
            {
                System.out.println("That is not an integer number, please try again");
            }
        }
        while(option < 0 || option > numOptions);

        return option;

    }

    public void startGame() {

        playerLot.printLot();

        String optionsString = "Please select what action you will do:\n"
                + "1. Visit Store| Buy crops\n"
                + "2. Tend to farm lot\n"
                + "3. Plant seeds\n"
                + "4. Harvest seeds\n"
                + "5. Check Inventory\n"
                + "6. View Farmer Status\n"
                + "7. Register Farmer Status\n"
                + "8. Advance Day\n"
                + "9. Exit\n";
        int numOptions = 9;

        int option = playerChoice(optionsString, numOptions);

        switch (option) {
            case 1 -> visitStore();
            case 2 -> tendLot();
            case 3 -> plantSeeds();
            case 4 -> harvestSeeds();
            case 5 -> checkInventory();
            case 6 -> viewFarmerStatus();
            case 7 -> newFarmerStatus();
            case 8 -> nextDay();
        }

        if (option != 9 && !checkFailCondition())
            startGame();
    }

    public void visitStore() {

        boolean insideStore = true;
        String optionsString = "You are at the Seed Store. Please choose an option from below:\n"
                + "1. Buy Root Crops\n"
                + "2. Buy Flowers\n"
                + "3. Buy Fruit Tree\n"
                + "4. Exit Store\n"
                + "You currently have: " + player.getObjectCoin() + "coins";
        int numOptions = 4;

        int option = playerChoice(optionsString, numOptions);

        switch (option) {
            case 1 -> buySeed("root crops");
            case 2 -> buySeed("flowers");
            case 3 -> buySeed("fruit tree");
            case 4 -> insideStore = false;
        }

        if (insideStore)
            visitStore();
    }

    public void buySeed(String seedType) {

        if (seedType.equalsIgnoreCase("root crops")) {
            System.out.println("Root Crops for sale:");
            for (Seeds seeds : seedStore.getRootCropSale())
                    seeds.showSeedInfo();

            int purchaseOption = playerChoice(playerLot.getSeedsString("0. Don't buy anything\n",
                            seedStore.getRootCropSale()),
                    seedStore.getRootCropSale().size());

            try {
                int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());
                if (player.getObjectCoin() < seedStore.getRootCropSale().get(purchaseOption - 1).getSeedCost() - farmerTypeBenefit)
                {
                    System.out.println("You don't have enough money to buy " + seedStore.getRootCropSale().get(purchaseOption - 1).getSeedName() + "!");
                }
                else if (purchaseOption != 0) // If the player chose to buy something
                {
                    playerLot.increaseSeed(seedStore.getRootCropSale().get(purchaseOption - 1),player.getFarmerType() );
                    player.decreaseObjectCoin((seedStore.getRootCropSale().get(purchaseOption - 1).getSeedCost()) - farmerTypeBenefit);
                    System.out.println(seedStore.getRootCropSale().get(purchaseOption - 1).getSeedName() + " bought!");
                    if (farmerTypeBenefit > 0)
                        System.out.println("You saved " + farmerTypeBenefit + " coin for being a " + player.getFarmerType() + "!");
                }
            } catch (Exception e) {
                System.out.println("");
            }

        }

        else if (seedType.equalsIgnoreCase("flowers")) {
            System.out.println("Flowers for sale:");
            for (Seeds seeds : seedStore.getFlowerSale())
                    seeds.showSeedInfo();

            int purchaseOption = playerChoice(playerLot.getSeedsString("0. Don't buy anything\n",
                            seedStore.getFlowerSale()),
                    seedStore.getFlowerSale().size());

            try {
                int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());
                if (player.getObjectCoin() < seedStore.getFlowerSale().get(purchaseOption - 1).getSeedCost())
                {
                    System.out.println("You don't have enough money to buy " + seedStore.getFlowerSale().get(purchaseOption - 1).getSeedName() + "!");
                }
                else if (purchaseOption != 0) // If the player chose to buy something
                {
                    playerLot.increaseSeed(seedStore.getFlowerSale().get(purchaseOption - 1), player.getFarmerType());
                    player.decreaseObjectCoin((seedStore.getFlowerSale().get(purchaseOption - 1).getSeedCost()) - farmerTypeBenefit);
                    System.out.println(seedStore.getFlowerSale().get(purchaseOption - 1).getSeedName() + " bought!");
                    if (farmerTypeBenefit > 0)
                        System.out.println("You saved " + farmerTypeBenefit + " coin for being a " + player.getFarmerType() + "!");
                }
            } catch (Exception e) {
                System.out.println("");
            }

        }
        else if (seedType.equalsIgnoreCase("fruit tree")) {
            System.out.println("Fruit Tree for sale:");
            for (Seeds seeds : seedStore.getFruitTreeSale())
                seeds.showSeedInfo();

            int purchaseOption = playerChoice(playerLot.getSeedsString("0. Don't buy anything\n",
                            seedStore.getFruitTreeSale()),
                    seedStore.getFruitTreeSale().size());

            try {
                int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());
                if (player.getObjectCoin() < seedStore.getFruitTreeSale().get(purchaseOption - 1).getSeedCost() - farmerTypeBenefit)
                {
                    System.out.println("You don't have enough money to buy " + seedStore.getFruitTreeSale().get(purchaseOption - 1).getSeedName() + "!");
                }
                else if (purchaseOption != 0) // If the player chose to buy something
                {
                    playerLot.increaseSeed(seedStore.getFruitTreeSale().get(purchaseOption - 1), player.getFarmerType());
                    player.decreaseObjectCoin((seedStore.getFruitTreeSale().get(purchaseOption - 1).getSeedCost()) - farmerTypeBenefit);
                    System.out.println(seedStore.getFruitTreeSale().get(purchaseOption - 1).getSeedName() + " bought!");
                    if (farmerTypeBenefit > 0)
                        System.out.println("You saved " + farmerTypeBenefit + " coin for being a " + player.getFarmerType() + "!");
                }
            } catch (Exception e) {
                System.out.println("");
            }

        }
    }

    public void tendLot() {

        boolean tendingLot = true;
        String optionsString = "\nYou are at your Farm Lot. Please choose an option from below:\n"
                + "1. View Tools\n"
                + "2. View status of planted seeds\n"
                + "3. Use Plow\n"
                + "4. Use Watering Can\n"
                + "5. Use Fertilizer\n"
                + "6. Use Pickaxe\n"
                + "7. Use Shovel\n"
                + "8. Leave Farm Lot\n"
                + "You currently have: " + player.getObjectCoin() + "coins";
        int numOptions = 8;

        int option = playerChoice(optionsString, numOptions);

        switch (option) {
            case 1 -> viewTools();
            case 2 -> viewPlantedSeeds();
            case 3 -> plowLot();
            case 4 -> waterSeeds();
            case 5 -> fertilizeSeeds();
            case 6 -> usePickAxe();
            case 7 -> useShovel();
            case 8 -> tendingLot = false;
        }

        if (tendingLot)
            tendLot();
    }
    public void viewTools () {
        System.out.println("Tools in Inventory:");
        for (Tools tools : playerLot.getTools()) {
            System.out.println("\n" + tools.getToolName());
            System.out.println("Description: " + tools.toolDescription());
            System.out.println("Cost Usage: " + tools.getCostUsage());
            System.out.println("Experience yield: " + tools.getCostUsage() + "xp");
        }
    }

    public void viewPlantedSeeds () {
        if (playerLot.getPlantedSeeds().size() == 0)
            System.out.println("You currently don't have anything planted!\n");
        else{
            System.out.println("Pick a seed to check");
            int seedPlantOption = playerChoice(playerLot.getSeedsString("0. Exit\n",
                    playerLot.getPlantedSeeds()), playerLot.getPlantedSeeds().size());

            try {
                //Try checks if option is valid
                Seeds plantedSeeds = playerLot.getPlantedSeeds().get(seedPlantOption - 1);

                System.out.println("Seed name: " + plantedSeeds.getSeedName());
                System.out.println("Seed type: " + plantedSeeds.getSeedType());
                System.out.printf("Planted on (%d, %d)\n", plantedSeeds.getxCoord() + 1, plantedSeeds.getyCoord() + 1);
                System.out.println("Days until harvest: " + (plantedSeeds.getHarvestTime() - plantedSeeds.getDaysHarvested()));
                System.out.printf("Has been watered (%d) times (bonus: %d),  needs (%d)\n",
                        plantedSeeds.getWaterCount(), plantedSeeds.getBonusFC(), plantedSeeds.getWaterNeeds());
                System.out.printf("Has been fertilized (%d) times (bonus: %d), needs (%d)\n\n",
                        plantedSeeds.getFertilizerCount(), plantedSeeds.getBonusFC(),
                        plantedSeeds.getFertilizerNeeds());

            } catch (Exception e) {
                if (seedPlantOption == 0)
                    System.out.println("");
                else
                    System.out.println("Not a valid option");
            }
        }
    }

    public void plowLot () {
        playerLot.printLot();

        String pickRowLot = "\nPick a row in your lot (1-10):\n";
        String pickColLot = "\nPick a column in your lot (1-5):\n";
        int rowOptions = 10;
        int colOptions = 5;
        int xCoord = playerChoice(pickRowLot, rowOptions);
        int yCoord = playerChoice(pickColLot, colOptions);

        //If tile chosen tile is valid and unplowed
        if (playerLot.getTools().get(0).useTool(playerLot.getTileStatus(xCoord - 1, yCoord - 1))){
            playerLot.setTileStatus(xCoord - 1, yCoord - 1, 1);
            player.addxp(0.5);

            System.out.printf("Successfully plowed tile at (%d)(%d)\n", xCoord, yCoord);
            System.out.println("Gained: " +playerLot.getTools().get(0).getXpYield() + "xp" );
        }
        else { //runs if tile chosen is not an unplowed tile
            System.out.println("Cannot plow on chosen tile, try again");
        }

    }

    public void waterSeeds() {
        if (playerLot.getPlantedSeeds().size() == 0)
            System.out.println("You currently don't have anything planted!\n");
        else{
            System.out.println("Pick a seed to water");
            int seedPlantOption = playerChoice(playerLot.getSeedsString("0. Exit\n",
                    playerLot.getPlantedSeeds()), playerLot.getPlantedSeeds().size());

            try {
                //Try checks if option is valid
                Seeds seeds = playerLot.getPlantedSeeds().get(seedPlantOption - 1);

                //Waters currently selected planted seed
                if (seeds.getWaterCount() == seeds.getWaterNeeds() &&
                    seeds.getBonusWater() == seeds.getBonusWC())
                    System.out.println("This seed cannot be watered anymore");
                else {
                    playerLot.getPlantedSeeds().get(seedPlantOption - 1).waterseed();
                    player.addxp(playerLot.getTools().get(1).getXpYield());

                    System.out.printf("Watered %s planted at tile (%d, %d)\n", seeds.getSeedName(), seeds.getxCoord() + 1,
                            seeds.getyCoord() + 1);
                    System.out.println("Gained: " + playerLot.getTools().get(1).getXpYield() + "xp");
                }


            } catch (Exception e) {
                if (seedPlantOption == 0)
                    System.out.println("");
                else
                    System.out.println("Not a valid option");
            }
        }
    }
    public void fertilizeSeeds() {
        if (playerLot.getPlantedSeeds().size() == 0)
            System.out.println("You currently don't have anything planted!\n");
        else if (player.getObjectCoin() <playerLot.getTools().get(2).getCostUsage())
            System.out.println("Cannot use Fertilizer, you lack coins!");
        else{
            System.out.println("Pick a seed to fertilize");
            int seedPlantOption = playerChoice(playerLot.getSeedsString("0. Exit\n",
                    playerLot.getPlantedSeeds()), playerLot.getPlantedSeeds().size());

            try {
                //Try checks if option is valid
                Seeds seeds = playerLot.getPlantedSeeds().get(seedPlantOption - 1);
                //Fertilize currently selected planted seed
                if (seeds.getFertilizerNeeds() == seeds.getFertilizerCount() &&
                    seeds.getBonusFertilizer() == seeds.getBonusFC())
                    System.out.println("This plant cannot be fertilized anymore");
                else {
                    playerLot.getPlantedSeeds().get(seedPlantOption - 1).fertilizeseed();
                    player.addxp(playerLot.getTools().get(2).getXpYield());
                    player.decreaseObjectCoin(playerLot.getTools().get(2).getCostUsage());

                    System.out.printf("Fertilized %s planted at tile (%d, %d)\n", seeds.getSeedName(),
                            seeds.getxCoord() + 1, seeds.getyCoord() + 1);
                    System.out.println("Gained: " + playerLot.getTools().get(2).getXpYield() + "xp");
                }

            } catch (Exception e) {
                if (seedPlantOption == 0)
                    System.out.println("");
                else
                    System.out.println("Not a valid option");
            }
        }
    }
    public void usePickAxe() {
        if (player.getObjectCoin() < playerLot.getTools().get(3).getCostUsage())
            System.out.println("You lack coins to use this tool");
        else if (playerLot.getRocks() == 0)
            System.out.println("No need to use this tool anymore, all rocks in farm lot have been cleared");
        else {
            playerLot.printLot();

            String pickRowLot = "\nPick a row in your lot (1 - 10):\n";
            String pickColLot = "\nPick a column in your lot (1 - 5):\n";
            int rowOptions = 10;
            int colOptions = 5;
            int xCoord = playerChoice(pickRowLot, rowOptions);
            int yCoord = playerChoice(pickColLot, colOptions);

            //If tile chosen tile is valid and contains rock
            if (playerLot.getTools().get(3).useTool(playerLot.getTileStatus(xCoord - 1, yCoord - 1))){

                playerLot.setTileStatus(xCoord - 1, yCoord - 1, 0);
                player.addxp(15);
                player.decreaseObjectCoin(50);

                System.out.printf("Successfully removed rock from tile at (%d)(%d)\n", xCoord, yCoord);
                System.out.println("Gained: " +playerLot.getTools().get(3).getXpYield() + "xp" );
            }
            else  //runs if tile chosen does not have rock
                System.out.println("Tile does not contain rock, try again");
        }
    }
    public void useShovel() {

        if (player.getObjectCoin() < playerLot.getTools().get(4).getCostUsage())
            System.out.println("You lack coins to use this tool");
        else {
            playerLot.printLot();
            boolean isUsingShovel = true;
            do {
                String pickRowLot = "\nPick a row in your lot (1 - 10):\n";
                String pickColLot = "\nPick a column in your lot (1 - 5):\n";
                int rowOptions = 10;
                int colOptions = 5;
                int xCoord = playerChoice(pickRowLot, rowOptions);
                int yCoord = playerChoice(pickColLot, colOptions);

                //If tile chosen tile contains withered plant
                if (playerLot.getTools().get(4).useTool(playerLot.getTileStatus(xCoord - 1, yCoord - 1))){

                    //Get the index of the withered plant in the ArrayList of Planted seeds
                    int index = playerLot.getWitheredPlantIndex(playerLot.getPlantedSeeds(), xCoord - 1, yCoord - 1);

                    playerLot.getPlantedSeeds().remove(index);
                    playerLot.setTileStatus(xCoord - 1, yCoord - 1, 0);
                    player.addxp(playerLot.getTools().get(4).getXpYield());
                    player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());

                    System.out.printf("Successfully removed withered plant from tile at (%d)(%d)\n", xCoord, yCoord);
                    System.out.println("Gained: " +playerLot.getTools().get(4).getXpYield() + "xp" );
                    isUsingShovel = false;
                }
                else if (playerLot.getTileStatus(xCoord - 1, yCoord - 1).equals("Unplowed") ||
                        playerLot.getTileStatus(xCoord - 1, yCoord - 1).equals("Rock") ) {

                    System.out.println("You used the shovel, but nothing happened");
                    System.out.printf("This action costed you %d coins\n",playerLot.getTools().get(4).getCostUsage() );
                    player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                    isUsingShovel = false;

                } //Using shovel on a tile with planted seed
                else if (playerLot.getTileStatus(xCoord - 1, yCoord - 1).equals("Planted")){

                    int index = playerLot.getPlantedSeedIndex(playerLot.getPlantedSeeds(), xCoord - 1, yCoord - 1);

                    Seeds planted = playerLot.getPlantedSeeds().get(index);

                    System.out.printf("This tile contains %s, yet to be harvested\n", planted.getSeedName());
                    System.out.println("Removed planted seed?");
                    int option = playerChoice("1. yes\n2. no", 2);

                    switch (option) {
                        case 1: {
                            System.out.println("Successfully removed " + planted.getSeedName());
                            playerLot.getPlantedSeeds().remove(index);
                            playerLot.setTileStatus(xCoord, yCoord, 0);
                            System.out.printf("Tile at (%d,%d) is now unplowed\n", xCoord, yCoord);
                            player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                            player.addxp(playerLot.getTools().get(4).getXpYield());
                            System.out.println("Gained: " + playerLot.getTools().get(4).getXpYield() + "xp");
                            isUsingShovel = false;
                        }
                        break;
                        case 2:
                        default:
                            System.out.println("Understood...");
                            isUsingShovel = false;
                    }
                }
                else
                    System.out.println("Cannot use shovel on plowed tile, try again");

            }while(isUsingShovel);
        }
    }

    public void plantSeeds() {
        if (playerLot.getSeeds().size() == 0)
            System.out.println("You don't currently own seeds! Buy at the store");
        else {
            System.out.println("Pick a seed to plant");
            int seedPlantOption = playerChoice(playerLot.getSeedsString("0. Exit\n",
                            playerLot.getSeeds()), playerLot.getSeeds().size());
            try {
                //Try checks if option is valid
                Seeds seed = playerLot.getSeeds().get(seedPlantOption - 1);

                playerLot.printLot();
                boolean isPlanting = true;
                do {
                    //If user does not have any plowed tiles
                    if (playerLot.getPlowedTiles() == 0) {
                        System.out.println("You currently don't have any plowed tiles, use plow tool!\n");
                        isPlanting = false;
                    }
                    else {
                        String pickRowLot = "\nPick a row in your lot (1 - 10):\n";
                        String pickColLot = "\nPick a column in your lot (1 - 5):\n";
                        int rowOptions = 10;
                        int colOptions = 5;
                        int xCoord = playerChoice(pickRowLot, rowOptions);
                        int yCoord = playerChoice(pickColLot, colOptions);

                        //runs when chosen seed is type Fruit Tree
                        if (seed.getSeedType().equals("Fruit Tree")) {
                            if (seed.canPlantTree(xCoord - 1, yCoord - 1, playerLot.getLotTiles())) {
                                //Set tile status at (x,y) as plowed and planted
                                playerLot.setTileStatus(xCoord- 1, yCoord - 1, 2);
                                //Update selected seed's status and (x,y) coordinates
                                playerLot.getSeeds().get(seedPlantOption - 1).setSeedStatus(1);
                                playerLot.getSeeds().get(seedPlantOption - 1).setxCoord(xCoord - 1);
                                playerLot.getSeeds().get(seedPlantOption - 1).setyCoord(yCoord - 1);
                                //Add selected seed to list of Planted seeds
                                playerLot.getPlantedSeeds().add(playerLot.getSeeds().get(seedPlantOption - 1));

                                System.out.printf("Successfully planted %s in tile at (%d)(%d)\n",
                                        playerLot.getSeeds().get(seedPlantOption - 1).getSeedName(), xCoord, yCoord);

                                //Finally remove selected seed from inventory
                                playerLot.getSeeds().remove(seedPlantOption - 1);
                                isPlanting = false;
                            }
                            else {
                                System.out.println("Cannot plant on chosen tile, try again");
                                System.out.println("Make sure plowed tile is not near the edge to plant Fruit Tree");
                                isPlanting = false;
                            }

                        }//Runs when seed type is not Fruit Tree
                        else {
                            //If tile chosen tile is plowed and empty
                            if (playerLot.getTileStatus(xCoord - 1, yCoord - 1).equals("Plowed")){

                                //Set tile status at (x,y) as plowed and planted
                                playerLot.setTileStatus(xCoord- 1, yCoord - 1, 2);
                                //Update selected seed's status and (x,y) coordinates
                                playerLot.getSeeds().get(seedPlantOption - 1).setSeedStatus(1);
                                playerLot.getSeeds().get(seedPlantOption - 1).setxCoord(xCoord - 1);
                                playerLot.getSeeds().get(seedPlantOption - 1).setyCoord(yCoord - 1);
                                //Add selected seed to list of Planted seeds
                                playerLot.getPlantedSeeds().add(playerLot.getSeeds().get(seedPlantOption - 1));

                                System.out.printf("Successfully planted %s in tile at (%d)(%d)\n",
                                        playerLot.getSeeds().get(seedPlantOption - 1).getSeedName(), xCoord, yCoord);

                                //Finally remove selected seed from inventory
                                playerLot.getSeeds().remove(seedPlantOption - 1);
                                isPlanting = false;
                            }
                            else { //runs if tile chosen is not a plowed and empty tile
                                System.out.println("Cannot plant on chosen tile, try again");
                            }
                        }
                    }
                }while (isPlanting);

            } catch (Exception e) {
                    System.out.println("Not a valid choice!");
            }
        }
    }

    public void harvestSeeds() {

        if (playerLot.getPlantedSeeds().size() == 0)
            System.out.println("No planted seeds, plant seeds first and take care of them");
        else {

            int harvest = 0;
            for (Seeds seed : playerLot.getPlantedSeeds())
                if (seed.canHarvestseed())
                    harvest++;

            if (harvest == 0)
                System.out.println("No planted seeds are harvestable, come back later");
            else {
                System.out.println("There are " + harvest + " harvestable seeds");
                int option = playerChoice("Harvest all?\n1. yes\n2. no", 2);

                if (option == 1) {

                    int index = 0;
                    //Store all harvestable seeds in Arraylist of harvestable seeds and remove from plantedSeeds Array
                    //Create a temporary arraylist to be able to change plantedseeds arraylist
                    for (Seeds seed : playerLot.getPlantedSeeds()){
                        if (seed.canHarvestseed())
                            playerLot.getHarvestSeeds().add(playerLot.getPlantedSeeds().get(index));
                        index++;
                    }
                    playerLot.getPlantedSeeds().removeIf(Seeds::canHarvestseed);

                    //Print information of produce product of each harvested seed
                    for (Seeds seed : playerLot.getHarvestSeeds()){

                        seed.harvestInfo(player.getFarmerType());
                        //Update player xp and coin amount
                        player.addxp(seed.getXpYield());
                        player.addObjectCoin(seed.finalHarvestPrice(player.getFarmerType(), seed.getSeedType()));
                        System.out.println("Gained: "+ seed.getXpYield() + "xp");
                        System.out.println("Current coin amount: " + player.getObjectCoin());
                        System.out.println("");
                        //Finally remove harvested plant from array list and update tile to unplowed state
                        playerLot.setTileStatus(seed.getxCoord(), seed.getyCoord(), 0);
                        }
                        playerLot.getHarvestSeeds().clear();
                }
                else
                    System.out.println("");
            }

        }
    }

    public void viewFarmerStatus() {
        System.out.println("Farmer Name: " + player.getName());
        System.out.println("Your current status is: " + player.getFarmerType());
        System.out.println("Farmer Lvl: " + player.getLvl());
        System.out.println("Current XP: " + player.getXp());
        System.out.println("You currently have: " + player.getObjectCoin() + " coins");
        System.out.println("Number of days passed: " + player.getDaysPassed() + "\n");
    }
    public void nextDay() {

        System.out.println("Advancing to the next day...\n");

        //Grow all currently planted seeds
        if (playerLot.getPlantedSeeds().size() > 0)
            for (int i = 0; i <playerLot.getPlantedSeeds().size(); i++) {
                playerLot.getPlantedSeeds().get(i).grow();
                playerLot.getPlantedSeeds().get(i).checkIfWither();
            }
        //Counts how many days have passed
        player.passDay();
        //Levels up the player whenever possible
        player.lvlUp();

        int witherCount = 0;
        //Check for any planted seeds that withered and updates tile if so
        for (Seeds seed: playerLot.getPlantedSeeds()) {
            if (seed.getSeedWithered() == 1) {
                playerLot.setTileStatus(seed.getxCoord(), seed.getyCoord(), 3);
                witherCount++;
            }
        }
        int harvestCount = 0;
        //Checks for any planted seeds that are ready to be harvested
        for (Seeds seed : playerLot.getPlantedSeeds())
            if (seed.canHarvestseed())
                harvestCount++;

        if (witherCount > 0)
            System.out.println("Uh oh! " + witherCount + " of your seeds withered!");
        if (harvestCount > 0) {
            System.out.println("\n Good morning! There are " + harvestCount + " seeds ready to be harvested!");
        }
    }
    public void newFarmerStatus() {
        String optionsString = "Please select what action you will do:\n"
                + "1. View Farmer status benefits\n"
                + "2. Register (Registered Farmer)\n"
                + "3. Register (Distinguished Farmer)\n"
                + "4. Register (Legendary Farmer)\n"
                + "5. Exit\n";
        int numOptions = 5;

        int option = playerChoice(optionsString, numOptions);

        switch (option) {
            case 1: player.benefitDescriptions(); break;
            case 2:
                if(player.getFarmerType().equals("Farmer") && player.getObjectCoin() >= 200 &&
                    player.getLvl() >= 5){
                    player.setFarmerType(option - 1);
                    System.out.println("Congratulations! You are now a " + player.getFarmerType() + "!\n");
                    player.decreaseObjectCoin(200);
                }

                else {
                    System.out.println("You are not qualified!");
                    if (!player.getFarmerType().equals("Farmer"))
                        System.out.println("Your status is not Farmer");
                    if (player.getObjectCoin() < 200)
                        System.out.println("You lack coins");
                    if (player.getLvl() < 5)
                        System.out.println("You do not meet the level requirement\n");
                }
                break;
            case 3:
                if(player.getFarmerType().equals("Registered Farmer") && player.getObjectCoin() >= 300 &&
                        player.getLvl() >= 10) {
                    player.setFarmerType(option - 1);
                    System.out.println("Congratulations! You are now a " + player.getFarmerType() + "!\n");
                    player.decreaseObjectCoin(300);
                }

                else {
                    System.out.println("You are not qualified!");
                    if (!player.getFarmerType().equals("Registered Farmer"))
                        System.out.println("Your status is not Registered Farmer");
                    if (player.getObjectCoin() < 300)
                        System.out.println("You lack coins");
                    if (player.getLvl() < 10)
                        System.out.println("You do not meet the level requirement\n");
                }
                break;
            case 4:
                if(player.getFarmerType().equals("Distinguished Farmer") && player.getObjectCoin() >= 400 &&
                        player.getLvl() >= 15) {
                    player.setFarmerType(option - 1);
                    System.out.println("Congratulations! You are now a " + player.getFarmerType() + "!\n");
                    player.decreaseObjectCoin(400);
                }

                else {
                    System.out.println("You are not qualified!");
                    if (!player.getFarmerType().equals("Distinguished Farmer"))
                        System.out.println("Your status is not Distinguished Farmer");
                    if (player.getObjectCoin() < 400)
                        System.out.println("You lack coins");
                    if (player.getLvl() < 15)
                        System.out.println("You do not meet the level requirement\n");
                }
                break;
        }
        if (option !=5)
            newFarmerStatus();
    }

    public void checkInventory() {
        System.out.println("Seeds currently owned: " + playerLot.getSeeds().size());

        if (playerLot.getSeeds().size() > 0)
            for (Seeds seeds : playerLot.getSeeds())
                System.out.println(seeds.getSeedName() + "| type: " + seeds.getSeedType());
        else
            System.out.println("You currently don't own any seeds");
    }

    public boolean checkFailCondition() {
        return (playerLot.getSeeds().size() == 0 && playerLot.getPlantedSeeds().size() == 0 &&
                player.getObjectCoin() == 0) && playerLot.lotWithered();
    }

    public void gameOver() {
        System.out.println("Farmer " + player.getName() + " tried his best to maintain the farm");
        System.out.println("But alas, it all came crashing down");
        System.out.println("Thanks for playing!");
    }
    public static void main(String[] args) {

        MyFarmCLI game = new MyFarmCLI();

        game.initGame();
        game.startGame();
        game.gameOver();

    }
}

