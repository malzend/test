package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *                          The main class will LAUNCH the program.<br>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        primaryStage.setTitle("Schedule Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }

    /**
     * MIAN Class will be responsible for the lunching of the app.<br>
     * @param args ontains the command-line arguments passed to the Java program upon invocation.<br>
     */
    public static void main(String[] args) {
       // Locale.setDefault(new Locale("fr","FR"));

        launch(args);

    }
}
