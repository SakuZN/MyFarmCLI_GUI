package com.example.Controllers;

import Farmer.Farmer;
import Farmer.FarmLot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class setupScreenController {

    @FXML
    GridPane gridPane;

    @FXML
    Button startButton;
    @FXML
    TextField farmerNamePrompt;
    @FXML
    TextField farmNamePrompt;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onAction(ActionEvent e) throws IOException {
        String farmerName = farmerNamePrompt.getText();
        String farmName = farmNamePrompt.getText();


        if ((farmerName.length() < 3 || farmerName.length() > 15 || !isAlphabet(farmerName)) &&
                (farmName.length() < 3 || farmName.length() > 15 || !isAlphabet(farmName))) {
            alertMessage();
        } else if (farmerName.length() < 3 || farmerName.length() > 15 || !isAlphabet(farmerName)) {
            alertMessage();
        } else if (farmName.length() < 3 || farmName.length() > 15 || !isAlphabet(farmName)) {
            alertMessage();
        }
        else {
            switchToMain(farmerName, farmName, e);
        }

    }
    public void setStage(Stage stage) { this.stage = stage;}

    private void switchToMain (String farmerName, String farmName, ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        root = loader.load();
        MainClassController mainScene = loader.getController();
        mainScene.setFarm(new Farmer(farmerName), new FarmLot(farmName));
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public boolean isAlphabet(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    public void alertMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeight(500);
        alert.setWidth(500);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input, please try again");
        alert.setContentText("Farmer's name and Farm name must be between 3-15 characters long" +
                        " and must not contain any numbers or special characters");
        alert.showAndWait();
    }

}