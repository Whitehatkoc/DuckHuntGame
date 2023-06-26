import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
 * Represents the Level Two of the game.
 */
public class LevelTwo {
    public Integer ammoLeft =3;
    private Integer movementNumber = 0;
    public Double heightDucks ;
    public Double widthDucks;
    private  double imageX ;
    private  double imageY ;
    private double imageSpeedX;
    private double imageSpeedY;

    /**
     * Returns the Level Two scene with the specified parameters.
     *
     * @param primaryStage   the primary stage of the application
     * @param selectedCursor the selected cursor image
     * @param selectedBackground the selected background image
     * @param countForBackground the count for background
     * @param foregroundNode the foreground node
     * @return the Level Two scene
     */
    public Scene getLevelTwoScene(Stage primaryStage, ImageCursor selectedCursor, ImageView selectedBackground, Integer countForBackground,ImageView foregroundNode){
        Pane selectionPane = new Pane();

        // Add selected background to selection pane
        selectionPane.getChildren().add(selectedBackground);
        Scene levelTwoScene = new Scene(selectionPane);

        // Prepare the labels for level and ammo left
        Label levelText = new Label("Level: 2/6");
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        levelText.setTextFill(Color.ORANGE);
        Label ammoText = new Label("Ammo Left: "+ ammoLeft.toString());
        ammoText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        ammoText.setTextFill(Color.ORANGE);

        // Add labels to selection pane and set their positions
        selectionPane.getChildren().addAll(ammoText,levelText);
        ammoText.setLayoutX(DuckHunt.Width - DuckHunt.SCALE *10*7);
        ammoText.setLayoutY(0);
        levelText.setLayoutX(DuckHunt.Width/2 - DuckHunt.SCALE *35);
        levelText.setLayoutY(0);

        // Cursor handling
        EventHandler<MouseEvent> cursorHandler = event -> {

            double sceneX = event.getSceneX()+(selectedCursor.getImage().getWidth());
            double sceneY = event.getSceneY()+(selectedCursor.getImage().getHeight());
            double sceneWidth = levelTwoScene.getWidth();
            double sceneHeight = levelTwoScene.getHeight();

            // Check scene boundaries
            if (sceneX <= 0 || sceneX >= sceneWidth || sceneY <= 0 || sceneY >= sceneHeight) {
                levelTwoScene.setCursor(Cursor.DEFAULT);
            } else {
                levelTwoScene.setCursor(selectedCursor);
            }
        };
        selectionPane.setOnMouseMoved(cursorHandler);
        levelTwoScene.setCursor(selectedCursor);


        // Prepare the duck images
        ArrayList<Image> duckMovements = new ArrayList<>();

        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_blue\\" + (i+1) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            double heightDuck = duck.getHeight();
            double widthDuck = duck.getWidth();
            heightDucks = heightDuck;
            widthDucks = widthDuck;
            duckMovements.add(duck);
        }
        ImageView duckNode = new ImageView(duckMovements.get(0));
        duckNode.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNode.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        selectionPane.getChildren().add(duckNode);

        // Duck animation
        EventHandler<ActionEvent> flyingMovement = e -> {
            if (movementNumber == 2){
                movementNumber=0;
            }else {
                movementNumber+=1;
            }
            duckNode.setImage(duckMovements.get(movementNumber));
            duckNode.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
            duckNode.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);

        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(250), flyingMovement));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        // Duck movement
        int imageAngle = 40;
        imageSpeedX =DuckHunt.SCALE * Math.cos(Math.toRadians(imageAngle));
        imageSpeedY = -(DuckHunt.SCALE) * Math.sin(Math.toRadians(imageAngle));
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                imageX += imageSpeedX;
                imageY += imageSpeedY;

                if (Math.round(imageX)< 0 || Math.round(imageX + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedX = -imageSpeedX;
                    duckNode.setScaleX(-duckNode.getScaleX());

                }
                if (Math.round(imageY)< 0 || Math.round(imageY) + heightDucks*DuckHunt.SCALE > DuckHunt.Height) {
                    imageSpeedY = -imageSpeedY;
                    duckNode.setScaleY(-duckNode.getScaleY());

                }
                duckNode.setTranslateX(imageX);
                duckNode.setTranslateY(imageY);

            }
        };

        // Duck shot event
        selectionPane.setOnMouseClicked(e->{
            FirstLevel.gunShotMusic.stop();
            FirstLevel.gunShotMusic.play();
            Bounds boundsInScene = duckNode.localToScene(duckNode.getBoundsInLocal());
            if (ammoLeft>0){
                ammoLeft-=1;
                selectionPane.getChildren().remove(ammoText);
                ammoText.setText("Ammo Left: " + ammoLeft.toString());
                selectionPane.getChildren().add(ammoText);
                FirstLevel.duckFallsMusic.play();

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
                        duckNode.setImage(new Image(new File("assets\\duck_blue\\7.png").toURI().toString()));
                        if (duckNode.getScaleY()<0){duckNode.setScaleY(-duckNode.getScaleY());}
                        duckNode.setFitHeight(DuckHunt.SCALE * widthDucks);
                        duckNode.setFitWidth(DuckHunt.SCALE *  heightDucks);
                        Timeline shot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                        shot.play();
                        FirstLevel.levelCompletedMusic.play();

                        //animation of the moment of falling
                        shot.setOnFinished(f->{
                        duckNode.setImage(new Image(new File("assets\\duck_blue\\8.png").toURI().toString()));
                        if (duckNode.getScaleY()<0){duckNode.setScaleY(-duckNode.getScaleY());}
                        duckNode.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNode.setFitWidth(DuckHunt.SCALE *  widthDucks);

                        //the status of the user entering input from the keyboard
                        selectionPane.setOnKeyPressed(keyEvent->{
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                primaryStage.setScene(new LevelThree().getLevelThreeScene(primaryStage, selectedCursor, selectedBackground, countForBackground, foregroundNode));
                            }
                        });
                        selectionPane.requestFocus();

                        //animation of the moment of falling
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNode);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                    });}
                    }

            //if game is over
            if (ammoLeft==0) {
                selectionPane.setMouseTransparent(true);
                FirstLevel.gameOverMusic.play();
                StackPane stackPane = new StackPane();
                VBox gameOverBox = gameOverText();
                stackPane.getChildren().add(gameOverBox);
                stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(gameOverBox.widthProperty()).divide(2));
                stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(gameOverBox.heightProperty()).divide(2));
                selectionPane.getChildren().add(stackPane);

                selectionPane.setMouseTransparent(true);
                selectionPane.requestFocus();
                selectionPane.setOnKeyPressed(keyEvent -> {
                    FirstLevel.gameOverMusic.stop();
                    FirstLevel.duckFallsMusic.stop();
                    FirstLevel.gunShotMusic.stop();
                    switch (keyEvent.getCode()) {
                        case ENTER:
                            primaryStage.setScene(new LevelTwo().getLevelTwoScene(primaryStage, selectedCursor, selectedBackground, countForBackground,foregroundNode));
                            break;
                        case ESCAPE:
                            primaryStage.setScene(new IntroScreen().getScene(primaryStage));
                            break;
                    }
                });
            }
        });

        foregroundNode.setMouseTransparent(false);
        selectionPane.getChildren().add(foregroundNode);
        foregroundNode.toFront();
        animationTimer.start();

        return levelTwoScene;
    }

    /**
     * Returns a VBox containing the text for game over message.
     *
     * @return The VBox containing the game over message text.
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
     * Returns a VBox containing the text for winning message.
     *
     * @return The VBox containing the winning message text.
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
