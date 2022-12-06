package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Seeds;
import main.Tools;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is the controller for the tend farm scene.
 * It handles all cases where the user tends to the farm.
 */
public class TendFarmController extends ContractController {

    /** Default constructor for TendFarmController class.
     * Not used as a different method is used to initialize the data for the scene. */
    public TendFarmController(){}

    /**The grid pane region that contains the farm tiles or farm lot. */
    @FXML
    private GridPane gridPaneLot;

    /**The text node that displays the farm name. */
    @FXML
    private Text farmName;

    /**The text node that displays the number of coins the player currently has. */
    @FXML
    private Text objectCoin;

    /**The text node that displays the number of planted seeds. */
    @FXML
    private Text plantedSeeds;

    /**The text node that displays the number of seeds the farmer has. */
    @FXML
    private Text witheredSeeds;

    /**The list view that displays the tools the farmer has. */
    @FXML
    private ListView<String> toolList;

    /**The array of button nodes that represent the tiles on the farm lot. */
    private Button[][] buttonLot;


    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method gets the necessary data for Farmer, Farmlot, and button array
     * from the previous screen or the main screen
     * @param player the player object
     * @param playerLot the player lot object
     * @param button the button node representing the farm tile
     */
    @Override
    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {
        this.player = player;
        this.playerLot = playerLot;
        this.buttonLot = button;

        //Adds or updates the scene text nodes
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
        this.witheredSeeds.setText("Seeds withered: " + playerLot.getNumberOfWitheredPlants());
        this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

        //Adds a custom event handler on each button in gridPaneLot (able to be clicked and use tools on it)
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {
                //Adds an innate coordinate for button nodes
                this.buttonLot[i][j].setUserData(new Coord(i, j));
                //adds an action event on button, used when tool is selected
                this.buttonLot[i][j].setOnAction(event -> {
                    Button b = (Button) event.getSource();
                    useTool(b);
                });
                gridPaneLot.add(this.buttonLot[i][j], j, i);
            }
        }
        //Adds tools to the toolList
        toolList.getItems().addAll("Plow", "Watering Can", "Fertilizer", "Pickaxe", "Shovel", "Inspect Planted Seed");
        toolList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //change listview font size
        toolList.setStyle("-fx-font-size: 16px;");
    }

    /**
     * This method lets the user use the tools in their inventory to tend to the farm
     * @param button the button node representing the farm tile
     */
    public void useTool(Button button) {
        //Get the tool to be used from the toolList
        String tool = toolList.getSelectionModel().getSelectedItem();

        //get the coordinate of the button pressed
        Coord coord = (Coord) button.getUserData();
        int x = coord.i();
        int y = coord.j();

        //If the tool is not selected, display an error message
        if (tool == null) {
            showMsgInfo("Error",
                    "Error",
                    "No tool selected",
                    "Please select a tool from the list");
        } else {
            if (tool.equals("Inspect Planted Seed")) {
                //If tile does not have a planted seed, show error
                if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                    showMsgInfo("Error",
                            "Error",
                            "No seed planted",
                            "There is no seed planted on this tile");
                } else {
                    for (Seeds seed : playerLot.getPlantedSeeds()) {
                        if (seed.getxCoord() == x && seed.getyCoord() == y) {
                            showMsgInfo("Information",
                                    "Seed Information",
                                    null,
                                    seed.getPlantedSeedInfo());
                        }
                    }
                }
            } else { //After all check condition is done, start using the tool and do something depending on the tool
                switch (tool) {
                    case "Plow" -> {
                        if (playerLot.getTileStatus(x, y).equals("Plowed")) {
                            showMsgInfo("Error",
                                    "Error",
                                    "Tile already plowed",
                                    "This tile has already been plowed!");
                        } else if (!playerLot.getTileStatus(x, y).equals("Unplowed")) {
                            showMsgInfo("Error",
                                    "Error",
                                    "Invalid Tile",
                                    "This tile is not an Unplowed tile!");
                        } else {
                            //update text of button
                            button.setText("Plowed");
                            //update button style from tile status
                            setButtonStyle(button);
                            //Show info to user of what happened
                            showMsgInfo("Information",
                                    "Plowed",
                                    null,
                                    "You have plowed the tile at (" + x + ", " + y + ")"
                                            + "\ngained " + playerLot.getTools().get(0).getXpYield() + " xp");

                            //update the lot tooltip
                            setTooltip(button, "Plowed");
                            //update player xp
                            player.addXP(playerLot.getTools().get(0).getXpYield());
                            //Show information when player gets enough xp to level up
                            if (player.canLvlUp()) {
                                showMsgInfo("Information",
                                        "Level up",
                                        "You have leveled up!",
                                        "You are now level " + player.getLvl() + "!");
                            }
                            //update lot tile status in array
                            playerLot.setTileStatus(x, y, 1);
                        }
                    }
                    case "Watering Can" -> {
                        //If tile does not have a planted seed, show error
                        if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                            showMsgInfo("Error",
                                    "Error",
                                    "No seed planted",
                                    "There is no seed planted on this tile");
                        } else {
                            int index = 0;
                            for (Seeds seed : playerLot.getPlantedSeeds()) {
                                //Find the seed coordinate
                                if (seed.getxCoord() == x && seed.getyCoord() == y) {
                                    //Check if it can be watered, if not, show error
                                    if (seed.getWaterCount() == seed.getWaterNeeds() &&
                                            seed.getBonusWater() == seed.getBonusWC()) {
                                            showMsgInfo("Error",
                                                "Error",
                                                "Seed water limit",
                                                "This seed cannot be watered anymore");
                                    } else {
                                        //If it can be watered, water it
                                        playerLot.getPlantedSeeds().get(index).waterSeed();
                                        //add xp from watering action
                                        player.addXP(playerLot.getTools().get(1).getXpYield());

                                        //Show information of action done
                                        showMsgInfo("Information",
                                                "Watering Can",
                                                "You have watered " +
                                                        playerLot.getPlantedSeeds().get(index).getSeedName()
                                                        + " at (" + x + ", " + y + ")",
                                                String.format("""
                                                        Needs (%d | %d), bonus (%d | %d)
                                                        gained %.1f xp""", seed.getWaterCount(),
                                                        seed.getWaterNeeds(), seed.getBonusWC(), seed.getBonusWater(),
                                                        playerLot.getTools().get(1).getXpYield()));

                                        //Show information when player gets enough xp to level up
                                        if (player.canLvlUp()) {
                                            showMsgInfo("Information",
                                                    "Level up",
                                                    "You have leveled up!",
                                                    "You are now level " + player.getLvl() + "!");
                                        }
                                    }
                                }
                                index++;
                            }
                        }
                    }
                    case "Fertilizer" -> {
                        //If tile does not have a planted seed, show error
                        if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                            showMsgInfo("Error",
                                    "Error",
                                    "No seed planted",
                                    "There is no seed planted on this tile");
                        } else {
                            int index = 0;
                            for (Seeds seed : playerLot.getPlantedSeeds()) {
                                //Find the seed coordinate
                                if (seed.getxCoord() == x && seed.getyCoord() == y) {
                                    //Check if it can be fertilized, if not, show error
                                    if (seed.getFertilizerNeeds() == seed.getFertilizerCount() &&
                                            seed.getBonusFertilizer() == seed.getBonusFC()) {
                                        showMsgInfo("Error",
                                                "Error",
                                                "Seed fertilize limit",
                                                "This seed cannot be fertilized anymore");
                                    } else {
                                        //if player does not have enough coins to perform action
                                        if (player.getObjectCoin() < playerLot.getTools().get(2).getCostUsage()) {
                                            showMsgInfo("Error",
                                                    "Error",
                                                    "Not enough coins",
                                                    "You do not have enough coins to fertilize this seed");
                                        } else {
                                            //If it can be fertilized, fertilize it
                                            playerLot.getPlantedSeeds().get(index).fertilizeSeed();
                                            //add xp from fertilizing action
                                            player.addXP(playerLot.getTools().get(2).getXpYield());
                                            //subtract coins from player
                                            player.decreaseObjectCoin(playerLot.getTools().get(2).getCostUsage());
                                            //update scene text coin
                                            objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                                            //Show information of fertilize action
                                            showMsgInfo("Information",
                                                    "Fertilizer",
                                                    "You have fertilized " +
                                                            playerLot.getPlantedSeeds().get(index).getSeedName()
                                                            + " at (" + x + ", " + y + ")",
                                                    String.format("""
                                                            Needs (%d | %d), bonus (%d | %d)
                                                            Gained %.1f xp
                                                            Lost %d coins""", seed.getFertilizerCount(),
                                                            seed.getFertilizerNeeds(), seed.getBonusFC(),
                                                            seed.getBonusFertilizer(),
                                                            playerLot.getTools().get(2).getXpYield(),
                                                            playerLot.getTools().get(2).getCostUsage()));

                                            //Show information when player gets enough xp to level up
                                            if (player.canLvlUp()) {
                                                showMsgInfo("Information",
                                                        "Level up",
                                                        "You have leveled up!",
                                                        "You are now level " + player.getLvl() + "!");
                                            }
                                        }
                                    }
                                }
                                index++;
                            }
                        }
                    }
                    case "Pickaxe" -> {
                        //If tile does not contain rock, show error
                        if (!playerLot.getTileStatus(x, y).equals("Rock")) {
                            showMsgInfo("Error",
                                    "Error",
                                    "No rock found",
                                    "There is no rock at (" + x + ", " + y + ")");
                        } //If player does not have enough coins, show error
                        else if (player.getObjectCoin() < playerLot.getTools().get(3).getCostUsage()) {
                            showMsgInfo("Error",
                                    "Error",
                                    "Not enough coins",
                                    "You do not have enough coins to use this tool");
                        } else {
                            //If tile contains rock, remove it and update button and lot state
                            button.setText("Unplowed");
                            setButtonStyle(button);
                            playerLot.setTileStatus(x, y, 0);

                            //Show information of pickaxe action
                            showMsgInfo("Information",
                                    "Pickaxe",
                                    "You have removed the rock at tile (" + x + ", " + y + ")",
                                    "\nGained: " + playerLot.getTools().get(3).getXpYield() + " xp" +
                                            "\nLost: " + playerLot.getTools().get(3).getCostUsage() + " coins");
                            //update the lot tooltip
                            setTooltip(button, "Unplowed");
                            //update player xp
                            player.addXP(playerLot.getTools().get(3).getXpYield());
                            //Show information when player gets enough xp to level up
                            if (player.canLvlUp()) {
                                showMsgInfo("Information",
                                        "Level up",
                                        "You have leveled up!",
                                        "You are now level " + player.getLvl() + "!");
                            }
                            //update player coins and scene text
                            player.decreaseObjectCoin(playerLot.getTools().get(3).getCostUsage());
                            this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                        }
                    }
                    case "Shovel" -> { //shovel tool has switch case for each tile status
                        switch (playerLot.getTileStatus(x, y)) {
                            case "Withered" -> {
                                //Find the seed coordinate that is withered
                                int witherIndex = playerLot.getWitheredPlantIndex(playerLot.getPlantedSeeds(), x, y);
                                //Remove from planted seeds array
                                playerLot.getPlantedSeeds().remove(witherIndex);
                                //Update scene text about planted seeds
                                this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());
                                this.witheredSeeds.setText("Seeds withered: " + playerLot.getNumberOfWitheredPlants());
                                //Update tile status
                                playerLot.setTileStatus(x, y, 0);

                                //Update button text and style
                                button = new Button("Unplowed");
                                button.setPrefSize(100, 100);
                                button.setText("Unplowed");
                                setTooltip(button, "Unplowed");
                                setButtonStyle(button);
                                //Reinitialize button with setAction and Coord class
                                button.setUserData(new Coord(x, y));
                                //adds an action event on button, used when tool is selected
                                button.setOnAction(event -> {
                                    Button b = (Button) event.getSource();
                                    useTool(b);
                                });
                                setButtonStyle(button);
                                this.buttonLot[x][y] = button;
                                gridPaneLot.add(button, y, x);

                                //Update player xp
                                player.addXP(playerLot.getTools().get(4).getXpYield());
                                //Update player coins and scene text
                                player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                                //Finally show information of removal
                                showMsgInfo("Information",
                                        "Shovel",
                                        "Withered plant removed",
                                        "You have removed the withered plant at tile (" + x + ", " + y + ")"
                                                + "\ngained " + playerLot.getTools().get(4).getXpYield() + " xp"
                                                + "\nlost " + playerLot.getTools().get(4).getCostUsage() + " coins");
                                //Show information when player gets enough xp to level up
                                if (player.canLvlUp()) {
                                    showMsgInfo("Information",
                                            "Level up",
                                            "You have leveled up!",
                                            "You are now level " + player.getLvl() + "!");
                                }
                            }
                            case "Planted" -> {
                                //Get the index of chosen seed that is currently planted
                                int plantIndex = playerLot.getPlantedSeedIndex(playerLot.getPlantedSeeds(), x, y);
                                Seeds plantedSeed = playerLot.getPlantedSeeds().get(plantIndex);

                                //If seed is not ready to harvest, show alert
                                if (!plantedSeed.canHarvestSeed()) {
                                    //Show confirmation of removal
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                    //Set alert image icon
                                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                                    stage.getIcons().add(new Image("sun.png"));
                                    alert.setTitle("Shovel");
                                    alert.setHeaderText(plantedSeed.getSeedName() + " is not ready to be harvested");
                                    alert.setContentText("This seed is not ready to harvest yet\n" +
                                            "Are you sure you want to remove it?");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        //Remove from planted seeds array
                                        playerLot.getPlantedSeeds().remove(plantIndex);
                                        this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

                                        //Update tile status
                                        playerLot.setTileStatus(x, y, 0);

                                        //Update button text and style
                                        button = new Button("Unplowed");
                                        button.setPrefSize(100, 100);
                                        button.setText("Unplowed");
                                        setTooltip(button, "Unplowed");
                                        setButtonStyle(button);
                                        //Reinitialize button with setAction and Coord class
                                        button.setUserData(new Coord(x, y));
                                        //adds an action event on button, used when tool is selected
                                        button.setOnAction(event -> {
                                            Button b = (Button) event.getSource();
                                            useTool(b);
                                        });
                                        this.buttonLot[x][y] = button;
                                        gridPaneLot.add(button, y, x);

                                        //Update player xp
                                        player.addXP(playerLot.getTools().get(4).getXpYield());
                                        //Update player coins
                                        player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                        this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                                        //Finally show information of removal
                                        showMsgInfo("Information",
                                                "Shovel",
                                                "Seed removed",
                                                "You have removed the seed at tile (" + x + ", " + y + ")"
                                                        + "\nGained: " + playerLot.getTools().get(4).getXpYield() + " xp"
                                                        + "\nLost: " + playerLot.getTools().get(4).getCostUsage() + " coins");

                                        //Show information when player gets enough xp to level up
                                        if (player.canLvlUp()) {
                                            showMsgInfo("Information",
                                                    "Level up",
                                                    "You have leveled up!",
                                                    "You are now level " + player.getLvl() + "!");
                                        }
                                    }
                                } else {
                                    showMsgInfo("Error",
                                            "Cannot remove",
                                            "Seed cannot be removed",
                                            "This seed is ready to be harvested, you cannot remove it");
                                }
                            }
                            case "Unplowed" -> showMsgInfo("Error",
                                        "Error",
                                        null,
                                        "Cannot use shovel on Unplowed tile");
                            default -> {
                                //Special case for shovel tool, if tile is used on a plowed or a rock tile, show alert
                                // and for some reason loses 7 coins... not sure why
                                player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                                showMsgInfo("Error",
                                        "Error",
                                        "Cannot use shovel on this tile",
                                        "This action unfortunately made you lose coins" +
                                                "\nLost " + playerLot.getTools().get(4).getCostUsage() + " coins");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method shows the description of the tool selected via pop-up alert window
     */
    public void showToolDescription() {
        //Get the tool selected from tool list
        String tool = toolList.getSelectionModel().getSelectedItem();
        if (tool == null) {
            showMsgInfo("Error",
                    "Error",
                    "No tool selected",
                    "Please select a tool to use");

        } else if (tool.contains("Inspect Planted Seed")) {
            showMsgInfo("Information",
                    "Inspect Planted Seed",
                    "The Inspection Tool",
                    """
                            The Inspection Tool allows you to inspect the planted seed
                            Shows relevant information about the seed
                            like the days left to harvest and number of times it was fertilized and watered""");
        } else {
            for (Tools tools : playerLot.getTools()) {
                if (tools.getToolName().equals(tool)) {
                    showMsgInfo("Information",
                            tools.getToolName(),
                            "The " + tools.getToolName(),
                            """
                                    %s
                                    Cost usage: $%d
                                    XP yield: %.1f
                                    """.formatted(tools.getToolDescription(), tools.getCostUsage(), tools.getXpYield()));
                }
            }
        }
    }

    /**
     * This method switches the scene to the main scene and passing data to MainClassController
     * @param event ActionEvent
     * @throws IOException IOException
     */
    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setFarm(player, playerLot, buttonLot);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

}
