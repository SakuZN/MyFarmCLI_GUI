package com.example.Controllers;

import Farmer.FarmLot;
import Farmer.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterStatusController extends ContractController {


    @FXML
    Text objectCoin;
    @FXML
    Text currentFarmerStatus;
    @FXML
    Text farmerLvl;

    private Farmer player;
    private FarmLot playerLot;

    private void initialize() {
    }

    @Override
    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;
        objectCoin.setText("Coins: " + player.getObjectCoin());
        currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
        farmerLvl.setText("Farmer level: " + player.getLvl());
    }

    //Below are methods used to show the benefits and requirements of each status
    public void registeredFarmerBenefits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setWidth(500);
        alert.setHeight(500);
        alert.setTitle("Registered Farmer Benefits");
        alert.setHeaderText("Registered Farmer Benefits");
        alert.setContentText(player.getBenefits("Registered Farmer"));
        alert.showAndWait();
    }

    public void distinguishedFarmerBenefits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setWidth(500);
        alert.setHeight(500);
        alert.setTitle("Distinguished Farmer Benefits");
        alert.setHeaderText("Distinguished Farmer Benefits");
        alert.setContentText(player.getBenefits("Distinguished Farmer"));
        alert.showAndWait();
    }

    public void legendaryFarmerBenefits() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setWidth(500);
        alert.setHeight(500);
        alert.setTitle("Legendary Farmer Benefits");
        alert.setHeaderText("Legendary Farmer Benefits");
        alert.setContentText(player.getBenefits("Legendary Farmer"));
        alert.showAndWait();
    }

    //Below are methods used to register new status, given the player meets the requirement
    public void registerToRegisteredFarmer() {
        if (player.getLvl() >= 5 && player.getFarmerType().equals("Farmer") && player.getObjectCoin() >= 200) {
            player.setFarmerType(1);
            currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registered Farmer");
            alert.setHeaderText("Registered Farmer");
            alert.setContentText("You have successfully registered as a Registered Farmer!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Registered Farmer");
            alert.setHeaderText("Registered Farmer");
            alert.setContentText("You do not meet the requirements to register as a Registered Farmer!");
            alert.showAndWait();
        }
    }

    public void registerToDistinguishedFarmer() {
        if (player.getLvl() >= 10 && player.getFarmerType().equals("Registered Farmer") && player.getObjectCoin() >= 300) {
            player.setFarmerType(2);
            currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Distinguished Farmer");
            alert.setHeaderText("Distinguished Farmer");
            alert.setContentText("You have successfully registered as a Distinguished Farmer!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Distinguished Farmer");
            alert.setHeaderText("Distinguished Farmer");
            alert.setContentText("You do not meet the requirements to register as a Distinguished Farmer!");
            alert.showAndWait();
        }
    }

    public void registerToLegendaryFarmer() {
        if (player.getLvl() >= 15 && player.getFarmerType().equals("Distinguished Farmer") && player.getObjectCoin() >= 400) {
            player.setFarmerType(3);
            currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Legendary Farmer");
            alert.setHeaderText("Legendary Farmer");
            alert.setContentText("You have successfully registered as a Legendary Farmer!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Legendary Farmer");
            alert.setHeaderText("Legendary Farmer");
            alert.setContentText("You do not meet the requirements to register as a Legendary Farmer!");
            alert.showAndWait();
        }
    }


    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setFarm(player, playerLot);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
