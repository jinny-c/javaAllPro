package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/9
 */
public class MyControllerAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MyController.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyController.fxml"));
//        GridPane root = loader.load();

//        //关闭按钮设置
//        Button closeButton = closeButton(primaryStage);
//        GridPane.setHalignment(closeButton, HPos.RIGHT);
//        root.add(closeButton, 0, 2);

        //可移动窗口
        removableWindow(root, primaryStage);
        //窗口设置
        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

//    private Button closeButton(Stage primaryStage) {
//        // 创建一个按钮，用于关闭应用程序
//        Button closeButton = new Button("Close");
//        closeButton.setPrefHeight(64);
//        closeButton.setOnAction(e -> primaryStage.close());
//        return closeButton;
//    }

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
