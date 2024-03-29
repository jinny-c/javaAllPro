package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @description 通用测试用
 * @auth chaijd
 * @date 2023/6/9
 */
public class SampleControllerAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/SampleController.fxml"));

//        Parent root = FXMLLoader.load(getClass().getResource("/myJfoenix/LoginDemo.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/BallGet.fxml"));



        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(root, 1300, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
