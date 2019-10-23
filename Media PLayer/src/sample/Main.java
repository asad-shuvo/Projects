package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.stage.*;
import java.io.File;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import sample.ConfirmBox;
import sample.MediaControl;

import java.awt.*;
import java.io.File;

import static java.awt.SystemColor.text;

public class Main extends Application {
    Stage stage;
    Button playBtn = new Button("Play");
    Button fullScreenButton2 = new Button();
    Button pauseBtn = new Button("Pause");
    int temp=0;
    Button fullScreen=new Button();
    Button fileOpenButton = new Button();
    Media media;
    Button backButton=new Button("Back");
    Button fastButton=new Button("FF");
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Slider slider = new Slider();
    Slider vol=new Slider();
    @Override
    public void start(Stage primaryStage) throws Exception {

        stage=primaryStage;

        ////Set Title for the window
        stage.setTitle("Media Player");
        Group sceneGroup = new Group();


        //  Scene mainScene = new Scene(sceneGroup, 600, 300,Color.DIMGRAY);
        //Scene mainScene = new Scene(sceneGroup, 600, 300,Color.DARKSLATEGRAY);
        Scene mainScene = new Scene(sceneGroup, 900, 500,Color.DARKGREY);
        Image backgroundImage = new Image("/sources/mpbi5.png", 1000, 660, true,true);
        Image backgroundImage2 = new Image("/sources/mpbi5.png", 1600, 1460, true,true);
        if(stage.isFullScreen()==true){
            ImageView iv=new ImageView();
            iv.setImage(backgroundImage2);
            sceneGroup.getChildren().add(iv);
        }
        else{
          //  sceneGroup.setGraphic(new ImageView(fullscreenImage2));
            ImageView iv=new ImageView();
            iv.setImage(backgroundImage);
            sceneGroup.getChildren().add(iv);

        }


//BackGround Image for scene

        // Button fileOpenButton = new Button();
        fileOpenButton.setText("Choose File");
     //   fileOpenButton.setStyle("-fx-background-color: #7cafc2");
      /*  #shiny-orange {
            -fx-background-color:
            linear-gradient(#ffd65b, #e68400),
                    linear-gradient(#ffef84, #f2ba44),
                    linear-gradient(#ffea6a, #efaa22),
                    linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),
                    linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));
            -fx-background-radius: 30;
            -fx-background-insets: 0,1,2,3,0;
            -fx-text-fill: #654b00;
            -fx-font-weight: bold;
            -fx-font-size: 14px;
            -fx-padding: 10 20 10 20;
        }*/
        fileOpenButton.setStyle("-fx-background-color: #7cafc2");
        /*
        fileOpenButton.setStyle("-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 )");
        fileOpenButton.setStyle("-fx-background-radius: 30");
        fileOpenButton.setStyle("-fx-background-insets: 0,1,2,3,0");
        fileOpenButton.setStyle("-fx-text-fill: #654b00");
        fileOpenButton.setStyle("-fx-font-weight: bold");
        fileOpenButton.setStyle("-fx-font-size: 14px");
        fileOpenButton.setStyle("-fx-padding: 10 20 10 20");*/

        //fileOpenButton.setStyle("border-radius: 20px");
        sceneGroup.getChildren().add(fileOpenButton);
        // sceneGroup.setBottom(hBox);
        //sceneGroup.getChildren().add(fileOpenButton);
        //  hBox.getChildren().addAll(fileOpenButton,playBtn,pauseBtn,slider);
        // sceneGroup.setBottom(hBox);
//sceneGroup.setStyle("-fx-background-color: Black");
        final FileChooser fileChooser = new FileChooser();

        fileOpenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                temp++;
                if(temp>1){
                    mediaPlayer.stop();
                }
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {

                    String fileLink = file.toURI().toString();
                    // Media media = new Media(fileLink);
                    media = new Media(fileLink);
                    // MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.stop();
                    mediaPlayer.setAutoPlay(true);

                    // MediaView mediaView = new MediaView(mediaPlayer);
                    //    mediaView = new MediaView(mediaPlayer);
                    MediaControl mediaControl = new MediaControl(mediaPlayer,fullScreen,stage,fileOpenButton,fullScreenButton2,mainScene);
                    mainScene.setRoot(mediaControl);


               /*    mediaPlayer.setOnReady(new Runnable() {
                        @Override
                        public void run() {
                            int w = mediaPlayer.getMedia().getWidth();
                            int h = mediaPlayer.getMedia().getHeight();

                            stage.setMinWidth(w);
                            stage.setMinHeight(h);

                            ///Full Screen
                            DoubleProperty width= mediaView.fitWidthProperty();
                            DoubleProperty height=mediaView.fitHeightProperty();
//
                            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

                        }
                    });*/

                    int w = mediaPlayer.getMedia().getWidth();
                    int h = mediaPlayer.getMedia().getHeight();

                    stage.setMinWidth(w);
                    stage.setMinHeight(h);
                    // mediaPlayer.play();
                    //  sceneGroup.getChildren().add(mediaView);

                }

            }

        });
        /*
        Image fullscreenImage2 = new Image("/sources/fullscreen.png", 20, 20, true,true);
      //  fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
        fullScreenButton2.setPrefSize(10,10);


        fullScreen.setOnAction((ActionEvent e) -> {
            if (stage.isFullScreen()) {
                fullScreenButton2.setGraphic(new ImageView());
                stage.setFullScreen(false);
            }
            else {
                stage.setFullScreen(true);
                fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
            }
        });
        mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent doubleClicked) {
                if(doubleClicked.getClickCount()==2){
                    if (stage.isFullScreen()) {
                        fullScreenButton2.setGraphic(new ImageView());
                        stage.setFullScreen(false);
                    } else {
                        stage.setFullScreen(true);
                        fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
                    }
                }
            }
        });
     ;

        fullScreenButton2.setOnAction((ActionEvent e) -> {
            if (stage.isFullScreen()) {
                fullScreenButton2.setGraphic(new ImageView());
                stage.setFullScreen(false);
            } else {
                stage.setFullScreen(true);
                fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
            }
        });*/


        stage.setScene(mainScene);
        stage.sizeToScene();
        stage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }



}