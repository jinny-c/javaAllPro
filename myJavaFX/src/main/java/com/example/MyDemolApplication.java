package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * @description JFoenix 示例
 * JFoenix是一个开源Java库，提供了一套丰富的JavaFX UI控件，这些控件是基于Google的Material Design规范设计的。
 * 使用JFoenix，JavaFX开发者可以轻松地为他们的应用程序添加Material Design的外观和感觉。
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyDemolApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/MyDemo.fxml"));

        //可移动窗口
        removableWindow(root, primaryStage);
        //窗口设置
        primaryStage.setTitle("MY DEMO APPLICATION");
        primaryStage.setScene(new Scene(root, 600, 400));
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