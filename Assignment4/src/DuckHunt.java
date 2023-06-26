import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;

public class DuckHunt extends Application {
    public static double SCALE = 3.0; // Scale factor for the game elements
    public static Double Width; // Width of the game window
    public static Double Height; // Height of the game window
    public static double VOLUME = 0.025; // Volume for audio

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an exception occurs during the start
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create the initial scene for the intro screen
        Scene introScene = new IntroScreen().getScene(primaryStage);

        // Load the favicon image
        String uri = new File("assets\\favicon\\1.png").toURI().toString();

        // Calculate the scaled width and height of the game window
        Image favIcon = new Image(uri);
        Width=favIcon.getWidth()* SCALE;
        Height=favIcon.getHeight()* SCALE;

        // Set the title, scene, and favicon for the primary stage
        primaryStage.setTitle("HUBMM Duck Hunt");
        primaryStage.setScene(introScene);
        primaryStage.getIcons().add(favIcon);

        // Show the primary stage
        primaryStage.show();

    }

    /**
     * The main method of the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}