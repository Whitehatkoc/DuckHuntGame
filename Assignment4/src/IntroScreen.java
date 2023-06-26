import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

public class IntroScreen {

    public static Timeline animation;
    public static MediaPlayer titleMusic;
    public Scene getScene(Stage primaryStage){

        // Set up the background music
        titleMusic = new MediaPlayer(new Media(new File("assets\\effects\\Title.mp3").toURI().toString()));
        titleMusic.setVolume(DuckHunt.VOLUME);
        
        // Set up the background image
        String uri = new File("assets\\welcome\\1.png").toURI().toString();
        Image favIcon = new Image(uri);
        double height = favIcon.getHeight();
        double width = favIcon.getWidth();
        ImageView favIconNode= new ImageView(favIcon);
        favIconNode.setFitHeight(DuckHunt.SCALE *height);
        favIconNode.setFitWidth(DuckHunt.SCALE *width);
        Pane textVBox= new VBox();

        // Create a VBox for the flashing text
        Text text1 = new Text("PRESS  ENTER  TO  START");
        Text text2 = new Text("    PRESS  ESC  TO  EXIT");
        text1.textAlignmentProperty(); 
        text2.textAlignmentProperty();
        text1.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text1.setFill(Color.ORANGE);
        text2.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text2.setFill(Color.ORANGE);
        textVBox.getChildren().addAll(text1,text2);
        textVBox.setTranslateX((favIconNode.getFitHeight()/2) - 90*DuckHunt.SCALE);
        textVBox.setTranslateY((favIconNode.getFitHeight()*3.0/4.0) - 30*DuckHunt.SCALE);

        // Set up the animation for flashing the text
        animation = new Timeline( new KeyFrame(Duration.seconds(0.95), event -> {
            titleMusic.play();
            titleMusic.setCycleCount(MediaPlayer.INDEFINITE);
            if (text1.getFill().equals(Color.ORANGE)){
                text1.setFill(Color.BLACK);
                text2.setFill(Color.BLACK);
            }
            else {
                text1.setFill(Color.ORANGE);
                text2.setFill(Color.ORANGE);
            }
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Set up the intro pane
        Pane introPane = new StackPane(favIconNode);
        introPane.getChildren().addAll(textVBox);
        introPane.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case ENTER:
                    primaryStage.setScene(new SelectionScene().getScene(primaryStage)); break;
                case ESCAPE: primaryStage.close();  break;
                default:

        }});
        Scene introScene = new Scene(introPane);

        introPane.requestFocus();
        return introScene;
    }
        }
