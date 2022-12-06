package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
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

/**
 * This class is the controller for the seed store scene.
 * It handles all cases where the user buys seeds.
 */
public class SeedStoreController extends ContractController {

    /** Default constructor for SeedStoreController class.
     * Not used as a different method is used to initialize the data for the scene. */
    public SeedStoreController(){}

    /**The table view that displays the seeds the farmer can buy. */
    @FXML
    private TableView<Seeds> seedView;

    /**The table column that displays the seed name. */
    @FXML
    private TableColumn<Seeds, String> seedName;

    /**The table column that displays the seed type. */
    @FXML
    private TableColumn<Seeds, String> seedType;

    /**The table column that displays the seed cost. */
    @FXML
    private TableColumn<Seeds, Integer> seedCost;

    /**The text node that displays the number of coins the player currently has. */
    @FXML
    private Text objectCoin;

    /**The text node that displays the number of seeds the farmer has. */
    @FXML
    private Text seedOwned;

    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method gets the necessary data for Farmer and Farmlot from the previous screen or the main screen
     * @param player the player object
     * @param playerLot the player lot object
     */
    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;
        // Adds or updates the scene text nodes
        objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
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

    /**
     * This method gets the seed list from the store class
     * Instantiates the Store class and seed list adds all the seeds to the list
     * @return the seed list
     */
    private ObservableList<Seeds> getSeeds () {
        ObservableList<Seeds> seedList = FXCollections.observableArrayList();
        Store seedStore = new Store(player.getFarmerType());
        seedList.addAll(seedStore.getAllForSale());
        return seedList;
    }

    /**
     * This method lets the farmer buy a seed from selected seed list
     */
    public void buySeed() {
        // Get the selected seed from the table view
        Seeds selectedSeed = seedView.getSelectionModel().getSelectedItem();

        // If user chose a seed to buy
        if (selectedSeed != null) {
            // If the farmer has enough coins to buy the seed
            if (player.getObjectCoin() >= selectedSeed.getSeedCost()) {
                // Add the seed to the farmer's seed list and deduct the cost from the farmer's coin
                player.decreaseObjectCoin(selectedSeed.getSeedCost());
                playerLot.increaseSeed(selectedSeed);
                // Update the scene text nodes
                objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
                seedOwned.setText("Seeds owned: " + playerLot.getSeeds().size());
            } else {
                //Else, display an error message to the farmer
                showMsgInfo("Information",
                        "Not enough coins",
                        null,
                        "You do not have enough coins to buy " + selectedSeed.getSeedName());
            }
        } else { // If user did not choose a seed to buy
            showMsgInfo("Information",
                    "No seed selected",
                    null,
                    "Please select a seed to buy.");
        }
    }

    /**
     * This method shows information about the seed via a pop-up alert window
     */
    public void seedInformation () {
        Seeds selectedSeed = seedView.getSelectionModel().getSelectedItem();
        //Show information about the seed selected from the list
        if (selectedSeed != null) {
            showMsgInfo("Information",
                    selectedSeed.getSeedName(),
                    null,
                    selectedSeed.getSeedInfo());
        } else {
            showMsgInfo("Error",
                    "No seed selected",
                    null,
                    "Please select a seed to view information.");
        }
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
        mainClassController.setFarm(player, playerLot);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }
}
