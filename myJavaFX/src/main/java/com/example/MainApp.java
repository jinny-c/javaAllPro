package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button openFileButton = new Button("Open File App");
        openFileButton.setOnAction(event -> {
            try {
                FileDigestView view = new FileDigestView();
                view.show();
//                FileReadView fileReadView = new FileReadView();
//                fileReadView.show();
//                String fileContent = fileReadView.getText();
//                System.out.println("File content: " + fileContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button base64Button = new Button("Open Base64 App");
        base64Button.setOnAction(event -> {
            try {
                Base64View view = new Base64View();
                view.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 创建一个水平布局容器
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(openFileButton, base64Button);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        // 创建一个边框布局容器
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(hbox);

        // 退出按钮
        Button exitButton = new Button("关闭窗口");
        exitButton.setOnAction(e -> System.exit(0));
        // 将关闭按钮放置在底部右侧
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(exitButton, new Insets(10));
        borderPane.setBottom(exitButton);

        // 创建场景并显示窗口
        Scene scene = new Scene(borderPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}