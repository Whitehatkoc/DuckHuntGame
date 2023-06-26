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

public class LevelFour {
    private Integer ammoLeft = 6;
    private  Integer movementNumber =0;
    public Double heightDucks ;
    public Double widthDucks;
    private  double imageXBlack ;
    private  double imageYBlack ;
    private  double imageYRed ;
    private  double imageXRed ;
    private double imageSpeedXBlack;
    private double imageSpeedYBlack;
    private double imageSpeedXRed;

    private double imageSpeedYRed;
    private final ArrayList<ImageView> duckList = new ArrayList<>(); // list of birds in the game that were not shot

    /**
     * Returns the scene for Level 4 of the game.
     *
     * @param primaryStage     the primary stage of the game
     * @param selectedCursor   the selected cursor image
     * @param selectedBackground the selected background image
     * @param countForBackGround the count for background
     * @param foreGroundNode   the foreground node
     * @return the scene for Level 4
     */
    public Scene getLevelFourScene(Stage primaryStage, ImageCursor selectedCursor, ImageView selectedBackground, Integer countForBackGround, ImageView foreGroundNode)
    {
        //prepare pane
        Pane selectionPane = new Pane();
        selectionPane.getChildren().add(selectedBackground);
        Scene levelFourScene = new Scene(selectionPane);

        // prepare text on the pane
        Label levelText = new Label("Level: 4/6");
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


        // handle mouse event about cursor
        EventHandler<MouseEvent> cursorHandler = event -> {

            double sceneX = event.getSceneX()+(selectedCursor.getImage().getWidth());
            double sceneY = event.getSceneY()+(selectedCursor.getImage().getHeight());
            double sceneWidth = levelFourScene.getWidth();
            double sceneHeight = levelFourScene.getHeight();

            // check boundaries of scene
            if (sceneX <= 0 || sceneX >= sceneWidth || sceneY <= 0 || sceneY >= sceneHeight) {
                levelFourScene.setCursor(Cursor.DEFAULT);
            } else {
                levelFourScene.setCursor(selectedCursor);
            }
        };
        selectionPane.setOnMouseMoved(cursorHandler);

        levelFourScene.setCursor(selectedCursor);

        // prepare red bird
        ArrayList<Image> duckMovementsForRed = new ArrayList<>(); // the part where the images of the bird's flapping wings are stored
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_red\\" + (i+1) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            double heightDuck = duck.getHeight();
            double widthDuck = duck.getWidth();
            heightDucks = heightDuck;
            widthDucks = widthDuck;
            duckMovementsForRed.add(duck);
        }

        //the image of the bird is being sized
        ImageView duckNodeRed = new ImageView(duckMovementsForRed.get(0));
        duckNodeRed.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeRed.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeRed);

        selectionPane.getChildren().add(duckNodeRed);

        //the animation of the bird's flapping wings is being prepared
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

        Timeline animationRed = new Timeline(new KeyFrame(Duration.millis(250), flyingMovementRed));
        animationRed.setCycleCount(Timeline.INDEFINITE);
        animationRed.play(); // Start animation

        // prepare black bird
        ArrayList<Image> duckMovementsForBlack = new ArrayList<>(); // the part where the images of the bird's flapping wings are stored
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_black\\" + (i+1) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            duckMovementsForBlack.add(duck);
        }
        //the image of the bird is being sized
        ImageView duckNodeBlack = new ImageView(duckMovementsForBlack.get(0));
        duckNodeBlack.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeBlack.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeBlack);
        selectionPane.getChildren().add(duckNodeBlack);

        //the animation of the bird's flapping wings is being prepared
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
        Timeline animationBlack = new Timeline(new KeyFrame(Duration.millis(250), flyingMovementBlack));
        animationBlack.setCycleCount(Timeline.INDEFINITE);
        animationBlack.play(); // Start animation

        // movement of red bird, cross-flight movement
        int imageAngleRed = 30;
        imageSpeedXRed =DuckHunt.SCALE *2* Math.cos(Math.toRadians(imageAngleRed));
        imageSpeedYRed = -(DuckHunt.SCALE) *2* Math.sin(Math.toRadians(imageAngleRed));
        AnimationTimer animationTimerRed = new AnimationTimer() {
            @Override
            public void handle(long now) {

                imageXRed += imageSpeedXRed;
                imageYRed += imageSpeedYRed;

                if (Math.round(imageXRed)< 0 || Math.round(imageXRed + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedXRed = -imageSpeedXRed;
                    duckNodeRed.setScaleX(-duckNodeRed.getScaleX());

                }
                if (Math.round(imageYRed)< 0 || Math.round(imageYRed) + heightDucks*DuckHunt.SCALE > DuckHunt.Height) {
                    imageSpeedYRed = -imageSpeedYRed;
                    duckNodeRed.setScaleY(-duckNodeRed.getScaleY());
                }
                duckNodeRed.setTranslateX(imageXRed);
                duckNodeRed.setTranslateY(imageYRed);

            }
        }; animationTimerRed.start();

        // movement of black bird, cross-flight movement
        int imageAngleBlack = 40;
        imageYBlack = DuckHunt.SCALE *30;

        imageSpeedXBlack =DuckHunt.SCALE * 2 *Math.cos(Math.toRadians(imageAngleBlack));
        imageSpeedYBlack = -(DuckHunt.SCALE) * 2*Math.sin(Math.toRadians(imageAngleBlack));
        AnimationTimer animationTimerBlack = new AnimationTimer() {
            @Override
            public void handle(long now) {

                imageXBlack += imageSpeedXBlack;
                imageYBlack += imageSpeedYBlack;

                if (Math.round(imageXBlack)< 0 || Math.round(imageXBlack + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedXBlack = -imageSpeedXBlack;
                    duckNodeBlack.setScaleX(-duckNodeBlack.getScaleX());

                }
                if (Math.round(imageYBlack)< 0 || Math.round(imageYBlack) + heightDucks*DuckHunt.SCALE > DuckHunt.Height) {
                    imageSpeedYBlack = -imageSpeedYBlack;
                    duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());

                }

                duckNodeBlack.setTranslateX(imageXBlack);
                duckNodeBlack.setTranslateY(imageYBlack);

            }
        };
        animationTimerBlack.start();


        //handling the mouse clicking event
        selectionPane.setOnMouseClicked(e->{
            FirstLevel.gunShotMusic.stop();
            FirstLevel.gunShotMusic.play();
            Bounds boundsInSceneBlack = duckNodeBlack.localToScene(duckNodeBlack.getBoundsInLocal());
            Bounds boundsInSceneRed = duckNodeRed.localToScene(duckNodeRed.getBoundsInLocal());

            //if the game is not over (if the bullets are not exhausted)
            if (ammoLeft>0){
                ammoLeft-=1;
                selectionPane.getChildren().remove(ammoText);
                ammoText.setText("Ammo Left: " + ammoLeft.toString());
                selectionPane.getChildren().add(ammoText);

                //if the black bird is shot
                if (e.getX() < boundsInSceneBlack.getMaxX() && e.getX() > boundsInSceneBlack.getMinX() && e.getY() < boundsInSceneBlack.getMaxY() && e.getY() > boundsInSceneBlack.getMinY()) {
                    animationBlack.stop();
                    animationTimerBlack.stop();
                    duckList.remove(duckNodeBlack);

                    //animation of the moment the bird was shot is being created
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\7.png").toURI().toString()));
                    if (duckNodeBlack.getScaleY()<0){duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());}
                    duckNodeBlack.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeBlack.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(450),shotHandler));
                    shot.play();

                    //the end of the animation of the moment of the shooting
                    shot.setOnFinished(fall->{
                        // animation of the moment of falling
                        duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\8.png").toURI().toString()));
                        if (duckNodeBlack.getScaleY()<0){duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());}
                        duckNodeBlack.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNodeBlack.setFitWidth(DuckHunt.SCALE *  widthDucks);
                        FirstLevel.duckFallsMusic.play();
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeBlack);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                    });

                    //if the game ends when the black bird is shot
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

                            //handling user inputs at the end of the game
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                primaryStage.setScene(new LevelFive().getLevelFiveScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            }
                        });
                        selectionPane.requestFocus();
                    }
                }

                // if the red bird is shot
                if (e.getX() < boundsInSceneRed.getMaxX() && e.getX() > boundsInSceneRed.getMinX() && e.getY() < boundsInSceneRed.getMaxY() && e.getY() > boundsInSceneRed.getMinY()) {
                    animationRed.stop();
                    animationTimerRed.stop();
                    duckList.remove(duckNodeRed);

                    //animation of the moment the bird was shot is being created
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeRed.setImage(new Image(new File("assets\\duck_red\\7.png").toURI().toString()));
                    if (duckNodeRed.getScaleY()<0){duckNodeRed.setScaleY(-duckNodeRed.getScaleY());}
                    duckNodeRed.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeRed.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shoot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                    shoot.play();

                    //the end of the animation of the moment of the shooting
                    shoot.setOnFinished(fall->{
                        // animation of the moment of falling
                        shoot.stop();
                        duckNodeRed.setImage(new Image(new File("assets\\duck_red\\8.png").toURI().toString()));
                        if (duckNodeRed.getScaleY()<0){duckNodeRed.setScaleY(-duckNodeRed.getScaleY());}
                        duckNodeRed.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNodeRed.setFitWidth(DuckHunt.SCALE *  widthDucks);
                        FirstLevel.duckFallsMusic.play();
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeRed);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
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
                            //handling user inputs at the end of the game
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                primaryStage.setScene(new LevelFive().getLevelFiveScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
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

                //game over screen texts are prepared
                StackPane stackPane = new StackPane();
                VBox gameOverBox = new LevelTwo().gameOverText();
                stackPane.getChildren().add(gameOverBox);
                stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(gameOverBox.widthProperty()).divide(2));
                stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(gameOverBox.heightProperty()).divide(2));
                selectionPane.getChildren().add(stackPane);

                selectionPane.setMouseTransparent(true);//mouse inputs are ignored
                selectionPane.requestFocus();

                selectionPane.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER:
                            FirstLevel.gameOverMusic.stop();
                            FirstLevel.gunShotMusic.stop();
                            FirstLevel.duckFallsMusic.stop();
                            primaryStage.setScene(new LevelFour().getLevelFourScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            break;
                        case ESCAPE:
                            FirstLevel.gameOverMusic.stop();
                            FirstLevel.gunShotMusic.stop();
                            FirstLevel.duckFallsMusic.stop();
                            primaryStage.setScene(new IntroScreen().getScene(primaryStage));
                            break;
                    }
                    selectionPane.requestFocus();
                });
            }
        });
        // add the foreground
        selectionPane.getChildren().add(foreGroundNode);
        foreGroundNode.toFront();

        return levelFourScene;
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
