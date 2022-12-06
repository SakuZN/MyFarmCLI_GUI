package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the controller for the registering of farmer status scene.
 * It handles all cases where the user registers their new farmer status.
 */
public class RegisterStatusController extends ContractController {

    /** Default constructor for RegisterStatusController class.
     * Not used as a different method is used to initialize the data for the scene. */
    public RegisterStatusController(){}

    /**The text node that displays the number of coins the farmer has. */
    @FXML
    private Text objectCoin;

    /**The text node that displays the current farmer status. */
    @FXML
    private Text currentFarmerStatus;

    /**The text node that displays the farmer level. */
    @FXML
    private Text farmerLvl;

    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method gets the necessary data for Farmer and Farmlot
     * from the previous screen or the main screen
     * @param player the player object
     * @param playerLot the player lot object
     */
    @Override
    public void setFarm(Farmer player, FarmLot playerLot) {
        this.player = player;
        this.playerLot = playerLot;

        // Adds or updates the text nodes in the scene
        objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
        currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
        farmerLvl.setText("Farmer level: " + player.getLvl());
    }

    /**
     * This method shows the benefits of being a "Registered" farmer by displaying an alert
     */
    public void registeredFarmerBenefits() {
        showMsgInfo("Information",
                "Registered Farmer Benefits",
                null,
                player.getBenefits("Registered Farmer"));
    }

    /**
     * This method shows the benefits of being a "Distinguished" farmer by displaying an alert
     */
    public void distinguishedFarmerBenefits() {
        showMsgInfo("Information",
                "Distinguished Farmer Benefits",
                null,
                player.getBenefits("Distinguished Farmer"));
    }

    /**
     * This method shows the benefits of being a "Legendary" farmer by displaying an alert
     */
    public void legendaryFarmerBenefits() {
        showMsgInfo("Information",
                "Legendary Farmer Benefits",
                null,
                player.getBenefits("Legendary Farmer"));
    }

    /**
     * This method registers the farmer as a "Registered" farmer provided that the farmer meets the requirements
     */
    public void registerToRegisteredFarmer() {
            // Checks if the farmer meets the requirements to register as a "Registered" farmer
            if (player.registerNewFarmerType("Registered Farmer")) {
                //Updates the appropriate scene text
                currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
                objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                //Finally, show information to the player
                showMsgInfo("Information",
                        "Successful Registration!",
                        null,
                        "You have successfully registered as a Registered Farmer!");
        } else {
                showMsgInfo("Warning",
                        "Error in Registration!",
                        null,
                        "You do not meet the requirements to register as a Registered Farmer!");
        }
    }

    /**
     * This method registers the farmer as a "Distinguished" farmer provided that the farmer meets the requirements
     */
    public void registerToDistinguishedFarmer() {
        // Checks if the farmer meets the requirements to register as a "Distinguished" farmer
        if (player.registerNewFarmerType("Distinguished Farmer")) {
                //Updates the  appropriate scene text nodes
                currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
                objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));

                //Finally, show information to the player
                showMsgInfo("Information",
                        "Successful Registration!",
                        null,
                        "You have successfully registered as a Distinguished Farmer!");
        } else {
                showMsgInfo("Warning",
                        "Error in Registration!",
                        null,
                        "You do not meet the requirements to register as a Distinguished Farmer!");
        }
    }

    /**
     * This method registers the farmer as a "Legendary" farmer provided that the farmer meets the requirements
     */
    public void registerToLegendaryFarmer() {
        // Checks if the farmer meets the requirements to register as a "Legendary" farmer
        if (player.registerNewFarmerType("Legendary Farmer")) {
                //Updates the appropriate scene text nodes
                currentFarmerStatus.setText("Farmer status: " + player.getFarmerType());
                objectCoin.setText("Coins: " + DF.format(player.getObjectCoin()));
                //Finally, show information to the player
                showMsgInfo("Information",
                        "Successful Registration!",
                        null,
                        "You have successfully registered as a Legendary Farmer!");
        } else {
            showMsgInfo("Warning",
                        "Error in Registration!",
                        null,
                        "You do not meet the requirements to register as a Legendary Farmer!");
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
