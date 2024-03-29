package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/9
 */
public class TableDemoControllerAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/TableDemoController.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TableDemoController.fxml"));
//        GridPane root = loader.load();

//        //关闭按钮设置
//        Button closeButton = closeButton(primaryStage);
//        GridPane.setHalignment(closeButton, HPos.RIGHT);
//        root.add(closeButton, 0, 2);

        //可移动窗口
        removableWindow(root, primaryStage);
        //窗口设置
        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(root, 650, 450));
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
