package com.example;

import com.example.app.MyAppMain;
import com.example.app.MyControllerAppMain;
import com.example.view.Base64View;
import com.example.view.FileDigestView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyJavaMainApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button openFileButton = new Button("File Processing");
        openFileButton.setPrefHeight(64);
        openFileButton.setOnAction(event -> {
            try {
                FileDigestView view = new FileDigestView();
                view.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button base64Button = new Button("Base64 Processing");
        base64Button.setPrefHeight(64);
        base64Button.setOnAction(event -> {
            Base64View view = new Base64View();
            view.show();
        });

        Button aloneWindowButton = new Button("Independent Processing");
        aloneWindowButton.setPrefHeight(64);
        aloneWindowButton.setOnAction(event -> {
            MyAppMain myMainApp = new MyAppMain();
            myMainApp.start(new Stage());
        });

        Button fxmlWindowButton = new Button(".fxml Processing");
        fxmlWindowButton.setPrefHeight(64);
        fxmlWindowButton.setOnAction(event -> {
            try {
                MyControllerAppMain myMainApp = new MyControllerAppMain();
                myMainApp.start(new Stage());
            } catch (Exception e) {
            }
        });

        // 创建一个一个流式布局容器
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(20));
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setOrientation(Orientation.VERTICAL); // 将 orientation 设置为垂直
        flowPane.getChildren().addAll(openFileButton, base64Button, aloneWindowButton, fxmlWindowButton);

        // 创建一个边框布局容器
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(flowPane);

        // 退出按钮
        Button exitButton = new Button("关闭窗口");
        exitButton.setPrefHeight(64);
        //exitButton.setOnAction(e -> System.exit(0));
        exitButton.setOnAction(event -> {
            primaryStage.close();
//            event.consume();
//            ConfirmDialog confirm = new ConfirmDialog(primaryStage);
//            if (confirm.showAndWait().get()) {
//                primaryStage.close();
//            }
        });

        // 将关闭按钮放置在底部右侧
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(exitButton, new Insets(10));
        borderPane.setBottom(exitButton);

        // 创建场景并显示窗口
        Scene scene = new Scene(borderPane, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.setResizable(false); // 将窗口大小设置为固定不变

        // 绑定鼠标事件
        borderPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        borderPane.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}