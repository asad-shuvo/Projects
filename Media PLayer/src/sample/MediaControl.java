package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MediaControl extends BorderPane {

    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private  double tvl=0;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private boolean volForBtnM=false;
    private boolean volTemp=false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private HBox mediaBar;
    private VBox lowerBox;
    boolean F10bool=false;
    boolean playstate=true;
    int voldown=100;
    //  private VBox vBox;
    Button backButton=new Button();
    Button fastButton=new Button();
   // Button playButton=new Button();
    Button pauseButton=new Button();
    Button reloadButton=new Button();
   // Button fullScreenButton=new Button();
   // Button fullScreenButton2=new Button();
    public MediaControl(final MediaPlayer mp, final Button fullScreenButton, final Stage primarystage, final Button fileOpenButton, final Button fullScreenButton2 , final Scene mainScene) {

///Set Images for the button
        Image playImage = new Image("/sources/play.png", 20, 20, true,true);

        Image pauseImage = new Image("/sources/pause.png", 20, 20, true,true);

        Image stopImage = new Image("/sources/stop.png", 20, 20, true,true);

        Image volumeImage = new Image("/sources/volume.png", 20, 20, true,true);

        Image notVolumeImage = new Image("/sources/notsound.png", 20, 20, true,true);

        Image forwardImage = new Image("/sources/forwardBtn.png", 20, 20, true,true);

        Image backwardImage = new Image("/sources/BackBtn.png", 20, 20, true,true);

        Image reloadImage = new Image("/sources/relod.png", 20, 20, true,true);

        Image fullscreenImage = new Image("/sources/fullscreen.png", 20, 20, true,true);

        Image fileImage = new Image("/sources/Files.png", 20, 20, true,true);

        Image CrossButtonImage = new Image("/sources/CrossButton.png", 20, 20, true,true);



        //Image :Volume
        Button volumeButton = new Button();
        volumeButton.setGraphic(new ImageView(volumeImage));
        volumeButton.setPrefSize(10, 10);

        //Image : NotVoluem
        Button NotvolumeButton = new Button();
        NotvolumeButton.setGraphic(new ImageView(notVolumeImage));
        NotvolumeButton.setPrefSize(10, 10);

        //Image : Pause
        pauseButton.setGraphic(new ImageView(playImage));
        pauseButton.setPrefSize(10, 10);


        //Image :Fast
        fastButton.setGraphic(new ImageView(forwardImage));
        fastButton.setPrefSize(10,10);

        //Image :Back
        backButton.setGraphic(new ImageView(backwardImage));
        backButton.setPrefSize(10,10);

        //Image :Relod
        reloadButton.setGraphic(new ImageView(reloadImage));
        reloadButton.setPrefSize(10,10);

        //Image :FullScreen
        fullScreenButton.setGraphic(new ImageView(fullscreenImage));
        fullScreenButton.setPrefSize(10,10);

        //Image File Image
        Button CrossButton=new Button();
        fileOpenButton.setGraphic(new ImageView(fileImage));
        fileOpenButton.setPrefSize(10,10);

        //Image File Cross Button Image
        CrossButton.setGraphic(new ImageView(CrossButtonImage));
        CrossButton.setPrefSize(10,10);

        final Button playButton = new Button();
        //Image :Play
        playButton.setGraphic(new ImageView(pauseImage));
        playButton.setPrefSize(10, 10);

        ///if we click cross button it will exit the window
        CrossButton.setOnAction(e->Platform.exit());


        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        Pane mvPane = new Pane() {
        };
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);
        int w = mp.getMedia().getWidth();
        int h = mp.getMedia().getHeight();

        primarystage.setMinWidth(w);
        primarystage.setMinHeight(h);

        ///Full Screen
        DoubleProperty width= mediaView.fitWidthProperty();
        DoubleProperty height=mediaView.fitHeightProperty();
//
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


        //  stage.setMinWidth(w);
        //stage.setMinHeight(h);
        // vBox=new VBox();
        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

///Work of play and pause
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN || status == Status.HALTED) {
                    // don't do anything in these states
                    return;
                }

                if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            }
        });
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setGraphic(new ImageView(pauseImage));
                }
            }
        });

        mp.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setGraphic(new ImageView(playImage));
            }
        });

        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setGraphic(new ImageView(playImage));
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

  //      mediaBar.getChildren().add(playButton);
        // Add spacer
        Label spacer = new Label("   ");
        mediaBar.getChildren().add(spacer);

        // Add Time label
        Label timeLabel = new Label("Time: ");
        mediaBar.getChildren().add(timeLabel);

        // Add time slider
        timeSlider = new Slider();
      //  timeSlider.setStyle("-fx-background-color: #7cafc2");
        timeSlider.setStyle("-fx-background-color: DARKGREY");
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);

        /**slider ticmark/the point

        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);

        /**      **/
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
///Demo Box
        HBox demoBox=new HBox();
        demoBox.setAlignment( Pos.BOTTOM_RIGHT);
        demoBox.getChildren().addAll(fullScreenButton2,CrossButton);



        HBox hB=new HBox();
        hB.setStyle("-fx-background-color: #7cafc2");
        hB = new HBox();
       hB .setAlignment(Pos.CENTER);
        hB .setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(hB , Pos.CENTER);
        //hB.getChildren().add()
        mediaBar.getChildren().add(timeSlider);

        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);

        // Add the volume label
        Label volumeLabel = new Label("Vol: ");
      //  mediaBar.getChildren().add(volumeLabel);

        // Add Volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mp.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        volumeButton.setOnAction((ActionEvent e)->{
            if(volTemp==false){
                volumeButton.setGraphic(new ImageView(notVolumeImage));
                volumeSlider.setValue(0);
                mp.setVolume(0);
                volTemp=true;
            }
            else{
                volumeButton.setGraphic(new ImageView(volumeImage));
                volumeSlider.setValue(10);
                mp.setVolume(10);
                volTemp=false;
            }

        });


        ///key Pressed
       final int  t=0;

        fastButton.setOnKeyPressed(
                event -> {
                    switch (event.getCode())
                    {
                        case UP:
                            System.out.println("Up Button Pressed\n");
                    }


        });



        Label space=new Label("        ");
       // mediaBar.getChildren().add(volumeSlider);
        hB.getChildren().add(fileOpenButton);
        hB.getChildren().add(space);
        hB.getChildren().add(backButton);
        hB.getChildren().add(playButton);
        hB.getChildren().add(fastButton);
        hB.getChildren().add(reloadButton);
        hB.getChildren().add(fullScreenButton);

        Label spc=new Label("                                      ");
        hB.getChildren().add(volumeButton);
        hB.getChildren().add(volumeSlider);
        //BackButton
        backButton.setOnAction((ActionEvent e) -> {
            mp.seek(mp.getCurrentTime().divide(1.1));
        });

        //FasrButton
        fastButton.setOnAction((ActionEvent e) -> {
            mp.seek(mp.getCurrentTime().divide(.9));
        });

        //ReloadButton
        reloadButton.setOnAction((ActionEvent e) -> {
            mp.seek(mp.getStartTime());
        });


        //FullScreenButton
      /*  fullScreenButton.setOnAction((ActionEvent e) -> {


        });*/
HBox demoBox2=new HBox();
      ///Full Screen
        Image fullscreenImage2 = new Image("/sources/fullscreen.png", 20, 20, true,true);
        //  fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
        fullScreenButton2.setPrefSize(10,10);


        fullScreenButton.setOnAction((ActionEvent e) -> {
            if (primarystage.isFullScreen()) {
                setTop(demoBox2);
                fullScreenButton2.setGraphic(new ImageView());
                primarystage.setFullScreen(false);
            }
            else {
                setTop(demoBox);
                primarystage.setFullScreen(true);
                fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
            }
        });
        mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent doubleClicked) {
                if(doubleClicked.getClickCount()==2){
                    if (primarystage.isFullScreen()) {
                        setTop(demoBox2);
                        fullScreenButton2.setGraphic(new ImageView());
                        primarystage.setFullScreen(false);
                    } else {
                        setTop(demoBox);
                        primarystage.setFullScreen(true);
                        fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
                    }
                }
            }
        });
        ;

        fullScreenButton2.setOnAction((ActionEvent e) -> {
            if (primarystage.isFullScreen()) {
                setTop(demoBox2);
                fullScreenButton2.setGraphic(new ImageView());
                primarystage.setFullScreen(false);
            } else {
                setTop(demoBox);
                primarystage.setFullScreen(true);
                fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
            }
        });

        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (F10bool == false) {
                    setTop(demoBox);
                    primarystage.setFullScreen(true);
                    fullScreenButton2.setGraphic(new ImageView(fullscreenImage2));
                    F10bool = true;
                }
                else if (F10bool == true) {
                    setTop(demoBox2);
                    fullScreenButton2.setGraphic(new ImageView());
                    primarystage.setFullScreen(false);
                    F10bool = false;
                }
            }
        });

        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.P) {
                if (playstate == true) {
                    mp.pause();
                    playstate=false;
                }
else if(playstate==false){
                    mp.play();
                    playstate=true;
                }
            }


        });
       addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.RIGHT)
                mp.seek(mp.getCurrentTime().divide(.9));
        });

        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.LEFT)
                mp.seek(mp.getCurrentTime().divide(1.1));
        });
        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.HOME)
                mp.seek(mp.getStartTime());
        });
        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.END)
                mp.seek(mp.getStopTime());

        });
        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.DELETE)
               Platform.exit();

        });


        addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.M) {
                if (volForBtnM == false) {
                    mp.setVolume(0);
                    volForBtnM = true;
                } else {
                    mp.setVolume(10);
                    volForBtnM = false;
                }
            }

        });
       addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.U)
          tvl=volumeSlider.getValue();
            tvl=tvl-10;

            System.out.println(tvl);
                mp.setVolume(tvl);
        });
        lowerBox=new VBox();
        //lowerBox.getChildren().add(demoBox);
       // lowerBox.setStyle("-fx-background-color: #7cafc2");
        lowerBox.setStyle("-fx-background-color: DARKGREY");
        lowerBox.getChildren().add(mediaBar);
        lowerBox.getChildren().addAll(hB);

        setBottom(lowerBox);
    }



    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
}
