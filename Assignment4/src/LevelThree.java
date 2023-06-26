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

public class LevelThree {
    public Integer ammoLeft = 6;
    private Integer movementNumber = 0;
    public Double heightDucks ;
    public Double widthDucks;
    private  double imageXBlack ;
    private  double imageXRed ;
    private double imageSpeedXBlack;
    private double imageSpeedXRed;
    private final ArrayList<ImageView> duckList = new ArrayList<>(); // list of birds in the game that were not shot

    /**
     * Returns the scene for Level 3 of the game.
     *
     * @param primaryStage     the primary stage of the game
     * @param selectedCursor   the selected cursor image
     * @param selectedBackground the selected background image
     * @param countForBackGround the count for background
     * @param foreGroundNode   the foreground node
     * @return the scene for Level 3
     */
    public Scene getLevelThreeScene(Stage primaryStage, ImageCursor selectedCursor, ImageView selectedBackground, Integer countForBackGround,ImageView foreGroundNode){
        Pane selectionPane = new Pane();

        // Add selected background to the selection pane
        selectionPane.getChildren().add(selectedBackground);
        Scene levelThreeScene = new Scene(selectionPane);

        // Prepare the pane
        Label levelText = new Label("Level: 3/6");
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        levelText.setTextFill(Color.ORANGE);
        Label ammoText = new Label("Ammo Left: "+ ammoLeft.toString());
        ammoText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        ammoText.setTextFill(Color.ORANGE);

        // Add level text and ammo text to the selection pane
        selectionPane.getChildren().addAll(ammoText,levelText);
        ammoText.setLayoutX(DuckHunt.Width - DuckHunt.SCALE *10*7); // Position ammo text in the top right corner of the screen
        ammoText.setLayoutY(0);
        levelText.setLayoutX(DuckHunt.Width/2 - DuckHunt.SCALE *35); // Position level text in the top center of the screen
        levelText.setLayoutY(0);


        EventHandler<MouseEvent> cursorHandler = event -> {

            double sceneX = event.getSceneX()+(selectedCursor.getImage().getWidth());
            double sceneY = event.getSceneY()+(selectedCursor.getImage().getHeight());
            double sceneWidth = levelThreeScene.getWidth();
            double sceneHeight = levelThreeScene.getHeight();

            // Check scene boundaries
            if (sceneX <= 0 || sceneX >= sceneWidth || sceneY <= 0 || sceneY >= sceneHeight) {
                levelThreeScene.setCursor(Cursor.DEFAULT);
            } else {
                levelThreeScene.setCursor(selectedCursor);
            }
        };
        // Set cursor handler for mouse movement on the selection pane
        selectionPane.setOnMouseMoved(cursorHandler);

        levelThreeScene.setCursor(selectedCursor);

        // Prepare red duck

        //the list that provides the animation of the flapping of the bird's wings
        ArrayList<Image> duckMovementsForRed = new ArrayList<>();// List to store red duck images
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_red\\" + (i+4) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            double heightDuck = duck.getHeight();
            double widthDuck = duck.getWidth();
            heightDucks = heightDuck;
            widthDucks = widthDuck;
            duckMovementsForRed.add(duck); // Add red duck image to the list
        }
        ImageView duckNodeRed = new ImageView(duckMovementsForRed.get(0)); // ImageView for red duck using the first image
        duckNodeRed.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeRed.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeRed);

        selectionPane.getChildren().add(duckNodeRed);

        // Animation and movement for red duck
        EventHandler<ActionEvent> flyingMovementRed = e -> {
            if (movementNumber == 2){
                movementNumber=0;
            }else {
                movementNumber+=1;
            }
            duckNodeRed.setImage(duckMovementsForRed.get(movementNumber));
            duckNodeRed.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
            duckNodeRed.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        };
        //animation of a bird flapping its wings
        Timeline animationRed = new Timeline(new KeyFrame(Duration.millis(250), flyingMovementRed));
        animationRed.setCycleCount(Timeline.INDEFINITE);
        animationRed.play(); // Start animation for red duck

        //prepare black bird

        //the list that provides the animation of the flapping of the bird's wings
        ArrayList<Image> duckMovementsForBlack = new ArrayList<>();
        //add wing images of the bird
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_black\\" + (i+4) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            duckMovementsForBlack.add(duck);
        }
        ImageView duckNodeBlack = new ImageView(duckMovementsForBlack.get(0));
        duckNodeBlack.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeBlack.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeBlack);

        selectionPane.getChildren().add(duckNodeBlack);

        // Animation and movement for black duck
        EventHandler<ActionEvent> flyingMovementBlack = e -> {
            if (movementNumber == 2){
                movementNumber=0;
            }else {
                movementNumber+=1;
            }
            duckNodeBlack.setImage(duckMovementsForBlack.get(movementNumber));
            duckNodeBlack.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
            duckNodeBlack.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);

        };
        //animation of a bird flapping its wings
        Timeline animationBlack = new Timeline(new KeyFrame(Duration.millis(250), flyingMovementBlack));
        animationBlack.setCycleCount(Timeline.INDEFINITE);
        animationBlack.play(); // Start animation

        // the movement of our cricket bird
        imageXRed=DuckHunt.SCALE *20;
        duckNodeRed.setLayoutY(DuckHunt.Width/5);
        imageSpeedXRed =DuckHunt.SCALE *2;
        AnimationTimer animationTimerRed = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // update movement
                imageXRed += imageSpeedXRed;
                // Check the reflection when it hits the windowsills
                if (Math.round(imageXRed)< 0 || Math.round(imageXRed + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedXRed = -imageSpeedXRed; // Reverse the X direction
                    duckNodeRed.setScaleX(-duckNodeRed.getScaleX()); // Rotate the image horizontally
                }
                // update location
                duckNodeRed.setLayoutX(imageXRed);
            }
        };
        animationTimerRed.start();

        // movement of black bird
        imageSpeedXBlack = DuckHunt.SCALE;
        duckNodeBlack.setLayoutY(DuckHunt.Width/3.0);
        AnimationTimer animationTimerBlack = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // update movement
                imageXBlack += imageSpeedXBlack;
                // Check the reflection when it hits the windowsills
                if (Math.round(imageXBlack)< 0 || Math.round(imageXBlack + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedXBlack = -imageSpeedXBlack; // Reverse the X direction
                    duckNodeBlack.setScaleX(-duckNodeBlack.getScaleX()); // Rotate the image horizontally
                }
                // update location
                duckNodeBlack.setTranslateX(imageXBlack);
            }
        };
        animationTimerBlack.start();


        // shooting or not shooting birds
        selectionPane.setOnMouseClicked(e->{
            FirstLevel.gunShotMusic.stop();
            FirstLevel.gunShotMusic.play();
            // finding the position of birds on the screen
            Bounds boundsInSceneBlack = duckNodeBlack.localToScene(duckNodeBlack.getBoundsInLocal());
            Bounds boundsInSceneRed = duckNodeRed.localToScene(duckNodeRed.getBoundsInLocal());
            FirstLevel.gunShotMusic.play();

            //if the game is not lost
            if (ammoLeft>0){
                ammoLeft-=1;
                selectionPane.getChildren().remove(ammoText);
                ammoText.setText("Ammo Left: " + ammoLeft.toString());
                selectionPane.getChildren().add(ammoText);

                // the incident of the shooting of the black bird
                if (e.getX() < boundsInSceneBlack.getMaxX() && e.getX() > boundsInSceneBlack.getMinX() && e.getY() < boundsInSceneBlack.getMaxY() && e.getY() > boundsInSceneBlack.getMinY()) {
                    animationBlack.stop();
                    animationTimerBlack.stop();
                    duckList.remove(duckNodeBlack);

                    // an image of the moment the black bird was shot
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\7.png").toURI().toString()));
                    if (duckNodeBlack.getScaleY()<0){duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());}
                    duckNodeBlack.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeBlack.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(450),shotHandler));
                    shot.play();

                    //the end of the animation of the moment of impact and the beginning of the animation of the fall
                    shot.setOnFinished(f-> {

                        //prepare a fall image
                        duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\8.png").toURI().toString()));
                        if (duckNodeBlack.getScaleY() < 0) {
                            duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());
                        }
                        duckNodeBlack.setFitHeight(DuckHunt.SCALE * widthDucks);
                        duckNodeBlack.setFitWidth(DuckHunt.SCALE * heightDucks);
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeBlack);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                        FirstLevel.duckFallsMusic.play();
                    });

                    // if the game ends when the black bird is shot
                    if (duckList.size()==0){
                        selectionPane.setMouseTransparent(true);
                        FirstLevel.levelCompletedMusic.play();
                        StackPane stackPane = new StackPane();
                        VBox winnerBox = winnerText();
                        stackPane.getChildren().add(winnerBox);
                        stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                        stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                        selectionPane.getChildren().add(stackPane);
                        selectionPane.setOnKeyPressed(keyEvent->{

                            //handling user inputs
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                //all music stops
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                // the next level opens
                                primaryStage.setScene(new LevelFour().getLevelFourScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            }
                        });
                        selectionPane.requestFocus();
                    }

                }

                //if the red bird is shot
                if (e.getX() < boundsInSceneRed.getMaxX() && e.getX() > boundsInSceneRed.getMinX() && e.getY() < boundsInSceneRed.getMaxY() && e.getY() > boundsInSceneRed.getMinY()) {
                    animationRed.stop();
                    animationTimerRed.stop();
                    duckList.remove(duckNodeRed);

                    // an image of the moment the red bird was shot
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeRed.setImage(new Image(new File("assets\\duck_red\\7.png").toURI().toString()));
                    if (duckNodeRed.getScaleY()<0){duckNodeRed.setScaleY(-duckNodeRed.getScaleY());}
                    duckNodeRed.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeRed.setFitWidth(DuckHunt.SCALE *  heightDucks);

                    //playing the animation of the moment of the shooting for a while
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                    shot.play();

                    //the end of the shooting moment animation and the beginning of the fall animation
                    shot.setOnFinished(f-> {
                        FirstLevel.duckFallsMusic.play();

                        // preparing the image
                        duckNodeRed.setImage(new Image(new File("assets\\duck_red\\8.png").toURI().toString()));
                        if (duckNodeRed.getScaleY() < 0) {
                            duckNodeRed.setScaleY(-duckNodeRed.getScaleY());
                        }
                        duckNodeRed.setFitHeight(DuckHunt.SCALE * widthDucks);
                        duckNodeRed.setFitWidth(DuckHunt.SCALE * heightDucks);

                        // falling animation
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeRed);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                        FirstLevel.duckFallsMusic.play();
                    });

                    //if the game ends when the red bird is shot
                    if (duckList.size()==0){
                        selectionPane.setMouseTransparent(true);
                        FirstLevel.levelCompletedMusic.play();
                        StackPane stackPane = new StackPane();
                        VBox winnerBox = winnerText();
                        stackPane.getChildren().add(winnerBox);
                        stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                        stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                        selectionPane.getChildren().add(stackPane);
                        selectionPane.setOnKeyPressed(keyEvent->{

                            //checking user inputs at the end of the game
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                //all music stops
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                // the next level opens
                                primaryStage.setScene(new LevelFour().getLevelFourScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            }
                        });
                        selectionPane.requestFocus();
                    }

                }
            }

            // if game is over
            if (ammoLeft==0 && duckList.size()!=0) {
                selectionPane.setMouseTransparent(true);
                FirstLevel.gameOverMusic.play();
                StackPane stackPane = new StackPane();
                VBox gameOverBox = new LevelTwo().gameOverText();
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
                            primaryStage.setScene(new LevelThree().getLevelThreeScene(primaryStage, selectedCursor, selectedBackground, countForBackGround,foreGroundNode));
                            break;
                        case ESCAPE:
                            primaryStage.setScene(new IntroScreen().getScene(primaryStage));
                            break;
                    }
                    selectionPane.requestFocus();
                });
            }


        });
        selectionPane.requestFocus();
        foreGroundNode.setMouseTransparent(false);
        selectionPane.getChildren().add(foreGroundNode);
        foreGroundNode.toFront();

        return levelThreeScene;
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
