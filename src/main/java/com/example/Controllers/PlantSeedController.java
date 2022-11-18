package com.example.Controllers;

import Farmer.FarmLot;
import Farmer.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Seeds;

import java.io.IOException;

public class PlantSeedController extends ContractController {

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
    ListView<String> seedList;

    private Farmer player;
    private FarmLot playerLot;
    private Button[][] button;


    private void initialize() {
    }

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
        //Adds the seeds to the list
        for (Seeds seed : playerLot.getSeeds()) {
            seedList.getItems().add(seed.getSeedName());
        }
        seedList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //change listview font size
        seedList.setStyle("-fx-font-size: 16px;");

        //Sets button with a custom event handler
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {
                gridPaneLot.add(this.button[i][j], j, i);
                this.button[i][j].setUserData(new Coord(i, j));
                //adds an action event on button, used when tool is selected
                this.button[i][j].setOnAction(event -> {
                    Button b = (Button) event.getSource();
                    plantSeed(b);
                });
            }
        }
    }

    public void plantSeed(Button button) {
        int listIndex = seedList.getSelectionModel().getSelectedIndex();
        String seedName = seedList.getSelectionModel().getSelectedItem();
        if (listIndex == -1) {
            //if no seed is selected, do nothing
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to plant");
            alert.showAndWait();
        } else {
            //Get button x and y coordinates to be used to store this data to planted seed
            Coord coord = (Coord) button.getUserData();
            int x = coord.getI();
            int y = coord.getJ();
            //If tile is not plowed
            if (!playerLot.getTileStatus(x, y).equals("Plowed")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Tile not plowed");
                alert.setContentText("Please plow the tile before planting");
                alert.showAndWait();
            } else {
                //Special condition for seed type Fruit Tree
                if (seedName.equals("Apple") || seedName.equals("Mango")) {
                    Seeds fruitTree = new Seeds(playerLot.getSeeds().get(listIndex), player.getFarmerType());
                    if (!fruitTree.canPlantTree(x, y, playerLot.getLotTiles())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Cannot plant tree");
                        alert.setContentText("Please plant the tree in tiles that are not adjacent to other objects");
                        alert.showAndWait();
                        return;
                    }
                }
                //Update relevant button information
                button.setText("Planted");
                //add grid to button style and change button text style to green
                setButtonStyle(button);


                //Update relevant information about seeds in playerLot
                Seeds seed = new Seeds(playerLot.getSeeds().get(listIndex), player.getFarmerType());
                //Tracks where the seed was planted
                seed.setxCoord(x);
                seed.setyCoord(y);
                //Add seed to planted seeds list
                playerLot.getPlantedSeeds().add(seed);
                //Update tile status
                playerLot.setTileStatus(x, y, 2);
                //Remove seed from seed list
                playerLot.getSeeds().remove(listIndex);
                //Remove seed from listview
                seedList.getItems().remove(listIndex);
                //update number of seeds owned
                seedsOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
                //Update tooltip to show what seed has been planted on the chosen tile
                setTooltip(button, seed.getSeedName());

                //Finally, show alert information about action done
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setWidth(500);
                alert.setHeight(500);
                alert.setTitle("Success");
                alert.setHeaderText("Seed planted");
                alert.setContentText(seed.getSeedName() + " planted successfully at tile (" + x + ", " + y + ")");
                alert.showAndWait();
            }
        }
    }

    public void seedInformation(ActionEvent event) {
        int listIndex = seedList.getSelectionModel().getSelectedIndex();
        Seeds selectedSeed = playerLot.getSeeds().get(listIndex);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setWidth(500);
        alert.setHeight(500);
        alert.setTitle(selectedSeed.getSeedName());
        alert.setHeaderText("Information about " + selectedSeed.getSeedName());
        alert.setContentText(selectedSeed.getSeedInfo());
        alert.showAndWait();
    }


    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setFarm(player, playerLot, button);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}
