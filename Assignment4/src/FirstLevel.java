import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
import java.util.ArrayList;
import java.util.Objects;

/**
 * The FirstLevel class represents a scene for the game level. It contains methods for setting up and managing the game level.
 */
public class FirstLevel {
    // Declare instance variables
    private   Integer movementNumber = 0;
    public static Double heightDucks ; // the height of the duck
    public static Double widthDucks ; //the width of the duck
   public Integer ammoLeft = 3;
    private  double imageX ;
    private double imageSpeedX;
    public static MediaPlayer gunShotMusic = new MediaPlayer(new Media(new File("assets\\effects\\Gunshot.mp3").toURI().toString()));
    public static MediaPlayer levelCompletedMusic =new MediaPlayer(new Media(new File("assets\\effects\\LevelCompleted.mp3").toURI().toString()));
    public static MediaPlayer gameOverMusic = new MediaPlayer(new Media(new File("assets\\effects\\GameOver.mp3").toURI().toString()));
    public static MediaPlayer duckFallsMusic =new MediaPlayer(new Media(new File("assets\\effects\\DuckFalls.mp3").toURI().toString()));


    /**
     * Returns the Scene object for the level one game scene.
     *
     * @param primaryStage     The primary stage of the JavaFX application.
     * @param selectedCursor    The selected cursor for the game scene.
     * @param selectedBackground    The selected background image for the game scene.
     * @param countForBackGround    The count for the background.
     * @param foreGroundNode    The foreground node for the game scene.
     * @return The Scene object for the level one game scene.
     */
    public Scene getLevelOneScene(Stage primaryStage, ImageCursor selectedCursor, ImageView selectedBackground, Integer countForBackGround,ImageView foreGroundNode){

        // Set the volume of the media players
        gunShotMusic.setVolume(DuckHunt.VOLUME);
        gameOverMusic.setVolume(DuckHunt.VOLUME);
        duckFallsMusic.setVolume(DuckHunt.VOLUME);
        levelCompletedMusic.setVolume(DuckHunt.VOLUME);

        // Create the selectionPane and FirstLevel
        Pane selectionPane = new Pane();
        selectionPane.getChildren().add(selectedBackground);
        Scene GameScene = new Scene(selectionPane);

        // Create and configure labels for level and ammo text
        Label levelText = new Label("Level: 1/6");
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        levelText.setTextFill(Color.ORANGE);
        Label ammoText = new Label("Ammo Left: "+ ammoLeft.toString());
        ammoText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        ammoText.setTextFill(Color.ORANGE);
        selectionPane.getChildren().addAll(ammoText,levelText);
        ammoText.setLayoutX(DuckHunt.Width - DuckHunt.SCALE *10*7);
        ammoText.setLayoutY(0);
        levelText.setLayoutX(DuckHunt.Width/2 - DuckHunt.SCALE *35);
        levelText.setLayoutY(0);


        // Event handler for cursor movement
        EventHandler<MouseEvent> cursorHandler = event -> {
            // Get the scene coordinates and dimensions
            double sceneX = event.getSceneX()+(selectedCursor.getImage().getWidth());
            double sceneY = event.getSceneY()+(selectedCursor.getImage().getHeight());
            double sceneWidth = GameScene.getWidth();
            double sceneHeight = GameScene.getHeight();

            // Check if the cursor is within the scene boundaries
            if (sceneX <= 0 || sceneX >= sceneWidth || sceneY <= 0 || sceneY >= sceneHeight) {
                GameScene.setCursor(Cursor.DEFAULT);
            } else {
                GameScene.setCursor(selectedCursor);
            }
        };
        selectionPane.setOnMouseMoved(cursorHandler);

        GameScene.setCursor(selectedCursor);

        // Create an ArrayList to store duck movement images
        ArrayList<Image> duckMovements = new ArrayList<>();

        // Load duck images and store their dimensions
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_red\\" + (i + 4) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            double heightDuck = duck.getHeight();
            double widthDuck = duck.getWidth();
            heightDucks = heightDuck;
            widthDucks = widthDuck;
            duckMovements.add(duck);
        }

        // Create a duckNode ImageView and set its initial properties
        ImageView duckNode = new ImageView(duckMovements.get(0));
        duckNode.setFitHeight(DuckHunt.SCALE * heightDucks);
        duckNode.setFitWidth(DuckHunt.SCALE * widthDucks);
        selectionPane.getChildren().add(duckNode);

        // Event handler for flying movement animation
        EventHandler<ActionEvent> flyingMovement = e -> {
            if (movementNumber == 2){
                movementNumber=0;
            }else {
                movementNumber+=1;
            }
            duckNode.setImage(duckMovements.get(movementNumber));
            duckNode.setFitHeight(DuckHunt.SCALE * heightDucks);
            duckNode.setFitWidth(DuckHunt.SCALE * widthDucks);

        };

        // Create the flying animation timeline
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(250), flyingMovement));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Set initial layout and animation for duckNode
        duckNode.setLayoutY(DuckHunt.Width/3);
        imageSpeedX =DuckHunt.SCALE;
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                imageX += imageSpeedX;
                if (Math.round(imageX)< 0 || Math.round(imageX + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedX= -imageSpeedX;
                    duckNode.setScaleX(-duckNode.getScaleX());
                }
                duckNode.setLayoutX(imageX);
            }
        };
        animationTimer.start();

        // Event handler for mouse click on duckNode
        selectionPane.setOnMouseClicked(e->{
            gunShotMusic.stop();
            gunShotMusic.play();
            Bounds boundsInScene = duckNode.localToScene(duckNode.getBoundsInLocal());
            if (ammoLeft>0){
                ammoLeft-=1;
                selectionPane.getChildren().remove(ammoText);
                ammoText.setText("Ammo Left: " + ammoLeft.toString());
                selectionPane.getChildren().add(ammoText);

                //if the duck is shot
                if (e.getX() < boundsInScene.getMaxX() && e.getX() > boundsInScene.getMinX() && e.getY() < boundsInScene.getMaxY() && e.getY() > boundsInScene.getMinY()) {
                    selectionPane.setMouseTransparent(true);
                    animation.stop();
                    animationTimer.stop();
                    StackPane stackPane = new StackPane();
                    VBox winnerBox = winnerText();
                    stackPane.getChildren().add(winnerBox);
                    stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                    stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                    selectionPane.getChildren().add(stackPane);

                    //animation of the moment of the shooting
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNode.setImage(new Image(new File("assets\\duck_red\\7.png").toURI().toString()));
                    if (duckNode.getScaleY()<0){duckNode.setScaleY(-duckNode.getScaleY());}
                    duckNode.setFitHeight(DuckHunt.SCALE * heightDucks);
                    duckNode.setFitWidth(DuckHunt.SCALE *  widthDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                    shot.play();

                    levelCompletedMusic.play();
                    duckFallsMusic.play();

                    //animation of the moment of falling
                    shot.setOnFinished(f->{
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNode);
                        duckNode.setImage(new Image(new File("assets\\duck_red\\8.png").toURI().toString()));
                        if (duckNode.getScaleY()<0){duckNode.setScaleY(-duckNode.getScaleY());}
                        duckNode.setFitHeight(DuckHunt.SCALE * widthDucks);
                        duckNode.setFitWidth(DuckHunt.SCALE *  heightDucks);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                    });

                    //the status of the user entering input from the keyboard
                    selectionPane.setOnKeyPressed(keyEvent->{
                        duckFallsMusic.stop();
                        gunShotMusic.stop();
                        levelCompletedMusic.stop();
                        if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                            primaryStage.setScene(new LevelTwo().getLevelTwoScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                        }
                    });
                    selectionPane.requestFocus();
                }
            }
            //if game is over
            if (ammoLeft==0) {
                gameOverMusic.play();
                selectionPane.setMouseTransparent(true);
                // Create a stack pane to display the game over message
                StackPane stackPane = new StackPane();
                VBox gameOverBox = gameOverText();
                stackPane.getChildren().add(gameOverBox);

                // Position the stack pane at the center of the selection pane
                stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(gameOverBox.widthProperty()).divide(2));
                stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(gameOverBox.heightProperty()).divide(2));

                // Add the stack pane to the selection pane
                selectionPane.getChildren().add(stackPane);

                // Disable mouse interaction with the selection pane and give it focus
                selectionPane.setMouseTransparent(true);
                selectionPane.requestFocus();

                // Handle key events for restarting the game or returning to the intro screen
                selectionPane.setOnKeyPressed(keyEvent -> {
                    duckFallsMusic.stop();
                    gameOverMusic.stop();
                    gunShotMusic.stop();
                    switch (keyEvent.getCode()) {
                        case ENTER:
                            primaryStage.setScene(new FirstLevel().getLevelOneScene(primaryStage, selectedCursor, selectedBackground, countForBackGround,foreGroundNode));
                            break;
                        case ESCAPE:
                            primaryStage.setScene(new IntroScreen().getScene(primaryStage));
                            break;
                    }
                });
            }
        });

        foreGroundNode.setMouseTransparent(false);
        selectionPane.getChildren().add(foreGroundNode);
        foreGroundNode.toFront();
        duckNode.requestFocus();

        return GameScene;
    }

    /**
     * Generates a VBox containing the "GAME OVER" message and instructions for playing again or exiting.
     * The text is styled with a bold Arial font and orange color.
     * The instructions prompt the player to press ENTER to play again or ESC to exit.
     * The VBox is centered within its parent container.
     * @return The VBox containing the "GAME OVER" message and instructions.
     */
    public VBox gameOverText(){
        Text text1 = new Text("GAME OVER");
        Text text2 = new Text("Press ENTER to play again");
        Text text3 = new Text("Press ESC to exit");
        text1.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text1.setFill(Color.ORANGE);
        text2.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text2.setFill(Color.ORANGE);
        text3.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text3.setFill(Color.ORANGE);
        VBox vbox= new VBox(text1,text2,text3);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    /**
     * Generates a VBox containing the "YOU WIN!" message and instructions for the next level.
     * The text is styled with a bold Arial font and orange color.
     * The instructions prompt the player to press ENTER to play the next level.
     * The VBox is centered within its parent container.
     * @return The VBox containing the "YOU WIN!" message and instructions.
     */
    public VBox winnerText(){
        Text text1 = new Text("YOU WIN!");
        Text text2 = new Text("Press ENTER to play next level");
        text1.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text1.setFill(Color.ORANGE);
        text2.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *15));
        text2.setFill(Color.ORANGE);
        VBox vbox= new VBox(text1,text2);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
