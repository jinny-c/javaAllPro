package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
//@SpringBootApplication
public class MyJavaFxmlApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/SampleController.fxml"));

        //可移动窗口
        removableWindow(root, primaryStage);
        //窗口设置
        primaryStage.setTitle("My Java Fxml Application");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    private <T extends Parent> void removableWindow(T pan, Stage primaryStage) {
        // 绑定鼠标事件
        pan.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        pan.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}