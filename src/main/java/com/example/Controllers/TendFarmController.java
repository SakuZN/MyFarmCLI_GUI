package com.example.Controllers;

import Farmer.Farmer;
import Farmer.FarmLot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.Seeds;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Tools;
import java.io.IOException;
import java.util.Optional;

public class TendFarmController extends MainClassController {

    @FXML
    GridPane gridPaneLot;
    @FXML
    Text farmName;
    @FXML
    Text objectCoin;
    @FXML
    Text plantedSeeds;
    @FXML
    Text seedsOwned;

    @FXML
    ListView<String> toolList;

    private Farmer player;
    private FarmLot playerLot;
    private Button[][] button;


    private void initialize() {}

    //Initializes the player and playerLot

    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {
        this.player = player;
        this.playerLot = playerLot;
        this.button = button;
        //Adds text to the main screen
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        this.objectCoin.setText("Coins: " + player.getObjectCoin());
        this.seedsOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
        this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {
                gridPaneLot.add(this.button[i][j], j, i);
                this.button[i][j].setUserData(new Coord(i, j));
                //adds an action event on button, used when tool is selected
                this.button[i][j].setOnAction(event -> {
                    Button b = (Button) event.getSource();
                    useTool(b);});
            }
        }
        //Adds tools to the toolList
        toolList.getItems().addAll("Plow", "Watering Can", "Fertilizer", "Pickaxe", "Shovel", "Inspect Planted Seed");
        toolList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //change listview font size
        toolList.setStyle("-fx-font-size: 16px;");
    }


    public void useTool(Button button) {
        String tool = toolList.getSelectionModel().getSelectedItem();

        //get the coordinate of the button pressed
        Coord coord = (Coord) button.getUserData();
        int x = coord.getI();
        int y = coord.getJ();

        Alert alert;
        if (tool == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No tool selected");
            alert.setContentText("Please select a tool from the list");
            alert.showAndWait();
        } else {
            if (tool.equals("Inspect Planted Seed")) {
                //If tile does not have a planted seed, show error
                if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("No seed planted");
                    alert.setContentText("There is no seed planted on this tile");
                    alert.showAndWait();
                } else {
                    for (Seeds seed : playerLot.getPlantedSeeds()) {
                        if (seed.getxCoord() == x && seed.getyCoord() == y) {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setWidth(500);
                            alert.setHeight(500);
                            alert.setTitle("Seed Information");
                            alert.setHeaderText("Seed Information");
                            alert.setContentText(seed.getPlantedSeedInfo());
                            alert.showAndWait();
                        }
                    }
                }
            }
            else {
                switch (tool) {
                    case "Plow":
                        if (playerLot.getTileStatus(x, y).equals("Plowed")) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Tile already plowed");
                            alert.setContentText("This tile has already been plowed");
                            alert.showAndWait();
                        } else if (!playerLot.getTileStatus(x, y).equals("Unplowed")) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Invalid Tile");
                            alert.setContentText("This tile is not an Unplowed tile");
                            alert.showAndWait();
                        }
                        else {
                            //update text of button
                            button.setText("Plowed");
                            setButtonStyle(button);
                            //Show info to user of what happened
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setWidth(500);
                            alert.setHeight(500);
                            alert.setTitle("Plow");
                            alert.setHeaderText("Plowed");
                            alert.setContentText("You have plowed the tile at (" + x + ", " + y + ")"
                                    +"\ngained " + playerLot.getTools().get(0).getXpYield() + " xp");
                            alert.showAndWait();
                            //update the lot tooltip
                            setTooltip(button, "Plowed");
                            //update player xp
                            player.addxp(playerLot.getTools().get(0).getXpYield());
                            //Show information when player gets enough xp to level up
                            if (player.canLvlUp()) {
                                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                alert3.setTitle("Level up");
                                alert3.setHeaderText("You have leveled up!");
                                alert3.setContentText("You are now level " + player.getLvl() + "!");
                                alert3.showAndWait();
                            }
                            //update lot tile status in array
                            playerLot.setTileStatus(x, y, 1);
                        }
                        break;
                    case "Watering Can":
                        //If tile does not have a planted seed, show error
                        if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("No seed planted");
                            alert.setContentText("There is no seed planted on this tile");
                            alert.showAndWait();
                        } else {
                            int index = 0;
                            for (Seeds seed : playerLot.getPlantedSeeds()) {
                                //Find the seed coordinate
                                if (seed.getxCoord() == x && seed.getyCoord() == y) {
                                    //Check if it can be watered
                                    if (seed.getWaterCount() == seed.getWaterNeeds() &&
                                            seed.getBonusWater() == seed.getBonusWC()) {
                                        alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Seed water limit");
                                        alert.setContentText("This seed cannot be watered anymore");
                                        alert.showAndWait();
                                    }
                                    else {
                                        playerLot.getPlantedSeeds().get(index).waterseed();
                                        player.addxp(playerLot.getTools().get(1).getXpYield());
                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setWidth(500);
                                        alert.setHeight(500);
                                        alert.setTitle("Watering Can");
                                        alert.setHeaderText("You have watered "+ playerLot.getPlantedSeeds().get(index).getSeedName()
                                                +" at (" + x + ", " + y + ")");
                                        alert.setContentText(playerLot.getPlantedSeeds().get(index).getPlantedSeedWaterInfo()
                                                +"\ngained " + playerLot.getTools().get(1).getXpYield() + " xp");
                                        alert.showAndWait();
                                        //Show information when player gets enough xp to level up
                                        if (player.canLvlUp()) {
                                            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                            alert3.setTitle("Level up");
                                            alert3.setHeaderText("You have leveled up!");
                                            alert3.setContentText("You are now level " + player.getLvl() + "!");
                                            alert3.showAndWait();
                                        }
                                    }
                                }
                                index++;
                            }
                        }
                        break;
                    case "Fertilizer":
                        //If tile does not have a planted seed, show error
                        if (!playerLot.getTileStatus(x, y).equals("Planted")) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("No seed planted");
                            alert.setContentText("There is no seed planted on this tile");
                            alert.showAndWait();
                        } else {
                            int index = 0;
                            for (Seeds seed : playerLot.getPlantedSeeds()) {
                                //Find the seed coordinate
                                if (seed.getxCoord() == x && seed.getyCoord() == y) {
                                    //Check if it can be fertilized
                                    if (seed.getFertilizerNeeds() == seed.getFertilizerCount() &&
                                            seed.getBonusFertilizer() == seed.getBonusFC()) {
                                        alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Seed fertilize limit");
                                        alert.setContentText("This seed cannot be fertilized anymore");
                                        alert.showAndWait();
                                    }
                                    else {
                                        //if player does not have enough coins to perform action
                                        if (player.getObjectCoin() < playerLot.getTools().get(2).getCostUsage()) {
                                            alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Error");
                                            alert.setHeaderText("Not enough coins");
                                            alert.setContentText("You do not have enough coins to fertilize this seed");
                                            alert.showAndWait();
                                        }
                                        else {
                                            playerLot.getPlantedSeeds().get(index).fertilizeseed();
                                            player.addxp(playerLot.getTools().get(2).getXpYield());
                                            player.decreaseObjectCoin(playerLot.getTools().get(2).getCostUsage());
                                            objectCoin.setText("Coins: " + player.getObjectCoin());
                                            alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setWidth(500);
                                            alert.setHeight(500);
                                            alert.setTitle("Fertilizer");
                                            alert.setHeaderText("You have fertilized "+ playerLot.getPlantedSeeds().get(index).getSeedName()
                                                    +" at (" + x + ", " + y + ")");
                                            alert.setContentText(playerLot.getPlantedSeeds().get(index).getPlantedSeedFertilizedInfo()
                                                    +"\ngained " + playerLot.getTools().get(2).getXpYield() + " xp" +
                                                    "\nLost: " + playerLot.getTools().get(2).getCostUsage() + " coins");
                                            alert.showAndWait();
                                            //Show information when player gets enough xp to level up
                                            if (player.canLvlUp()) {
                                                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                                alert3.setTitle("Level up");
                                                alert3.setHeaderText("You have leveled up!");
                                                alert3.setContentText("You are now level " + player.getLvl() + "!");
                                                alert3.showAndWait();
                                            }
                                        }
                                    }
                                }
                                index++;
                            }
                        }
                        break;
                    case "Pickaxe":
                        //If tile does not contain rock, show error
                        if (!playerLot.getTileStatus(x,y).equals("Rock")) {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("No rock found");
                            alert.setContentText("There is no rock at (" + x + ", " + y + ")");
                            alert.showAndWait();
                        } //If player does not have enough coins, show error
                        else if (player.getObjectCoin() < playerLot.getTools().get(3).getCostUsage()){
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Not enough coins");
                            alert.setContentText("You do not have enough coins to use this tool");
                            alert.showAndWait();
                        }
                        else {
                            button.setText("Unplowed");
                            setButtonStyle(button);
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setWidth(500);
                            alert.setHeight(500);
                            alert.setTitle("Pickaxe");
                            alert.setHeaderText("Rock found");
                            alert.setContentText("You have removed the rock at tile (" + x + ", " + y + ")"
                                    +"\nGained: " + playerLot.getTools().get(3).getXpYield() + " xp"
                                    +"\nLost: " + playerLot.getTools().get(3).getCostUsage() + " coins");
                            alert.showAndWait();
                            //update the lot tooltip
                            setTooltip(button, "Unplowed");
                            //update player xp
                            player.addxp(playerLot.getTools().get(3).getXpYield());
                            //Show information when player gets enough xp to level up
                            if (player.canLvlUp()) {
                                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                alert3.setTitle("Level up");
                                alert3.setHeaderText("You have leveled up!");
                                alert3.setContentText("You are now level " + player.getLvl() + "!");
                                alert3.showAndWait();
                            }
                            //update player coins
                            player.decreaseObjectCoin(playerLot.getTools().get(3).getCostUsage());
                            this.objectCoin.setText("Coins: " + player.getObjectCoin());
                            //update tile status in array
                            playerLot.setTileStatus(x, y, 0);
                        }
                        break;
                    case "Shovel": //shovel tool has switch case for each tile status
                        switch (playerLot.getTileStatus(x,y)) {
                            case "Withered":
                                //Find the seed coordinate that is withered
                                int witherIndex = playerLot.getWitheredPlantIndex(playerLot.getPlantedSeeds(), x, y);
                                //Remove from planted seeds array
                                playerLot.getPlantedSeeds().remove(witherIndex);
                                this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());
                                //Update tile status
                                playerLot.setTileStatus(x, y, 0);

                                //Update button text
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
                                    useTool(b);});
                                setButtonStyle(button);
                                this.button[x][y] = button;
                                gridPaneLot.add(button, y, x);

                                //Update player xp
                                player.addxp(playerLot.getTools().get(4).getXpYield());
                                //Update player coins
                                player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                this.objectCoin.setText("Coins: " + player.getObjectCoin());
                                //Finally show information of removal
                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Shovel");
                                alert.setHeaderText("Withered plant removed");
                                alert.setContentText("You have removed the withered plant at tile (" + x + ", " + y + ")"
                                        +"\ngained " + playerLot.getTools().get(4).getXpYield() + " xp"
                                        +"\nlost " + playerLot.getTools().get(4).getCostUsage() + " coins");
                                alert.showAndWait();
                                //Show information when player gets enough xp to level up
                                if (player.canLvlUp()) {
                                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                    alert3.setTitle("Level up");
                                    alert3.setHeaderText("You have leveled up!");
                                    alert3.setContentText("You are now level " + player.getLvl() + "!");
                                    alert3.showAndWait();
                                }
                                break;
                            case "Planted":
                                int plantIndex = playerLot.getPlantedSeedIndex(playerLot.getPlantedSeeds(), x, y);
                                Seeds plantedSeed = playerLot.getPlantedSeeds().get(plantIndex);
                                //If seed is not ready to harvest, show alert
                                if (!plantedSeed.canHarvestseed()) {
                                    //Show confirmation of removal
                                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Shovel");
                                    alert.setHeaderText(plantedSeed.getSeedName() +" is not ready to be harvested");
                                    alert.setContentText("This seed is not ready to harvest yet\nAre you sure you want to remove it?");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        //Remove from planted seeds array
                                        playerLot.getPlantedSeeds().remove(plantIndex);
                                        this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());
                                        this.
                                        //Update tile status
                                        playerLot.setTileStatus(x, y, 0);

                                        //Update button text
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
                                            useTool(b);});
                                        this.button[x][y] = button;
                                        gridPaneLot.add(button, y, x);

                                        //Update player xp
                                        player.addxp(playerLot.getTools().get(4).getXpYield());
                                        //Update player coins
                                        player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                        this.objectCoin.setText("Coins: " + player.getObjectCoin());

                                        //Finally show information of removal
                                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                        alert2.setTitle("Shovel");
                                        alert2.setHeaderText("Seed removed");
                                        alert2.setContentText("You have removed the seed at tile (" + x + ", " + y + ")"
                                                +"\nGained: " + playerLot.getTools().get(4).getXpYield() + " xp"
                                                +"\nLost: " + playerLot.getTools().get(4).getCostUsage() + " coins");
                                        alert2.showAndWait();
                                        //Show information when player gets enough xp to level up
                                        if (player.canLvlUp()) {
                                            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                                            alert3.setTitle("Level up");
                                            alert3.setHeaderText("You have leveled up!");
                                            alert3.setContentText("You are now level " + player.getLvl() + "!");
                                            alert3.showAndWait();
                                        }
                                    }
                                } else {
                                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                                    alert3.setTitle("Cannot remove");
                                    alert3.setHeaderText("Seed cannot be removed");
                                    alert3.setContentText("This seed is ready to harvest, you cannot remove it");
                                    alert3.showAndWait();
                                }
                                break;
                            case "Unplowed":
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Cannot use shovel on Unplowed tile");
                                alert.setContentText("There is no seed at (" + x + ", " + y + ")");
                                alert.showAndWait();
                                break;
                            default:
                                alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Cannot use shovel on this tile");
                                alert.setContentText("This action unfortunately made you lose coins" +
                                        "\nLost " + playerLot.getTools().get(4).getCostUsage() + " coins");
                                player.decreaseObjectCoin(playerLot.getTools().get(4).getCostUsage());
                                this.objectCoin.setText("Coins: " + player.getObjectCoin());
                                alert.showAndWait();
                                break;
                        }
                        break;
                }
            }
        }
    }
    public void showToolDescription (ActionEvent event) {
        String tool = toolList.getSelectionModel().getSelectedItem();
        if (tool == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No tool selected");
                alert.setContentText("Please select a tool to use");
                alert.showAndWait();

        }
        else if (tool.contains("Inspect Planted Seed")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setWidth(500);
            alert.setHeight(500);
            alert.setTitle("Inspect Planted Seed");
            alert.setHeaderText("The Inspection Tool");
            alert.setContentText("The Inspection Tool allows you to inspect the planted seed" + "\nShows relevant information about the seed"
            + "\nlike the days left to harvest and number of times it was fertilized and watered");
            alert.showAndWait();
        }
        else {
            for (Tools tools : playerLot.getTools()) {
                if (tools.getToolName().equals(tool)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(tools.getToolName());
                    alert.setHeaderText("The " + tools.getToolName());
                    alert.setContentText(tools.toolDescription() + "\n\nCost usage: " + tools.getCostUsage() + " coins"
                            + "\nXP yield: " + tools.getXpYield() + " xp");
                    alert.showAndWait();
                }
            }
        }


    }
    //Handles the back button
    public void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setButtonFarm(player, playerLot, button);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    /*Debugging method meant to print out the coordinate of the button pressed
    private EventHandler<ActionEvent> createTileHandler(int x, int y) {
        return event -> tileHandler(x, y);
    }
    private void tileHandler (int x, int y){
        System.out.println(String.format("Clicked tile at (%d,%d)", x, y));
        }
     */

}
