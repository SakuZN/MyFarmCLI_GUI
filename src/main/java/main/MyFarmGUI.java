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

public class MyFarmGUI extends Application {
    //disable logger for javafx due to bug (unnecessary warning calls)
    static {
        PlatformLoggingMXBean loggingMXBean = ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);
        for (String loggerName : loggingMXBean.getLoggerNames()) {
            loggingMXBean.setLoggerLevel(loggerName, "OFF");
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/setupScreen.fxml"));
        root = loader.load();
        scene = new Scene(root);
        // image icon of sun
        stage.getIcons().add(new Image("sun.png"));
        stage.setTitle("Harvest Sun (MyFarm Sim Game)");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
