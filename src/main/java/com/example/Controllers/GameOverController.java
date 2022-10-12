package com.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void initialize(){}

    //action event to start game again
    public void switchToMain(ActionEvent e) throws IOException {
        //Alert to say good luck to player
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Game");
        alert.setHeaderText("Good Luck!");
        alert.setContentText("Make sure to do better this time around!");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/setupScreen.fxml"));
        root = loader.load();
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exitGame(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Goodbye!");
        alert.setContentText("Thanks for playing!");
        alert.showAndWait();
        System.exit(0);
    }
}
