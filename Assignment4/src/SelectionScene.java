import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
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
import java.io.File;
import java.util.ArrayList;

public class SelectionScene {
    Integer countForBackGround = 0;
    Integer countForCursor =0;
    Image cursorImage;
    ImageView backGroundImageView;

    public Scene getScene(Stage primaryStage) {
        //add the musics
        MediaPlayer introMusic = new MediaPlayer(new Media(new File("assets\\effects\\Intro.mp3").toURI().toString()));

        // Get the list of background and cursor names
        ArrayList<String> backGroundNames = new ArrayList<>();
        ArrayList<String > cursorNames = new ArrayList<>();
        File directoryBackGround = new File("assets\\background");
        File directoryCursor = new File("assets\\crosshair");
        // Retrieve the background image names
        File[] files = directoryBackGround.listFiles();
        assert files != null;
        for (File file : files) {
                backGroundNames.add(file.getName());
            }
        // Retrieve the cursor image names
        File[] filess = directoryCursor.listFiles();
        assert filess != null;
        for (File file : filess) {
            cursorNames.add(file.getName());
        }

            Pane selectionPane = new StackPane();
            selectionPane.setOnKeyPressed(e->{
                switch (e.getCode()){
                    case LEFT:
                        countForBackGround-=1;
                        if (countForBackGround<0){
                            countForBackGround+=backGroundNames.size();}
                        selectionForBackGround(selectionPane, backGroundNames);
                        selectionForCursor(selectionPane, cursorNames);
                        break;
                    case RIGHT:
                        countForBackGround +=1;
                        if (countForBackGround>=backGroundNames.size()) {
                            countForBackGround-=backGroundNames.size();}
                        selectionForBackGround(selectionPane, backGroundNames);
                        selectionForCursor(selectionPane, cursorNames);
                        break;
                    case UP: countForCursor +=1;
                        if (countForCursor>=cursorNames.size()) { countForCursor-=cursorNames.size();}
                        selectionForCursor(selectionPane, cursorNames);
                        break;
                    case DOWN:
                        countForCursor-=1;
                        if (countForCursor<0){ countForCursor+=cursorNames.size();}
                        selectionForCursor(selectionPane, cursorNames);
                        break;
                    case ENTER:
                        ImageCursor selectedCursor = new ImageCursor(cursorImage);
                        IntroScreen.animation.stop();
                        IntroScreen.titleMusic.stop();
                        introMusic.play();
                        introMusic.setVolume(DuckHunt.VOLUME);
                        introMusic.setOnEndOfMedia(()-> primaryStage.setScene(new FirstLevel().getLevelOneScene(primaryStage,selectedCursor,backGroundImageView,countForBackGround,selectionForeGround()))
                        );
                        break;
                    case ESCAPE:
                        IntroScreen.animation.stop();
                        IntroScreen.titleMusic.stop();
                        primaryStage.setScene(new IntroScreen().getScene(primaryStage));

                    break;

                }});

        selectionForBackGround(selectionPane, backGroundNames);
        selectionForCursor(selectionPane, cursorNames);

        Scene selectionScene = new Scene(selectionPane);
        selectionPane.requestFocus();
        return selectionScene;

    }

    /**
     * Sets the background image for the selection scene.
     *
     * @param selectionPane    The pane representing the selection scene
     * @param backGroundNames  The list of background image names
     */
    public void selectionForBackGround(Pane selectionPane, ArrayList<String> backGroundNames) {
        String uri = new File("assets\\background\\" + backGroundNames.get(countForBackGround)).toURI().toString();
        Image backGround = new Image(uri);
        double height = backGround.getHeight();
        double width = backGround.getWidth();
        ImageView backGroundNode = new ImageView(backGround); // Convert the image to an ImageView node
        backGroundNode.setFitHeight(DuckHunt.SCALE * height);
        backGroundNode.setFitWidth(DuckHunt.SCALE * width);
        backGroundImageView = backGroundNode;
        selectionPane.getChildren().clear(); // Clear the existing children of the selectionPane
        selectionPane.getChildren().add(backGroundNode); // Add the background image to the selectionPane
        selectionPane.getChildren().add(textForSelectionScene()); // Add the text for the selection scene
    }

    /**
     * Sets the cursor image for the selection scene.
     * @param selectionPane  The pane representing the selection scene
     * @param cursorNames    The list of cursor image names
     */
    public void selectionForCursor(Pane selectionPane, ArrayList<String> cursorNames){
        String uri = new File("assets\\crosshair\\"+cursorNames.get(countForCursor)).toURI().toString();
        Image cursor = new Image(uri);
        double height = cursor.getHeight();
        double width = cursor.getWidth();
        cursorImage=cursor;
        ImageView cursorNode= new ImageView(cursor); // Convert the image to an ImageView node
        cursorNode.setFitHeight(DuckHunt.SCALE *height);
        cursorNode.setFitWidth(DuckHunt.SCALE *width);
        // We didn't call clear() here because we first set the background and then call this method,
        // so if clear() is called here, the previously set background will be cleared.
        selectionPane.getChildren().add(cursorNode);
        selectionPane.getChildren().add(textForSelectionScene());
    }

    /**
     * Sets the foreground image for the selection scene.
     *
     * @return The ImageView node representing the foreground image
     */
public ImageView selectionForeGround(){
    ArrayList<String > foreGroundNames = new ArrayList<>();
    File directoryForeGround = new File("assets\\foreground");
    File[] files = directoryForeGround.listFiles();
    assert files != null;
    for (File file : files) {
        foreGroundNames.add(file.getName());
    }
    String url = new File("assets\\foreground\\" + foreGroundNames.get(countForBackGround)).toURI().toString();
    Image foreGround = new Image(url);
    double heightForeGround = foreGround.getHeight();
    double widthForeGround = foreGround.getWidth();
    ImageView foreGroundNode = new ImageView(foreGround); // Convert the image to an ImageView node
    foreGroundNode.setFitHeight(DuckHunt.SCALE * heightForeGround);
    foreGroundNode.setFitWidth(DuckHunt.SCALE * widthForeGround);
    return foreGroundNode;
}

    /**
     * Creates a VBox containing the text for the selection scene.
     *
     * @return The VBox containing the text nodes
     */
    public VBox textForSelectionScene(){
        Text text1 = new Text("USE ARROW KEYS TO NAVIGATE");
        Text text2 = new Text("PRESS ENTER TO START");
        Text text3 = new Text("PRESS ESC TO EXIT");
        text1.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        text1.setFill(Color.ORANGE);
        text2.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        text2.setFill(Color.ORANGE);
        text3.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE *10));
        text3.setFill(Color.ORANGE);
        VBox vbox= new VBox(text1,text2,text3);
        vbox.setAlignment(Pos.TOP_CENTER);
        return vbox;
    }
}
