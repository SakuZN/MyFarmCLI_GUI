package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.Seeds;

import java.io.IOException;

/**
 * This class is the controller for the plant seed scene.
 * It handles all cases where the user plants a seed.
 */
public class PlantSeedController extends ContractController {

    /** Default constructor for PlantSeedController class.
     * Not used as a different method is used to initialize the data for the scene. */
    public PlantSeedController(){}

    /**The grid pane region that holds the farm tiles or lot. */
    @FXML
    private GridPane gridPaneLot;

    /**The text node that displays the farm name. */
    @FXML
    private Text farmName;

    /**The text node that displays the number of coins the player currently has. */
    @FXML
    private Text objectCoin;

    /**The text node that displays the number of planted seeds the farmer has. */
    @FXML
    private Text plantedSeeds;

    /**The text node that displays the number of seeds the farmer has. */
    @FXML
    private Text seedsOwned;

    /**The list view that displays the seeds the farmer has. */
    @FXML
    private ListView<Seeds> seedList;

    /**The array of button nodes that represent the tiles on the farm lot. */
    private Button[][] buttonLot;


    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method gets the necessary data for Farmer, Farmlot, and button array
     * from the previous screen or the main screen
     * @param player the player object
     * @param playerLot the player lot object
     * @param button the button node representing the farm tile
     */
    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {
        this.player = player;
        this.playerLot = playerLot;
        this.buttonLot = button;
        //Adds or update the text nodes in the scene
        farmName.setText("The " + playerLot.getFarmName() + " lot");
        farmName.setTextAlignment(TextAlignment.CENTER);
        this.objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
        this.seedsOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
        this.plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

        //Adds the seeds to the listview node
        seedList.getItems().addAll(playerLot.getSeeds());
        //Enable single selection
        seedList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //change listview font size
        seedList.setStyle("-fx-font-size: 16px;");

        //Sets button with a custom event handler (when the button is clicked, do something)
        for (int i = 0; i < playerLot.getLotTiles().length; i++) {
            for (int j = 0; j < playerLot.getLotTiles()[i].length; j++) {
                gridPaneLot.add(this.buttonLot[i][j], j, i);
                this.buttonLot[i][j].setUserData(new Coord(i, j));
                //adds an action event on button, used when tool is selected
                this.buttonLot[i][j].setOnAction(event -> {
                    Button b = (Button) event.getSource();
                    plantSeed(b);
                });
            }
        }
    }

    /**
     * This method plants a seed on the farm lot
     * @param button the button node representing the farm tile
     */
    public void plantSeed(Button button) {
        //Get the index of the selected seed from listview
        int listIndex = seedList.getSelectionModel().getSelectedIndex();
        //Get the seedType string from selected seed from listview
        String seedType = seedList.getSelectionModel().getSelectedItem().getSeedType();

        //if no seed is selected, do nothing and show error message
        if (listIndex == -1) {
            showMsgInfo("Error",
                    "Error",
                    "No seed selected",
                    "Please select a seed to plant");
        } else {
            //Get button x and y coordinates to be used to store this data to planted seed
            Coord coord = (Coord) button.getUserData();
            int x = coord.i();
            int y = coord.j();

            //If tile already has a seed planted
            if (playerLot.getTileStatus(x, y).equals("Planted")) {
                showMsgInfo("Error",
                        "Error",
                        "Tile already planted",
                        "Please find another tile to plant");
            } else if (!playerLot.getTileStatus(x, y).equals("Plowed")) {
                //If tile is not plowed
                showMsgInfo("Error",
                        "Error",
                        "Tile not plowed",
                        "Please plow the tile before planting");
            }
            else {
                //Special condition for seed type Fruit Tree
                if (seedType.equals("Fruit Tree")) {
                    Seeds fruitTree = new Seeds(playerLot.getSeeds().get(listIndex));
                    if (!fruitTree.canPlantTree(x, y, playerLot.getLotTiles())) {
                        showMsgInfo("Error",
                                "Error",
                                "Cannot plant tree",
                                "Please plant the tree in tiles that are not adjacent to other objects");
                        return;
                    }
                }
                //Update relevant button information
                button.setText("Planted");
                //add grid to button style and change button text style to green
                setButtonStyle(button);
                //Update relevant information about seeds in playerLot
                Seeds seed = new Seeds(playerLot.getSeeds().get(listIndex));
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

                //update text node in scene of number of seeds owned
                seedsOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
                plantedSeeds.setText("Seeds planted: " + playerLot.getPlantedSeeds().size());

                //Update tooltip to show what seed has been planted on the chosen tile
                setTooltip(button, seed.getSeedName());

                //Finally, show alert information about action done
                showMsgInfo("Information",
                        "Success!",
                        "Seed planted",
                        seed.getSeedName() + " planted successfully at tile (" + x + ", " + y + ")");
            }
        }
    }

    /**
     * This method shows an alert information for the seed to be planted
     * Information shown depends on the chosen seed from listview node
     */
    public void seedInformation() {
        int listIndex = seedList.getSelectionModel().getSelectedIndex();
        Seeds selectedSeed = playerLot.getSeeds().get(listIndex);
        showMsgInfo("Information",
                selectedSeed.getSeedName(),
                "Information about " + selectedSeed.getSeedName(),
                selectedSeed.getSeedInfo());
    }

    /**
     * This method switches the scene to the main scene and passing data to MainClassController
     * @param event ActionEvent
     * @throws IOException IOException
     */
    public void backButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        root = loader.load();
        MainClassController mainClassController = loader.getController();
        mainClassController.setFarm(player, playerLot, buttonLot);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}
