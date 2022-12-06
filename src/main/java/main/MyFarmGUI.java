package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.PlatformLoggingMXBean;

/**
 * This class is the main class for the game.
 * It starts the game.
 */
public class MyFarmGUI extends Application {

    /**Default constructor for MyFarmGUI class.
     * Never used. */
    public MyFarmGUI() {}

    //disable logger for javafx due to bug (unnecessary warning calls)
    static {
        PlatformLoggingMXBean loggingMXBean = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
        for (String loggerName : loggingMXBean.getLoggerNames()) {
            loggingMXBean.setLoggerLevel(loggerName, "OFF");
        }
    }

    /**
     * This method starts the game.
     * @param stage the stage to start the game on.
     * @throws IOException if the fxml file is not found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetupView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // image icon of sun
        stage.getIcons().add(new Image("sun.png"));
        stage.setTitle("Harvest Sun (MyFarm Sim Game)");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Driver method to call the launch method which starts the game.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
