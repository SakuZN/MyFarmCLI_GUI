package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Seeds;

import java.io.IOException;
import java.util.Optional;

/**
 * This class is the controller for the main game scene.
 * Shows a menu which allows the player to do various actions.
 */
public class MainClassController extends ContractController {

    /** Default constructor for MainClassController class.
     * Not used as a different method is used to pass data values. */
    public MainClassController(){}

    /**The grid pane region that contains the farm tiles or farm lot. */
    @FXML
    private GridPane gridPaneLot;

    /**The split pane region that contains the menu and various actions that can be done. */
    @FXML
    private SplitPane splitPane;

    /**The text node that displays the farm name. */
    @FXML
    private Text farmName;

    /**The text node that displays the number of coins the player currently has. */
    @FXML
    private Text objectCoin;

    /**The text node that displays the number of planted seeds. */
    @FXML
    private Text plantedSeeds;

    /**The text node that displays the number of seeds that can be harvested. */
    @FXML
    private Text seedsHarvest;

    /**The text node that displays the number of days passed. */
    @FXML
    private Text daysPassed;

    /**The array button nodes that represent the tiles on the farm lot. */
    private Button[][] buttonLot = new Button[10][5];

    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method passes important data from any controller to this controller
     * This method is always called first before the scene is shown
     * @param player the player object
     * @param playerLot the player lot object
     */
    @Override
    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;

        //Adds or update the text nodes in the main game scene
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
        daysPassed.setText(("Days passed: " + player.getDaysPassed()));
        seedsHarvest.setText("Seeds harvestable: " + playerLot.getHarvestCount());
        plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

        //Add buttons to gridPaneLot
        buttonReinstate();

    }

    /**
     * This method passes important data from any controller to this controller
     * This method is used when a controller modifies the button nodes
     * This is called first before the scene is shown
     * @param player the player object
     * @param playerLot the player lot object
     * @param button the button node representing the farm tile
     */
    @Override
    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {
        this.player = player;
        this.playerLot = playerLot;
        this.buttonLot = button;

        //Adds or update the text nodes in the main game scene
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
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

    /**
     * This method reinstates the buttons on the farm lot
     */
    private void buttonReinstate() {
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {

                buttonLot[i][j] = new Button();
                buttonLot[i][j].setPrefSize(100, 100);
                buttonLot[i][j].setText(playerLot.getTileStatus(i, j));
                setButtonStyle(buttonLot[i][j]);
                setTooltip(buttonLot[i][j], playerLot.getTileStatus(i, j));
                gridPaneLot.add(buttonLot[i][j], j, i);
            }
        }
    }

    /**
     * This method switches the scene to the store scene and passing data to the store controller
     * @param e the action event
     * @throws IOException if the fxml file is not found
     */
    public void visitStore(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VisitStoreView.fxml"));
        root = loader.load();
        SeedStoreController storeScene = loader.getController();
        storeScene.setFarm(player, playerLot);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method advances the day, which grows the crops and updates the farm lot
     * Checks first if there are plants ready to be harvested or if there are seeds that withered
     * Also checks for game over conditions
     * @param e the action event
     * @throws IOException if the fxml file is not found
     */
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
                    //If withered, update tile status and button text and style
                    playerLot.setTileStatus(seed.getxCoord(), seed.getyCoord(), 3);
                    buttonLot[seed.getxCoord()][seed.getyCoord()].setText("Withered");
                    setButtonStyle(buttonLot[seed.getxCoord()][seed.getyCoord()]);
                    witherCount++;
                }
            }

            //Checks for any planted seeds that are ready to be harvested
            for (Seeds seed : playerLot.getPlantedSeeds())
                if (seed.canHarvestSeed()) {
                    //If ready to be harvested, update tile status and button text and style
                    buttonLot[seed.getxCoord()][seed.getyCoord()].setText("Harvest");
                    setButtonStyle(buttonLot[seed.getxCoord()][seed.getyCoord()]);
                    harvestCount++;
                }
        }

        //Increase day count by 1 and update appropriate text node
        player.passDay();
        daysPassed.setText(("Days passed: " + player.getDaysPassed()));

        //update text for seeds harvestable
        seedsHarvest.setText("Seeds harvestable: " + harvestCount);

        //Show message that day has advanced
        showMsgInfo("Information",
                "Advance Day",
                "Good morning!",
                "Rise and shine farmer! A new day has risen."
        );

        //Show message if there are seeds that withered
        if (witherCount > 0) {
            showMsgInfo("Warning",
                    "Withered Seeds",
                    "Uh oh! Planted seeds have withered!",
                    "You have " + witherCount + " withered seeds.");
        }
        //Show message if there are seeds that are ready to be harvested
        if (harvestCount > 0) {
            showMsgInfo("Information",
                    "Harvest seeds",
                    "Today is a good day!",
                    "You have " + harvestCount + " seeds ready to be harvested!"
            );
        }
        //Game over condition, switch to game over scene if game over condition is true
        if (checkFailCondition()) {
            showMsgInfo("Error",
                    "Game Over",
                    "Today is a sad day!",
                    "You have failed to grow your farm!"
            );
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOverView.fxml"));
            root = loader.load();
            GameOverController gameOverScene = loader.getController();
            gameOverScene.getDayCount(player.getDaysPassed());
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This method switched to tend farm scene by calling the TendFarmController
     * @param e the action event
     * @throws IOException if the fxml file is not found
     */
    public void tendFarm(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TendFarmView.fxml"));
        root = loader.load();
        TendFarmController tendFarmScene = loader.getController();
        tendFarmScene.setFarm(player, playerLot, buttonLot);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**
     * This method switches to the plant seed scene by calling the PlantSeedController
     * @param e the action event
     * @throws IOException if the fxml file is not found
     */
    public void plantSeed(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlantSeedView.fxml"));
        root = loader.load();
        PlantSeedController plantSeedScene = loader.getController();
        plantSeedScene.setFarm(player, playerLot, buttonLot);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method harvests the seeds that are ready to be harvested
     */
    public void harvestSeed() {
        int harvestCount = 0;

        //Check for any planted seeds that are ready to be harvested
        for (Seeds seed : playerLot.getPlantedSeeds())
            if (seed.canHarvestSeed())
                harvestCount++;
        //Prevent player from harvesting if there are no seeds ready to be harvested, show warning message
        if (playerLot.getPlantedSeeds().size() == 0 || harvestCount == 0) {
            showMsgInfo("Warning",
                    "Harvest seeds",
                    "You have no seeds to harvest!",
                    "Plant seeds and take care of them to harvest!"
            );
        } else {
            //create a confirmation dialog box about harvesting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("sun.png"));
            alert.setTitle("Harvest seed");
            alert.setHeaderText("There are " + harvestCount + " seeds ready to be harvested!");
            alert.setContentText("Harvest all the seeds?");

            //get the user  result of ok or cancel
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //if the user clicks ok, harvest all harvestable seeds
                for (Seeds seed : playerLot.getPlantedSeeds()) {
                    if (seed.canHarvestSeed())
                        playerLot.getHarvestSeeds().add(seed);

                }
                //Remove all harvested seeds from planted seeds
                playerLot.getPlantedSeeds().removeIf(Seeds::canHarvestSeed);

                //Loop through all harvested seeds
                for (Seeds harvestSeed : playerLot.getHarvestSeeds()) {
                    //Show information of harvest
                    showMsgInfo("Information",
                            "Harvested seed",
                            "You have harvested a " + harvestSeed.getSeedName() + "!",
                            harvestSeed.getHarvestInfo(player.getFarmerType())
                    );

                    //Update player xp and coin amount
                    player.addXP(harvestSeed.getXpYield());
                    player.addObjectCoin(harvestSeed.finalHarvestPrice(player.getFarmerType(),
                            harvestSeed.getSeedType()));

                    //Alert info if player levels up
                    if (player.canLvlUp()) {
                        showMsgInfo("Information",
                                "Level up",
                                "You have leveled up!",
                                "You are now level " + player.getLvl() + "!"
                        );
                    }

                    //update tile status, turning tile to unplowed state
                    playerLot.setTileStatus(harvestSeed.getxCoord(), harvestSeed.getyCoord(), 0);

                    //update scene text
                    objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
                    plantedSeeds.setText("Planted seeds: " + playerLot.getPlantedSeeds().size());

                    //update button information
                    buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()] = new Button();
                    //set width and height of button
                    buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()].setPrefSize(100, 100);
                    //set button text with tile status
                    buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()].setText("Unplowed");
                    //set button tooltip
                    setTooltip(buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()], "Unplowed");
                    //set button style
                    setButtonStyle(buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()]);
                    //Finally, add button to gridPane
                    gridPaneLot.add(buttonLot[harvestSeed.getxCoord()][harvestSeed.getyCoord()],
                            harvestSeed.getyCoord(), harvestSeed.getxCoord());

                }
                //Finally, clear harvestSeed list and update scene text for harvested seeds
                playerLot.getHarvestSeeds().clear();
                seedsHarvest.setText("Harvest seeds: " + playerLot.getHarvestSeeds().size());
            }
        }
    }

    /**
     * This method shows a message dialog box to the user that contains information about the farmer status
     */
    public void viewFarmerStatus() {

        //alert content of farmer name, type, level, xp, and coins
        showMsgInfo("Information",
                "Farmer Status",
                null,
                player.getFarmerStatus()
        );
    }

    /**
     * This method switches to registration of new farmer type scene by calling the RegisterStatusController
     * @param e the action event
     * @throws IOException if the fxml file is not found
     */
    public void registerNewStatus(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterNewStatusView.fxml"));
        root = loader.load();
        RegisterStatusController regStatusScene = loader.getController();
        regStatusScene.setFarm(player, playerLot);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method checks for game over condition
     * @return true if game is over, false otherwise
     */
    private boolean checkFailCondition() {
        int farmerType;
        //Assign farmer type value depending on farmer type
        switch (player.getFarmerType()) {
            case "Registered Farmer" -> farmerType = 1;
            case "Distinguished Farmer" -> farmerType = 2;
            case "Legendary Farmer" -> farmerType = 3;
            default -> farmerType = 0;
        }

        /*
        2 conditions that will return true:
        1. Player's seed and planted seed list is empty and player is unable to buy nor use tools
        2. Player's seed and planted seed list is not empty but the lot is withered and player is unable to buy nor use tools
         */
        return ((playerLot.getSeeds().size() == 0 && playerLot.getPlantedSeeds().size() == 0 &&
                        player.getObjectCoin() < 5 - farmerType) ||
                (player.getObjectCoin() < 5 - farmerType && playerLot.lotWithered()));
    }
}

