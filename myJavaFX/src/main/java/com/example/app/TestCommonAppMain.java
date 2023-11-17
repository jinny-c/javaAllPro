package com.example.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
public class TestCommonAppMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Custom CheckBox Text Example");

        // 创建 CheckBox
        CheckBox customCheckBox = new CheckBox("自定义文字");

        // 使用 CSS 设置样式
        customCheckBox.setStyle(
                "-fx-mark-displayed: text;" + // 显示文本而不是对号
                        "-fx-mark-character: '\u2713';" + // 设置自定义的Unicode字符，这里使用的是对号的Unicode
                        "-fx-mark-size: 16;" // 设置文本的大小
        );

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(customCheckBox);

        Scene scene = new Scene(stackPane, 300, 100);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
