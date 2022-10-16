package com.example.Controllers;

import Farmer.FarmLot;
import Farmer.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Seeds;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Optional;

public class MainClassController {

    //Limit double type values into two decimal places
    private static final DecimalFormat df = new DecimalFormat("0.00");
    protected Stage stage;
    protected Scene scene;
    protected Parent root;
    @FXML
    GridPane gridPaneLot;
    @FXML
    Text farmName;
    @FXML
    Text objectCoin;
    @FXML
    Text plantedSeeds;
    @FXML
    Text seedsHarvest;
    @FXML
    Text daysPassed;
    Button[][] button = new Button[10][5];
    private Farmer player;
    private FarmLot playerLot;

    @FXML
    private void initialize() {

    }

    //Initializes the player and playerLot
    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;

        //Adds text to the farmLot screen
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        objectCoin.setText("Coins: " + df.format(player.getObjectCoin()));
        daysPassed.setText(("Days passed: " + player.getDaysPassed()));
        seedsHarvest.setText("Seeds harvestable: " + playerLot.getHarvestCount());
        plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

        //Add buttons to gridPaneLot
        buttonReinstate();

    }

    private void buttonReinstate() {
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {

                button[i][j] = new Button();
                button[i][j].setPrefSize(100, 100);
                button[i][j].setText(playerLot.getTileStatus(i, j));
                setButtonStyle(button[i][j]);
                setTooltip(button[i][j], playerLot.getTileStatus(i, j));
                gridPaneLot.add(button[i][j], j, i);
            }
        }
    }

    //Used by other scenes that modifies the playerLot
    public void setButtonFarm(Farmer player, FarmLot playerLot, Button[][] button) {
        this.player = player;
        this.playerLot = playerLot;
        this.button = button;

        //Adds text to the farmLot screen
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        objectCoin.setText("Coins: " + df.format(player.getObjectCoin()));
        daysPassed.setText(("Days passed: " + player.getDaysPassed()));
        seedsHarvest.setText("Seeds harvestable: " + playerLot.getHarvestCount());
        plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());
        //Add updated buttons to gridPaneLot
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {
                button[i][j].setOnAction(event -> {
                });
                gridPaneLot.add(button[i][j], j, i);
            }
        }

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

    public void visitStore(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/visitStore.fxml"));
        root = loader.load();
        seedStoreController storeScene = loader.getController();
        storeScene.setFarm(player, playerLot);
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void advanceDay(ActionEvent e) throws IOException {

        int witherCount = 0;
        int harvestCount = 0;
        //Grow all currently planted seeds
        if (playerLot.getPlantedSeeds().size() > 0) {
            for (int i = 0; i < playerLot.getPlantedSeeds().size(); i++) {
                playerLot.getPlantedSeeds().get(i).grow();
                playerLot.getPlantedSeeds().get(i).checkIfWither();
            }

            //Check for any planted seeds that withered and updates tile if so
            for (Seeds seed : playerLot.getPlantedSeeds()) {
                if (seed.getSeedWithered() == 1) {
                    playerLot.setTileStatus(seed.getxCoord(), seed.getyCoord(), 3);
                    button[seed.getxCoord()][seed.getyCoord()].setText("Withered");
                    //set button background to black
                    setButtonStyle(button[seed.getxCoord()][seed.getyCoord()]);
                    witherCount++;
                }
            }

            //Checks for any planted seeds that are ready to be harvested
            for (Seeds seed : playerLot.getPlantedSeeds()) {
                if (seed.canHarvestseed()) {

                    setButtonStyle(button[seed.getxCoord()][seed.getyCoord()]);
                    harvestCount++;
                }
            }
        }

        //Increase day count by 1
        player.passDay();
        daysPassed.setText(("Days passed: " + player.getDaysPassed()));
        //update text for seeds harvestable and seeds planted
        seedsHarvest.setText("Seeds harvestable: " + harvestCount);
        plantedSeeds.setText("Seeds planted: " + (playerLot.getPlantedSeeds().size() - witherCount));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Advance Day");
        alert.setHeaderText("Good morning!");
        alert.setContentText("Rise and shine farmer! A new day has risen.");
        alert.showAndWait();

        if (witherCount > 0) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Withered seeds");
            alert2.setHeaderText("Uh oh! Planted seeds have withered!");
            alert2.setContentText("You have " + witherCount + " withered seeds.");
            alert2.showAndWait();
        }
        if (harvestCount > 0) {
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Harvest seeds");
            alert3.setHeaderText("Today is a good day!");
            alert3.setContentText("You have " + harvestCount + " seeds ready to be harvested!");
            alert3.showAndWait();
        }
        //Game over condition
        if (checkFailCondition()) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Game Over");
            alert4.setHeaderText("Today is a sad day!");
            alert4.setContentText("You have failed to grow your farm!");
            alert4.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOverScreen.fxml"));
            root = loader.load();
            //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void tendFarm(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tendFarmScreen.fxml"));
        root = loader.load();
        TendFarmController tendFarmScene = loader.getController();
        tendFarmScene.setFarm(player, playerLot, button);
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void plantSeed(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/plantSeedScreen.fxml"));
        root = loader.load();
        PlantSeedController plantSeedScene = loader.getController();
        plantSeedScene.setFarm(player, playerLot, button);
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void harvestSeed(ActionEvent e) throws IOException {
        int harvestCount = 0;
        for (Seeds seed : playerLot.getPlantedSeeds())
            if (seed.canHarvestseed())
                harvestCount++;
        if (playerLot.getPlantedSeeds().size() == 0 || harvestCount == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Harvest seeds");
            alert.setHeaderText("You have no seeds to harvest!");
            alert.setContentText("Plant seeds and take care of them to harvest!");
            alert.showAndWait();
        } else {
            //create a confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Harvest seed");
            alert.setHeaderText("There are " + harvestCount + " seeds ready to be harvested!");
            alert.setContentText("Harvest all the seeds?");
            //get the user  result of ok or cancel
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //if the user clicks ok, harvest all harvestable seeds
                for (Seeds seed : playerLot.getPlantedSeeds()) {
                    if (seed.canHarvestseed())
                        playerLot.getHarvestSeeds().add(seed);

                }
                //Remove all harvested seeds from planted seeds
                playerLot.getPlantedSeeds().removeIf(Seeds::canHarvestseed);

                //Loop through all harvested seeds
                for (Seeds harvestSeed : playerLot.getHarvestSeeds()) {
                    //Show information of harvest
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert.setWidth(500);
                    alert.setHeight(500);
                    alert2.setTitle("Harvested seed");
                    alert2.setHeaderText("You have harvested a " + harvestSeed.getSeedName() + "!");
                    alert2.setContentText(harvestSeed.getHarvestInfo(player.getFarmerType()) +
                            "\n\ngained " + harvestSeed.getXpYield() + " exp!");
                    alert2.showAndWait();

                    //Update player xp and coin amount
                    player.addxp(harvestSeed.getXpYield());
                    player.addObjectCoin(harvestSeed.finalHarvestPrice(player.getFarmerType(), harvestSeed.getSeedType()));
                    //Alert info if player levels up
                    if (player.canLvlUp()) {
                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                        alert3.setTitle("Level up");
                        alert3.setHeaderText("You have leveled up!");
                        alert3.setContentText("You are now level " + player.getLvl() + "!");
                        alert3.showAndWait();
                    }

                    //update tile status
                    playerLot.setTileStatus(harvestSeed.getxCoord(), harvestSeed.getyCoord(), 0);

                    //update scene text
                    objectCoin.setText("Coins: " + df.format(player.getObjectCoin()));
                    plantedSeeds.setText("Planted seeds: " + playerLot.getPlantedSeeds().size());

                    //update button information
                    button[harvestSeed.getxCoord()][harvestSeed.getyCoord()] = new Button();
                    //set width and height of button
                    button[harvestSeed.getxCoord()][harvestSeed.getyCoord()].setPrefSize(100, 100);
                    //set button text with tile status
                    button[harvestSeed.getxCoord()][harvestSeed.getyCoord()].setText("Unplowed");
                    //set button tooltip
                    setTooltip(button[harvestSeed.getxCoord()][harvestSeed.getyCoord()], "Unplowed");
                    //set button style
                    setButtonStyle(button[harvestSeed.getxCoord()][harvestSeed.getyCoord()]);
                    //Finally, add button to gridPane
                    gridPaneLot.add(button[harvestSeed.getxCoord()][harvestSeed.getyCoord()],
                            harvestSeed.getyCoord(), harvestSeed.getxCoord());

                }
                //Finally, clear harvestSeed list
                playerLot.getHarvestSeeds().clear();

            }
        }
    }

    public void viewFarmerStatus(ActionEvent e) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setWidth(500);
        alert.setHeight(500);
        alert.setTitle("Farmer Status");
        alert.setHeaderText("Farmer Status");
        //alert content of farmer name, farmer type, farmer level, and farmer xp
        alert.setContentText("Farmer Name: " + player.getName() +
                "\nFarmer Type: " + player.getFarmerType() +
                "\nFarmer Level: " + player.getLvl() +
                "\nFarmer XP: " + player.getXp());
        alert.showAndWait();
    }

    public void registerNewStatus(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterNewStatusScreen.fxml"));
        root = loader.load();
        RegisterStatusController regStatusScene = loader.getController();
        regStatusScene.setFarm(player, playerLot);
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean checkFailCondition() {
        return (playerLot.getSeeds().size() == 0 && playerLot.getPlantedSeeds().size() == 0 &&
                player.getObjectCoin() <= 2) && playerLot.lotWithered();
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

