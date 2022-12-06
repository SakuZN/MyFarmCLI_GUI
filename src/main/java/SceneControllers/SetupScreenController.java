package SceneControllers;

import FarmerModel.FarmLot;
import FarmerModel.Farmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * This class is the controller for the setup scene.
 * It handles all cases where the user sets up the game.
 */
public class SetupScreenController implements Initializable {

    /** Default constructor for SetupScreenController class.
     * Not used as a different method is used to initialize the data for the scene. */
    public SetupScreenController(){}

    /**The button node that starts the game. */
    @FXML
    private Button startButton;

    /**The button node that displays the controls. */
    @FXML
    private Button controlsButton;

    /**The text field that prompts the user to enter a name. */
    @FXML
    private TextField farmerNamePrompt;

    /**The text field that prompts the user to enter a farm name. */
    @FXML
    private TextField farmNamePrompt;

    /**The text area that displays the file selected. */
    @FXML
    private TextArea fileName;

    /**The button node that prompts the user to select a seed file. */
    @FXML
    private Button seedFilePrompt;

    /**The button node that generates a random seed. */
    @FXML
    private Button generateSeed;

    /**The file chooser that allows the user to select a file. */
    private final FileChooser FILECHOOSER = new FileChooser();

    /**The lot tile initial placement from selected file. */
    private char[][] lotTilesInitPlacement = new char[10][5];

    /**
     * Initialize method that defaults the file chooser to the current directory
     * also makes the seed text area uneditable
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set file chooser initial directory to current directory
        try {
            String path = URLDecoder.decode
                    (getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath(),
                            StandardCharsets.UTF_8);

            File file = new File(path);
            FILECHOOSER.setInitialDirectory(file.getParentFile());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //Prevent textarea from being edited
        fileName.setEditable(false);
    }

    /**
     * This method switches to the main screen or game provided the user has entered a valid farmer name and
     * farm name and a valid seed
     * @param e the action event
     * @throws IOException the io exception
     */
    public void gameStartPrompt(ActionEvent e) throws IOException {
        String farmerName = farmerNamePrompt.getText();
        String farmName = farmNamePrompt.getText();

        //check first if the player has picked or generated a lot tile placement
        if (fileName.getText().equals("")) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Empty or invalid file.",
                    "Please select or generate a lot tile placement file first.");
            return;
        }
        //Checks for valid farmer name and farm name
        if ((farmerName.length() < 3 || farmerName.length() > 15 || isNotAlphabet(farmerName)) &&
                (farmName.length() < 3 || farmName.length() > 15 || isNotAlphabet(farmName))) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid Farmer Name and Farm Name length or characters",
                    String.format("""
                            Farmer and farm names must be between 3 and 15 characters long and must only contain alphabets.
                            Farmer name length: %d
                            Farm name length: %d
                            """, farmerName.length(), farmName.length()));
        } else if (farmerName.length() < 3 || farmerName.length() > 15 || isNotAlphabet(farmerName)) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid Farmer Name length or characters",
                    String.format("""
                            Farmer name must be between 3 and 15 characters long and must only contain alphabets.
                            Farmer name length: %d
                            """, farmerName.length()));
        } else if (farmName.length() < 3 || farmName.length() > 15 || isNotAlphabet(farmName)) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid Farm Name length or characters",
                    String.format("""
                            Farm name must be between 3 and 15 characters long and must only contain alphabets.
                            Farm name length: %d
                            """, farmName.length()));
        } else { //If all inputs are valid, create new farmer and farm lot
            switchToMain(farmerName, farmName, lotTilesInitPlacement, e);
        }

    }

    /**
     * This method shows the controls for the game via a pop-up alert window
     */
    public void controlsPrompt() {
        ContractController.showMsgInfo("Information",
                "Controls",
                "Controls for MyFarm Game",
                """
                        This game is mostly controlled by the mouse. You can click on the buttons to navigate the game.
                        However, there will be lots of prompts when you are playing the game.
                        
                        You can use the SPACEBAR to easily confirm the prompts.
                        
                        Do be careful when using the SPACEBAR, as you can unintentionally skip important prompts
                        or do actions that you do not want to do.
                        """);
    }

    /**
     * This method lets the user see the lot tile placement from selected or generated file
     * and will prevent the user from seeing the lot tile placement if the file is invalid or empty
     */
    public void seeLotTiles() {
        //Prevent user from seeing lot tiles if they have not generated or selected a file
        if (fileName.getText().equals("")) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "No file selected",
                    "Please select a valid file or generate one to view lot tiles");
            return;
        }
        //Setup the stage and scene
        Stage stage = new Stage();
        stage.setTitle("Lot Tiles");
        stage.setScene(new Scene(new Group()));
        stage.setWidth(300);
        stage.setHeight(350);
        //prevent window from being resized
        stage.setResizable(false);
        stage.getIcons().add(new Image("sun.png"));

        //Add label
        final Label label = new Label("Lot Tiles from text file");
        label.setFont(new Font("Arial", 20));

        //Add text area where the lot tile placement from file will be displayed
        final TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
        //set textarea to display the lot tiles line by line
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                textArea.appendText(lotTilesInitPlacement[i][j] + "   ");
            }
            textArea.appendText("\n");
        }
        textArea.setPrefColumnCount(10);
        textArea.setFont(new Font("Arial", 20));

        //Finally, setup VBox and add label and text area to it and show to user
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 10, 5, 10));
        vbox.getChildren().addAll(label, textArea);
        ((Group) stage.getScene().getRoot()).getChildren().addAll(vbox);
        stage.show();
    }

    /**
     * This method lets the user select a lot tile file and display the current file name and directory
     * provided the user has selected a valid .txt file and the text format inside is correct
     * @throws FileNotFoundException the file not found exception when user does not select a file
     */
    public void pickLotTileFile() throws FileNotFoundException {
        //Opens file chooser window
        File file = FILECHOOSER.showOpenDialog(new Stage());
        Scanner scanner = new Scanner(file);

        //Checks if file is valid and a .txt file, if not, show error message
        if (!file.getName().endsWith(".txt")) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid File",
                    "Please select a valid .txt file");
            scanner.close();
            return;
        }

        //Checks if file has 10 lines, if not, show error message
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        if (lineCount != 10) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid lot tile generation",
                    "Please make sure its a 10x5 grid of x and o");
            scanner.close();
            return;
        }
        //reset scanner
        scanner = new Scanner(file);

        //scan file character by character per line and store in 2D array
        int row = 0;
        char[][] lotTilesTemp = new char[10][5];
        int xCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //check if line consist of only x and o and is 5 characters long
            if (!line.matches("[xo]+") && line.length() != 5) {
                ContractController.showMsgInfo("Error",
                        "Error",
                        "Invalid lot tile generation",
                        "Please make sure its a 10x5 grid of x and o");
                scanner.close();
                return;
            }
            for (int col = 0; col < line.length(); col++) {
                lotTilesTemp[row][col] = line.charAt(col);
                //Counts the number of x's in the file for validation later
                if (lotTilesTemp[row][col] == 'x') {
                    xCount++;
                }
            }
            row++;
        }
        //final check to see if lot tiles contain at least 10 x and at most, 30 x
        if (xCount < 10 || xCount > 30) {
            ContractController.showMsgInfo("Error",
                    "Error",
                    "Invalid lot tile generation",
                    "Please make sure there are at least 10 'x' and at most 30 'x'");
            scanner.close();
            return;
        }

        //copy temp array to lotTiles
        lotTilesInitPlacement = Arrays.copyOf(lotTilesTemp, lotTilesTemp.length);
        //display lot tiles .txt file in text area
        fileName.setText(file.getAbsolutePath());
        //finally, close scanner
        scanner.close();
    }

    /**
     * This method generates a random seed and displays it in the lot tile text area
     * Also outputs the generated lot tile placement to a file called "lotTiles.txt" in the current directory
     * @throws IOException the io exception when the file cannot be created
     */
    public void generateRandomLotTile() throws IOException {
        //generate a random 10x5 grid of x and o
        Random random = new Random();
        char[][] lotTilesTemp = new char[10][5];
        for (int row = 0; row < lotTilesTemp.length; row++) {
            for (int col = 0; col < lotTilesTemp[row].length; col++) {
                lotTilesTemp[row][col] = random.nextBoolean() ? 'x' : 'o';
            }
        }
        //copy temp array to lotTiles
        lotTilesInitPlacement = Arrays.copyOf(lotTilesTemp, lotTilesTemp.length);

        //store the generated lot tile in a file called "lotTile.txt"
        File file = new File("lotTile.txt");
        FileWriter fileWriter = new FileWriter(file);
        for (char[] lotTile : lotTilesInitPlacement) {
            for (char c : lotTile)
                fileWriter.write(c);

            fileWriter.write(System.lineSeparator());
        }

        fileWriter.close();

        //Finally, show a message to the user that the seed has been generated
        ContractController.showMsgInfo("Information",
                "Information",
                "Lot Tile Generated",
                String.format("""
                        A random lot tile has been generated and saved to the current directory.
                        %s
                        """, file.getAbsolutePath()));
        fileName.setText(file.getAbsolutePath());
    }

    /**
     * This method switches to the main screen or game, passing the data of the farmer and farm lot and the seed
     * to the main scene game
     * @param farmerName the farmer name
     * @param farmName the farm name
     * @param lotTilesFile the lot tiles file
     * @param e the action event
     * @throws IOException the io exception
     */
    private void switchToMain(String farmerName, String farmName, char[][] lotTilesFile, ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
        Parent root = loader.load();
        MainClassController mainScene = loader.getController();
        mainScene.setFarm(new Farmer(farmerName), new FarmLot(farmName, lotTilesFile));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method checks if the string contains any characters that are not alphabets
     * @param name the name
     * @return true if the string contains any characters that are not alphabets, false otherwise
     */
    private boolean isNotAlphabet(String name) {
        return !name.matches("[a-zA-Z ]+");
    }

}