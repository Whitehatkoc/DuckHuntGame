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

public class LevelFive {
    public Integer ammoLeft = 9;
    private Integer movementNumber = 0;
    public Double heightDucks ;
    public Double widthDucks;
    private  double imageXBlack ;
    private  double imageYBlack ;
    private  double imageYRed ;
    private  double imageXRed ;
    private  double imageXBlue ;
    private double imageSpeedXBlack;
    private double imageSpeedYBlack;
    private double imageSpeedXRed;
    private double imageSpeedYRed;
    private double imageSpeedXBlue;

    private final ArrayList<ImageView> duckList = new ArrayList<>(); // list of birds in the game that were not shot
    public Scene getLevelFiveScene(Stage primaryStage, ImageCursor selectedCursor, ImageView selectedBackground, Integer countForBackGround,ImageView foreGroundNode) {
       // prepare selected pane
        Pane selectionPane = new Pane();
        selectionPane.getChildren().add(selectedBackground);
        Scene levelFiveScene = new Scene(selectionPane);

        // prepare texts on the pane
        Label levelText = new Label("Level: 5/6");
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

        // the selected cursor is set to cursor
        EventHandler<MouseEvent> cursorHandler = event -> {

            double sceneX = event.getSceneX()+(selectedCursor.getImage().getWidth());
            double sceneY = event.getSceneY()+(selectedCursor.getImage().getHeight());
            double sceneWidth = levelFiveScene.getWidth();
            double sceneHeight = levelFiveScene.getHeight();

            // Check the scene boundaries
            if (sceneX <= 0 || sceneX >= sceneWidth || sceneY <= 0 || sceneY >= sceneHeight) {
                levelFiveScene.setCursor(Cursor.DEFAULT);
            } else {
                levelFiveScene.setCursor(selectedCursor);
            }
        };
        selectionPane.setOnMouseMoved(cursorHandler);

        levelFiveScene.setCursor(selectedCursor);

        // prepare red bird
        ArrayList<Image> duckMovementsForRed = new ArrayList<>();  // where the bird's wing movements are stored
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_red\\" + (i+1) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            double heightDuck = duck.getHeight();
            double widthDuck = duck.getWidth();
            heightDucks = heightDuck;
            widthDucks = widthDuck;
            duckMovementsForRed.add(duck);
        }
        ImageView duckNodeRed = new ImageView(duckMovementsForRed.get(0));
        duckNodeRed.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeRed.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeRed);
        selectionPane.getChildren().add(duckNodeRed);

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
        animationRed.play(); // Start animation

        // prepare black bird
        ArrayList<Image> duckMovementsForBlack = new ArrayList<>();  // where the bird's wing movements are stored
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_black\\" + (i+1) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            duckMovementsForBlack.add(duck);
        }

        ImageView duckNodeBlack = new ImageView(duckMovementsForBlack.get(0));
        //the image is being resized
        duckNodeBlack.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeBlack.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeBlack);

        selectionPane.getChildren().add(duckNodeBlack);

        //animation of a bird flapping its wings
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

        //starting the wing flapping animation
        Timeline animationBlack = new Timeline(new KeyFrame(Duration.millis(100), flyingMovementBlack));
        animationBlack.setCycleCount(Timeline.INDEFINITE);
        animationBlack.play();

        // prepare blue bird
        ArrayList<Image> duckMovementsForBlue= new ArrayList<>(); // where the bird's wing movements are stored
        for(int i =0; i<3;i++) {
            String uriForDuck = new File("assets\\duck_blue\\" + (i+4) + ".png").toURI().toString();
            Image duck = new Image(uriForDuck);
            duckMovementsForBlue.add(duck);
        }
        ImageView duckNodeBlue = new ImageView(duckMovementsForBlue.get(0));
        //the image is being resized
        duckNodeBlue.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
        duckNodeBlue.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);
        duckList.add(duckNodeBlue);
        selectionPane.getChildren().add(duckNodeBlue);

        //animation of a bird flapping its wings
        EventHandler<ActionEvent> flyingMovementBlue = e -> {
            if (movementNumber == 2){
                movementNumber=0;
            }else {
                movementNumber+=1;
            }
            duckNodeBlue.setImage(duckMovementsForBlue.get(movementNumber));
            duckNodeBlue.setFitHeight(DuckHunt.SCALE * FirstLevel.heightDucks);
            duckNodeBlue.setFitWidth(DuckHunt.SCALE * FirstLevel.widthDucks);

        };
        //starting the wing flapping animation
        Timeline animationBlue = new Timeline(new KeyFrame(Duration.millis(200), flyingMovementBlue));
        animationBlue.setCycleCount(Timeline.INDEFINITE);
        animationBlue.play(); // Start animation

        // movement of the red bird, cross-flying movement
        int imageAngleRed = 30;
        imageXRed = DuckHunt.Width-widthDucks*DuckHunt.SCALE;
        imageSpeedXRed =DuckHunt.SCALE *2* Math.cos(Math.toRadians(imageAngleRed));
        imageSpeedYRed = -(DuckHunt.SCALE) *2* Math.sin(Math.toRadians(imageAngleRed));
        AnimationTimer animationTimerRed = new AnimationTimer() {
            @Override
            public void handle(long now) {

                imageXRed += imageSpeedXRed;
                imageYRed += imageSpeedYRed;

                //control of hitting the window edges
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

        //movement of the black bird, cross-flying movement
        int imageAngleBlack = 40;
        imageSpeedXBlack =DuckHunt.SCALE * 2 *Math.cos(Math.toRadians(imageAngleBlack));
        imageSpeedYBlack = -(DuckHunt.SCALE) * 2*Math.sin(Math.toRadians(imageAngleBlack));
        AnimationTimer animationTimerBlack = new AnimationTimer() {
            @Override
            public void handle(long now) {

                imageXBlack += imageSpeedXBlack;
                imageYBlack += imageSpeedYBlack;

                //control of hitting the window edges
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

        // movement of the blue bird , horizontally flying
        imageXBlue=DuckHunt.SCALE *20;
        duckNodeBlue.setLayoutY(DuckHunt.Width/5);
        imageSpeedXBlue =DuckHunt.SCALE *2;
        AnimationTimer animationTimerBlue = new AnimationTimer() {
            @Override
            public void handle(long now) {
                imageXBlue += imageSpeedXBlue;
                if (Math.round(imageXBlue)< 0 || Math.round(imageXBlue + widthDucks*DuckHunt.SCALE) > DuckHunt.Width) {
                    imageSpeedXBlue = -imageSpeedXBlue;
                    duckNodeBlue.setScaleX(-duckNodeBlue.getScaleX());
                }
                duckNodeBlue.setLayoutX(imageXBlue);
            }
        };
        animationTimerBlue.start();

        // shooting or not shooting birds
        selectionPane.setOnMouseClicked(e->{
            FirstLevel.gunShotMusic.stop();
            FirstLevel.gunShotMusic.play();
            // finding the position of birds on the screen
            Bounds boundsInSceneBlack = duckNodeBlack.localToScene(duckNodeBlack.getBoundsInLocal());
            Bounds boundsInSceneRed = duckNodeRed.localToScene(duckNodeRed.getBoundsInLocal());
            Bounds boundsInSceneBlue= duckNodeBlue.localToScene(duckNodeBlue.getBoundsInLocal());

            // if the game is still going on
            if (ammoLeft>0){
                ammoLeft-=1;

                // changing the remaining amount of lead
                selectionPane.getChildren().remove(ammoText);
                ammoText.setText("Ammo Left: " + ammoLeft.toString());
                selectionPane.getChildren().add(ammoText);

                // if the black bird is shot
                if (e.getX() < boundsInSceneBlack.getMaxX() && e.getX() > boundsInSceneBlack.getMinX() && e.getY() < boundsInSceneBlack.getMaxY() && e.getY() > boundsInSceneBlack.getMinY()) {
                    animationBlack.stop();
                    animationTimerBlack.stop();
                    duckList.remove(duckNodeBlack);

                    // animation of the moment of the shooting
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\7.png").toURI().toString()));
                    if (duckNodeBlack.getScaleY()<0){duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());}
                    duckNodeBlack.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeBlack.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(450),shotHandler));
                    shot.play();
                    // when the shooting is over
                    shot.setOnFinished(fall->{
                        shot.stop();

                        duckNodeBlack.setImage(new Image(new File("assets\\duck_black\\8.png").toURI().toString()));
                        if (duckNodeBlack.getScaleY()<0){duckNodeBlack.setScaleY(-duckNodeBlack.getScaleY());}
                        duckNodeBlack.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNodeBlack.setFitWidth(DuckHunt.SCALE *  widthDucks);

                        FirstLevel.duckFallsMusic.stop();
                        FirstLevel.duckFallsMusic.play();
                        //fall animation
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeBlack);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                    });

                    // if the game ends when the black bird is shot
                    if (duckList.size()==0){
                        selectionPane.setMouseTransparent(true);
                        FirstLevel.levelCompletedMusic.play();
                        StackPane stackPane = new StackPane();
                        // winning informational texts
                        VBox winnerBox = winnerText();
                        stackPane.getChildren().add(winnerBox);
                        stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                        stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                        selectionPane.getChildren().add(stackPane);
                        //handling of the player's keyboard inputs in case of a win
                        selectionPane.setOnKeyPressed(keyEvent->{
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                //all music stops
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                //the next level opens
                                primaryStage.setScene(new LevelSix().getLevelSixScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            }
                        });
                        selectionPane.requestFocus();
                    }
                }
                // if the black bird is shot
                if (e.getX() < boundsInSceneRed.getMaxX() && e.getX() > boundsInSceneRed.getMinX() && e.getY() < boundsInSceneRed.getMaxY() && e.getY() > boundsInSceneRed.getMinY()) {
                    animationRed.stop();
                    animationTimerRed.stop();
                    duckList.remove(duckNodeRed);

                    // the moment of the shooting is animated
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeRed.setImage(new Image(new File("assets\\duck_red\\7.png").toURI().toString()));
                    if (duckNodeRed.getScaleY()<0){duckNodeRed.setScaleY(-duckNodeRed.getScaleY());}
                    duckNodeRed.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeRed.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                    shot.play();

                    // animation of falling when the moment of shooting is over
                    shot.setOnFinished(fall->{
                        shot.stop();
                        duckNodeRed.setImage(new Image(new File("assets\\duck_red\\8.png").toURI().toString()));
                        if (duckNodeRed.getScaleY()<0){duckNodeRed.setScaleY(-duckNodeRed.getScaleY());}
                        duckNodeRed.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNodeRed.setFitWidth(DuckHunt.SCALE *  widthDucks);
                        FirstLevel.duckFallsMusic.stop();
                        FirstLevel.duckFallsMusic.play();
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeRed);
                        falling.setToY(DuckHunt.Width);
                        falling.play();
                    });

                    // if the game ends when the red bird is shot
                    if (duckList.size()==0){
                        selectionPane.setMouseTransparent(true);
                        FirstLevel.levelCompletedMusic.play();
                        StackPane stackPane = new StackPane();
                        // winning text
                        VBox winnerBox = winnerText();
                        stackPane.getChildren().add(winnerBox);
                        stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                        stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                        selectionPane.getChildren().add(stackPane);

                        //handling the user input
                        selectionPane.setOnKeyPressed(keyEvent->{
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                //all music stops
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                //the next level opens
                                primaryStage.setScene(new LevelSix().getLevelSixScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
                            }
                        });
                        selectionPane.requestFocus();
                    }
                }

                // if the blue bird is shot
                if (e.getX() < boundsInSceneBlue.getMaxX() && e.getX() > boundsInSceneBlue.getMinX() && e.getY() < boundsInSceneBlue.getMaxY() && e.getY() > boundsInSceneBlue.getMinY()) {
                    animationBlue.stop();
                    animationTimerBlue.stop();
                    duckList.remove(duckNodeBlue);

                    // the moment of the shooting is animated
                    EventHandler<ActionEvent> shotHandler= shot->{};
                    duckNodeBlue.setImage(new Image(new File("assets\\duck_blue\\7.png").toURI().toString()));
                    if (duckNodeBlue.getScaleY()<0){duckNodeBlue.setScaleY(-duckNodeBlue.getScaleY());}
                    duckNodeBlue.setFitHeight(DuckHunt.SCALE * widthDucks);
                    duckNodeBlue.setFitWidth(DuckHunt.SCALE *  heightDucks);
                    Timeline shot = new Timeline(new KeyFrame(Duration.millis(500),shotHandler));
                    shot.play();

                    // animation of falling when the moment of shooting is over
                    shot.setOnFinished(fall->{
                        duckNodeBlue.setImage(new Image(new File("assets\\duck_blue\\8.png").toURI().toString()));
                        if (duckNodeBlue.getScaleY()<0){duckNodeBlue.setScaleY(-duckNodeBlue.getScaleY());}
                        duckNodeBlue.setFitHeight(DuckHunt.SCALE * heightDucks);
                        duckNodeBlue.setFitWidth(DuckHunt.SCALE *  widthDucks);
                        FirstLevel.duckFallsMusic.stop();
                        FirstLevel.duckFallsMusic.play();
                        TranslateTransition falling = new TranslateTransition(Duration.seconds(2), duckNodeBlue);
                        falling.setToY(DuckHunt.Width);
                        falling.play();

                    });

                    // if the game ends when the bluebird is shot
                    if (duckList.size()==0){
                        selectionPane.setMouseTransparent(true);
                        FirstLevel.levelCompletedMusic.play();
                        // winning texts
                        StackPane stackPane = new StackPane();
                        VBox winnerBox = winnerText();
                        stackPane.getChildren().add(winnerBox);
                        stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(winnerBox.widthProperty()).divide(2));
                        stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(winnerBox.heightProperty()).divide(2));
                        selectionPane.getChildren().add(stackPane);

                        //handling of player keyboard inputs
                        selectionPane.setOnKeyPressed(keyEvent->{
                            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                                //all music stops
                                FirstLevel.levelCompletedMusic.stop();
                                FirstLevel.duckFallsMusic.stop();
                                FirstLevel.gunShotMusic.stop();
                                primaryStage.setScene(new LevelSix().getLevelSixScene(primaryStage, selectedCursor, selectedBackground, countForBackGround, foreGroundNode));
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
                // prepare game over texts
                StackPane stackPane = new StackPane();
                VBox gameOverBox = new LevelTwo().gameOverText();
                stackPane.getChildren().add(gameOverBox);
                stackPane.layoutXProperty().bind(selectionPane.widthProperty().subtract(gameOverBox.widthProperty()).divide(2));
                stackPane.layoutYProperty().bind(selectionPane.heightProperty().subtract(gameOverBox.heightProperty()).divide(2));
                selectionPane.getChildren().add(stackPane);

                // blocking input outside the keyboard
                selectionPane.setMouseTransparent(true);
                selectionPane.requestFocus();

                // handling keyboard inputs
                selectionPane.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER:
                            FirstLevel.levelCompletedMusic.stop();
                            FirstLevel.gunShotMusic.stop();
                            FirstLevel.duckFallsMusic.stop();
                            //the same level is started
                            primaryStage.setScene(new LevelFive().getLevelFiveScene(primaryStage, selectedCursor, selectedBackground, countForBackGround,foreGroundNode));
                            break;
                        case ESCAPE:
                            FirstLevel.levelCompletedMusic.stop();
                            FirstLevel.gunShotMusic.stop();
                            FirstLevel.duckFallsMusic.stop();
                            //go to the login screen
                            primaryStage.setScene(new IntroScreen().getScene(primaryStage));
                            break;
                    }
                    selectionPane.requestFocus();
                });
            }
        });

        foreGroundNode.setMouseTransparent(false);
        selectionPane.getChildren().add(foreGroundNode);
        foreGroundNode.toFront();

        return levelFiveScene;
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
