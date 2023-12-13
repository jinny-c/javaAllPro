package com.example.app.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author chaijd
 * @description TODO
 * @date 2023-12-13
 */
public class ScrollPaneExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ScrollPane Example");

        // 创建一个大量内容的 VBox
        VBox content = new VBox();
        for (int i = 1; i <= 100; i++) {
            content.getChildren().add(new Label("Item " + i));
        }

        // 创建 ScrollPane 并将内容添加到其中
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);

        // 设置 ScrollPane 的可见视图大小
        scrollPane.setPrefSize(300, 200);

        // 创建布局
        VBox root = new VBox(10); // 垂直布局
        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 320, 240);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}