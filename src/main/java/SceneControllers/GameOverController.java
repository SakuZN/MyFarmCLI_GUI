package SceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the controller for the game over scene.
 * It lets the user know that the game is over and displays the number of days lasted.
 * Also allows the user to go back to the game or quit the game.
 */
public class GameOverController {

    /** Default constructor for GameOverController class.
     * Not used as a different method is used to pass data values. */
    public GameOverController(){}

    /** Text node that displays the number of days lasted. */
    @FXML
    private Text dayCount;

    /**
     * Initialize method purposely left blank
     * as we are using a different method to initialize the data for the scene
     */
    private void initialize() {
    }

    /**
     * This method gets the day count from the previous screen and displays it
     * @param dayCount the day count
     */
    public void getDayCount(int dayCount) {
        this.dayCount.setText("You lasted for: " + dayCount + " days!");
    }

    /**
     * This method is called when the user presses yes on playing the game again
     * @param e the event that triggered the method
     * @throws IOException if the fxml file is not found
     */
    public void switchToMain(ActionEvent e) throws IOException {
        //Alert to say good luck to player
        ContractController.showMsgInfo("Information",
                "New Game",
                "Good Luck!",
                "Make sure to do better this time around!");
        //Switch to main screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetupView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called when the user presses no on playing the game again
     * It exits the game
     */
    public void exitGame() {
        ContractController.showMsgInfo("Information",
                "Exit Game",
                "Goodbye!",
                "Thanks for playing!");
        System.exit(0);
    }
}
