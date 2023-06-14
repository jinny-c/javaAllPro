package com.example.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 音乐播放主页面
 */
public class MusicPlayerController extends Application {

    //private Button playButton;

    private MediaPlayer mp1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            stage.setTitle("播放");
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setVgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(25, 25, 25, 25));

            //显示播放歌曲的窗口
            Text musicTitle = new Text();
            musicTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            musicTitle.setFill(Color.BLUE);
            gridPane.add(musicTitle, 1, 0, 2, 1);

            Button playButton = new Button("播放");
            //playButton.setPadding(new Insets(5, 5, 5, 5));
            Button exitButton = new Button("注销");
            playButton.setPrefSize(100, 20);
            exitButton.setPrefSize(100, 20);
            //exitButton.setPadding(new Insets(5, 5, 5, 5));
            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(15, 12, 15, 12));
            hBox.setAlignment(Pos.CENTER);
            //hBox.getChildren().add(playButton);
            hBox.getChildren().addAll(playButton, exitButton);
            gridPane.add(playButton, 1, 6);
            gridPane.add(exitButton, 12, 6);

            playButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String musicName = "外婆的澎湖湾.mp3";
                    String s1 = MusicPlayerController.class.getResource(musicName).toString();
                    Media media1 = new Media(s1);
                    mp1 = new MediaPlayer(media1);
                    mp1.play();
                    String[] arr = musicName.split("\\.");
                    System.out.println("arr:" + arr.length);
                    musicTitle.setText(arr[0]);
                }
            });

            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mp1.stop();
                    stage.close();
                    LoginController loginController = new LoginController();
                    Stage newStage = new Stage();
                    loginController.start(newStage);
                }
            });


            /*Button exitButton = new Button("注销");
            HBox exitButtonHBox = new HBox(10);
            exitButtonHBox.setAlignment(Pos.BOTTOM_RIGHT);
            exitButtonHBox.getChildren().add(exitButton);
            gridPane.add(exitButton, 6, 6);*/

            Scene scene = new Scene(gridPane, 300, 275);
            //scene.getStylesheets().add(MusicPlayerController.class.getResource("MusicPlayerController.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}