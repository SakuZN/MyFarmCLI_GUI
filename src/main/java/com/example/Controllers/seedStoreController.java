package com.example.Controllers;

import Farmer.FarmLot;
import Farmer.Farmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Seeds;
import main.Store;

import java.io.IOException;

public class seedStoreController extends ContractController {

    @FXML
    private TableView<Seeds> seedView;
    @FXML
    private TableColumn<Seeds, String> seedName;
    @FXML
    private TableColumn<Seeds, String> seedType;
    @FXML
    private TableColumn<Seeds, Integer> seedCost;
    @FXML
    private Text objectCoin;
    @FXML
    private Text seedOwned;
    private Farmer player;
    private FarmLot playerLot;

    @FXML
    private void initialize() {
    }

    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;
        objectCoin.setText("Coins: " + player.getObjectCoin());
        seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());

        // Set up Table column for seed name
        seedName.setCellValueFactory(new PropertyValueFactory<>("seedName"));
        // Set up Table column for seed type
        seedType.setCellValueFactory(new PropertyValueFactory<>("seedType"));
        // Set up Table column for seed cost
        seedCost.setCellValueFactory(new PropertyValueFactory<>("seedCost"));

        //Finally setup Table List
        seedView.setItems(getSeeds());

        //change table view font size
        seedView.setStyle("-fx-font-size: 18px;");
        //prevent table view scrolling
        seedView.setFixedCellSize(31.5);
    }

    //Method to get all seeds from the store
    private ObservableList<Seeds> getSeeds () {
        ObservableList<Seeds> seedList = FXCollections.observableArrayList();
        Store seedStore = new Store();
        seedList.addAll(seedStore.getAllForSale());
        return seedList;
    }

    //Buy selected seed from the store
    public void buySeed() {
        Seeds selectedSeed = seedView.getSelectionModel().getSelectedItem();
        //Store farmer type benefit discount
        int farmerTypeBenefit = player.farmerTypeBenefit(player.getFarmerType());
        if (selectedSeed != null) {
            if (player.getObjectCoin() >= selectedSeed.getSeedCost()) {
                player.decreaseObjectCoin(selectedSeed.getSeedCost() - farmerTypeBenefit);
                playerLot.increaseSeed(selectedSeed, player.getFarmerType());
                objectCoin.setText("Coins: " + player.getObjectCoin());
                seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setWidth(500);
                alert.setHeight(500);
                alert.setTitle("Not enough coins");
                alert.setHeaderText(null);
                alert.setContentText("You do not have enough coins to buy " + selectedSeed.getSeedName());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setWidth(500);
            alert.setHeight(500);
            alert.setTitle("No seed selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a seed to buy.");
            alert.showAndWait();
        }
    }
    //Show selected seed information
    public void seedInformation () {
        Seeds selectedSeed = seedView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (selectedSeed != null) {
            alert.setTitle(selectedSeed.getSeedName());
            alert.setHeaderText(null);
            alert.setContentText(selectedSeed.getSeedInfo());
        } else {
            alert.setWidth(500);
            alert.setHeight(500);
            alert.setTitle("No seed selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a seed to view information.");
        }
        alert.showAndWait();
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
