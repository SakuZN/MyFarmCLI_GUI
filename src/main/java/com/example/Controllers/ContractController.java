package com.example.Controllers;

import Farmer.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public abstract class ContractController {

    //Limit double type values into two decimal places
    protected static final DecimalFormat df = new DecimalFormat("0.00");
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    public void setFarm(Farmer player, FarmLot playerLot) {

    }
    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {

    }

    //Sets the tooltip for each button, shows relevant information about the tile
    public void setTooltip(Button button, String text) {
        Tooltip tooltip = new Tooltip();
        switch (text) {
            case "Unplowed" -> tooltip.setText("This tile is unplowed.\nUse plow tool to make the tile plowed.");
            case "Plowed" -> tooltip.setText("This tile is plowed.\nIt is possible to plant seeds.");
            case "Withered" -> tooltip.setText("This tile is withered.\nUse shovel tool to remove it");
            case "Rock" -> tooltip.setText("This tile contains rock.\nUse Pickaxe tool to remove rock.");
            default -> tooltip.setText(text + " is planted here." + "\nTake good care of it!");
        }
        tooltip.setStyle("-fx-font: normal bold 4 Langdon; "
                + "-fx-font-size: 12;"
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        tooltip.setPrefSize(150, 100);
        tooltip.setWrapText(true);
        tooltip.setShowDelay(javafx.util.Duration.seconds(0.5));
        button.setTooltip(tooltip);
    }

    public void setButtonStyle(Button button) {
        String Val = button.getText();
        //Change text of button depending on tile status
        switch (Val) {
            case "Unplowed" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #fa8072;" + "-fx-font: normal bold 12 Langdon;");
            case "Plowed" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #ffa500;" + "-fx-font: normal bold 12 Langdon;");
            case "Planted" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #228C22;" + "-fx-font: normal bold 12 Langdon;");
            case "Withered" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #DC143C;" + "-fx-font: normal bold 12 Langdon;");
            case "Rock" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #a48c1d;" + "-fx-font: normal bold 12 Langdon;");
        }
    }

    //Stores the coordinate of the button pressed in the gridPane
    public static class Coord {
        private final int i;
        private final int j;

        public Coord(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }

}
