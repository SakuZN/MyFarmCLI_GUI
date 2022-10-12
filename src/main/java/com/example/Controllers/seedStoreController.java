package com.example.Controllers;

import Farmer.Farmer;
import Farmer.FarmLot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import main.Seeds;
import main.Store;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class seedStoreController extends MainClassController {

    @FXML
    ListView<String> listRootCrop;
    @FXML
    ListView<String> listFlower;
    @FXML
    ListView<String> listFruitTree;
    @FXML
    Button buyButton;
    @FXML
    Text objectCoin;
    @FXML
    Text seedOwned;
    @FXML
    private void initialize() {}

    private Farmer player;
    private FarmLot playerLot;

    Store seedStore;

    public void setFarm (Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;
        objectCoin.setText("Coins: " + player.getObjectCoin());
        seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
        // Instantiate store
        seedStore = new Store();
        listRootCrop.getItems().addAll("Turnip", "Carrot", "Potato");
        listFlower.getItems().addAll("Rose", "Turnips", "Sunflower");
        listFruitTree.getItems().addAll("Mango", "Apple");

    }
    public void buyRootCrop(ActionEvent e) {
        //Store farmer type benefit discount
        int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());

        //Check if selected seed is of type root crop
        if (listRootCrop.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listRootCrop.getSelectionModel().getSelectedItem();
            for (Seeds seed: seedStore.getRootCropSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    //Check if player has enough coins
                    if (player.getObjectCoin() >= seed.getSeedCost() - farmerTypeBenefit) {
                        player.decreaseObjectCoin(seed.getSeedCost() - farmerTypeBenefit);
                        playerLot.increaseSeed(seed, player.getFarmerType());
                        objectCoin.setText("Coins: " + player.getObjectCoin());
                        seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Not enough coins");
                        alert.setContentText("You do not have enough coins to buy " + selectedSeed);
                        alert.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to buy");
            alert.showAndWait();
        }

    }
    public void showRootCropInfo(ActionEvent e) {

        if (listRootCrop.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listRootCrop.getSelectionModel().getSelectedItem();
            for (Seeds seed : seedStore.getRootCropSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Seed Information");
                    alert.setHeaderText("Seed Name: " + seed.getSeedName());
                    alert.setContentText(seed.getSeedInfo());
                    alert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to view information");
            alert.showAndWait();
        }
    }

    public void buyFlower (ActionEvent e) {
        //Store farmer type benefit discount
        int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());

        //Check if selected seed is of type root crop
        if (listFlower.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listFlower.getSelectionModel().getSelectedItem();
            for (Seeds seed: seedStore.getFlowerSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    //Check if player has enough coins
                    if (player.getObjectCoin() >= seed.getSeedCost() - farmerTypeBenefit) {
                        player.decreaseObjectCoin(seed.getSeedCost() - farmerTypeBenefit);
                        playerLot.increaseSeed(seed, player.getFarmerType());

                        objectCoin.setText("Coins: " + player.getObjectCoin());
                        seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Not enough coins");
                        alert.setContentText("You do not have enough coins to buy " + selectedSeed);
                        alert.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to buy");
            alert.showAndWait();
        }
    }
    public void showFlowerInfo(ActionEvent e) {
        if (listFlower.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listFlower.getSelectionModel().getSelectedItem();
            for (Seeds seed : seedStore.getFlowerSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Seed Information");
                    alert.setHeaderText("Seed Name: " + seed.getSeedName());
                    alert.setContentText(seed.getSeedInfo());
                    alert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to view information");
            alert.showAndWait();
        }
    }

    public void buyFruitTree(ActionEvent e) {
        //Store farmer type benefit discount
        int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());

        //Check if selected seed is of type Fruit Tree
        if (listFruitTree.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listFruitTree.getSelectionModel().getSelectedItem();
            for (Seeds seed: seedStore.getFruitTreeSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    //Check if player has enough coins
                    if (player.getObjectCoin() >= seed.getSeedCost() - farmerTypeBenefit) {
                        player.decreaseObjectCoin(seed.getSeedCost() - farmerTypeBenefit);
                        playerLot.increaseSeed(seed, player.getFarmerType());
                        objectCoin.setText("Coins: " + player.getObjectCoin());
                        seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Not enough coins");
                        alert.setContentText("You do not have enough coins to buy " + selectedSeed);
                        alert.showAndWait();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to buy");
            alert.showAndWait();
        }
    }
    public void showFruitTreeInfo(ActionEvent e) {
        if (listFruitTree.getSelectionModel().getSelectedItem() != null) {
            String selectedSeed = listFruitTree.getSelectionModel().getSelectedItem();
            for (Seeds seed : seedStore.getFruitTreeSale()) {
                if (seed.getSeedName().equals(selectedSeed)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Seed Information");
                    alert.setHeaderText("Seed Name: " + seed.getSeedName());
                    alert.setContentText(seed.getSeedInfo());
                    alert.showAndWait();
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No seed selected");
            alert.setContentText("Please select a seed to view information");
            alert.showAndWait();
        }
    }

    //Handles the back button
    public void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setFarm(player, playerLot);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }
}
