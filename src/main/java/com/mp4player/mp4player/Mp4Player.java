package com.mp4player.mp4player;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Mp4Player extends Application {
    private MediaPlayer mediaPlayer;

     int WIDTH = 1280;
     int HEIGHT = 720;

    private Stage stage;
    private Stage secondStage;
    private Pane options;
    private Button returnMenu;
    private Scene scene;
    private Button fullscreen;
    private Scene scene2;
    private MediaView mediaView;
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Pane pane = new Pane();
        Image menu = new Image("menu.png");
        ImageView menuView = new ImageView(menu);
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Files", "*.mp4"));

            Button openButton = new Button();
            openButton.setPrefSize(220,150);
            openButton.setLayoutY(340);
            openButton.setLayoutX(530);
            openButton.setOpacity(0);
            openButton.setOnMouseClicked(e -> {
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) {
                    playMedia(file.toURI().toString());
                    stage.hide();
                }
            });

            pane.getChildren().addAll(menuView,openButton);
            Scene scene = new Scene(pane, WIDTH, HEIGHT);

            stage.setTitle("MP4 Player");
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMedia(String source) {
        try {
            Button returnMenu = new Button("Options");
            returnMenu.setStyle("-fx-background-color: grey;-fx-text-fill: lightgray");

            Media media = new Media(source);
            mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(1100);
            mediaView.setFitHeight(1100);
            mediaView.setLayoutX(100);
            mediaView.setLayoutY(10);

            Pane pane2 = new Pane();
            pane2.setStyle("-fx-background-color: #1d1b1b");
            options = optionsPane();




            addHoverEffect(returnMenu);
            removeHoverEffect(returnMenu);

            //Media controls
            //===============================================================================

            Button play = new Button();
            Image playImg = new Image("play.png");
            ImagePattern playPatt = new ImagePattern(playImg);
            Rectangle rectangle = new Rectangle(62,62);
            rectangle.setFill(playPatt);
            play.setGraphic(rectangle);
            play.setStyle("-fx-background-color: transparent");
            play.setOnMouseClicked(event -> {
                mediaPlayer.play();
            });

            Button pause = new Button();
            Image pauseImg = new Image("pause.png");
            ImagePattern pausePatt = new ImagePattern(pauseImg);
            Rectangle rectangle2 = new Rectangle(50,50);
            rectangle2.setFill(pausePatt);
            pause.setGraphic(rectangle2);
            pause.setStyle("-fx-background-color: transparent");
            pause.setOnMouseClicked(event -> {
                mediaPlayer.pause();
            });

            HBox hBox = new HBox(4);
            hBox.getChildren().addAll(play,pause);
            hBox.setAlignment(Pos.CENTER);
            hBox.setLayoutY(630);
            hBox.setLayoutX(500);


            //================================================================================

            pane2.getChildren().addAll(mediaView, returnMenu, options,rectangle,rectangle2,hBox);
            Scene scene2 = new Scene(pane2, WIDTH, HEIGHT);
            secondStage = new Stage();
            secondStage.setResizable(false);
            secondStage.setOnCloseRequest(event -> mediaPlayer.stop());

            mediaPlayer.play();
            secondStage.setScene(scene2);
            secondStage.show();
        } catch (IllegalArgumentException m) {
            m.printStackTrace();
        }
    }

    private Pane optionsPane() {
        Pane options = new Pane();
        VBox vBox = new VBox(3);

        Button newMediaFile = new Button("Open New File");
        Button fullscreen = new Button("Fullscreen");

        options.setPrefWidth(250);
        options.setPrefHeight(400);
        options.setStyle("-fx-background-color: #808080FF");
        options.setLayoutY(25);
        options.setVisible(false);
        optionsPaneHandler(options);

        options.getChildren().add(vBox);

        newMediaFile.setOnMouseClicked(event -> openNewMedia());
        newMediaFile.setStyle("-fx-background-color: transparent;-fx-border-color: black;-fx-pref-width: 250");


        fullscreen.setOnMouseClicked(event -> {
            fullscreenStage();
        });
        fullscreen.setStyle("-fx-background-color: transparent;-fx-border-color: black;-fx-pref-width: 250");

        vBox.getChildren().addAll(newMediaFile,fullscreen);
        vBox.setAlignment(Pos.BASELINE_LEFT);

        return options;
    }

    private void addHoverEffect(Button returnMenu) {
        returnMenu.setOnMouseEntered((MouseEvent event) -> {
            options.setVisible(true);
        });
    }

    private void removeHoverEffect(Button returnMenu){
        returnMenu.setOnMouseExited((MouseEvent event) -> {
            options.setVisible(false);
        });
    }
    private void optionsPaneHandler(Pane options){
        options.setOnMouseEntered((MouseEvent event )-> {
            options.setVisible(true);
        });

        options.setOnMouseExited((MouseEvent event) ->{
            options.setVisible(false);
        });

    }
    private void openNewMedia(){
        mediaPlayer.stop();
        secondStage.close();
        stage.show();
    }
    private void fullscreenStage(){
        if(stage.isShowing()){
            stage.setFullScreen(true);
        }else{
            secondStage.setFullScreen(true);

        }
    }

    public static void main(String[] args) {
        launch();
    }
}
