package SceneControllers;

import FarmerModel.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.text.DecimalFormat;

/**
 * This class is the abstract controller class that acts as the contract for controllers that share common functionality.
 * This class is never instantiated.
 */
public abstract class ContractController {

    /**Default constructor for ContractController class.
     * Abstract class never needed to be initialized. */
    public ContractController(){}

    /**
     * Record class that stores the x and y coordinate of the button node
     * @param i the x coordinate
     * @param j the y coordinate
     */
    public record Coord(int i, int j) {
    }

    /** Used to format decimal numbers to 2 decimal places. */
    protected static final DecimalFormat DF = new DecimalFormat("0.00");

    /** The stage that the scene is on. */
    protected Stage stage;

    /** The scene that the controller is associated with. */
    protected Scene scene;

    /** The root of the scene that the controller is associated with. */
    protected Parent root;

    /**The farmer object that represents the player. */
    protected Farmer player;

    /**The farm lot object that represents the player's farm lot. */
    protected FarmLot playerLot;

    /**
     * This method is used to pass data between controllers
     * Left blank to be overridden by child classes
     * @param player the player object
     * @param playerLot the player lot object
     */
    public void setFarm(Farmer player, FarmLot playerLot) {

    }

    /**
     * This method is used to pass data between controllers that uses the button nodes (which represents the farm tiles)
     * Left blank to be overridden by child classes
     * @param player the player object
     * @param playerLot the player lot object
     * @param button the button node representing the farm tile
     */
    public void setFarm(Farmer player, FarmLot playerLot, Button[][] button) {

    }

    /**
     * This method sets tooltip on the button nodes, which shows information about the tile
     * @param button the button node representing the farm tile
     * @param text the text to be displayed on the tooltip
     */
    public void setTooltip(Button button, String text) {
        Tooltip tooltip = new Tooltip();
        switch (text) {
            case "Unplowed" -> tooltip.setText("This tile is unplowed.\nUse plow tool to make the tile plowed.");
            case "Plowed" -> tooltip.setText("This tile is plowed.\nIt is possible to plant seeds.");
            case "Withered" -> tooltip.setText("This tile is withered.\nUse shovel tool to remove it");
            case "Rock" -> tooltip.setText("This tile contains rock.\nUse Pickaxe tool to remove rock.");
            default -> tooltip.setText(text + " is planted here." + "\nTake good care of it!");
        }
        //Set the tooltip style and dialog box size
        tooltip.setStyle("-fx-font: normal bold 4 Langdon; "
                + "-fx-font-size: 12;"
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        tooltip.setPrefSize(150, 100);
        tooltip.setWrapText(true);
        tooltip.setShowDelay(javafx.util.Duration.seconds(0.5));
        button.setTooltip(tooltip);
    }

    /**
     * This method changes the style of the button node depending on the tile status
     * @param button the button node representing the farm tile
     */
    public void setButtonStyle(Button button) {
        String Val = button.getText();
        //Change text of button depending on tile status
        switch (Val) {
            case "Unplowed" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #fa8072;" + "-fx-font: normal bold 12 Langdon;");
            case "Plowed" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #ffa500;" + "-fx-font: normal bold 12 Langdon;");
            case "Planted", "Harvest" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #228C22;" + "-fx-font: normal bold 12 Langdon;");
            case "Withered" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #DC143C;" + "-fx-font: normal bold 12 Langdon;");
            case "Rock" -> button.setStyle("-fx-border-color: green;"
                    + "-fx-text-fill: #a48c1d;" + "-fx-font: normal bold 12 Langdon;");
        }
    }

    /**
     * This method is used to display an alert dialog box
     * Left static to be used by other controllers that do not need to extend this class
     * @param alertType the type of alert
     * @param title the title of the alert
     * @param header the header of the alert
     * @param content the content of the alert
     */
    public static void showMsgInfo(String alertType, String title, String header, String content) {
        Alert alert;
        switch (alertType) {
            case "Error" -> alert = new Alert(Alert.AlertType.ERROR);
            case "Warning" -> alert = new Alert(Alert.AlertType.WARNING);
            case "Information" -> alert = new Alert(Alert.AlertType.INFORMATION);
            default -> alert = new Alert(Alert.AlertType.INFORMATION);
        }
        //Set the alert dialog pane size, image icon, and content
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("sun.png"));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
