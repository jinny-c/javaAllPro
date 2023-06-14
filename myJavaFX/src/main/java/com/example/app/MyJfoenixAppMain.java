package com.example.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
public class MyJfoenixAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建JFXTextField和JFXButton控件
        JFXTextField textField = new JFXTextField();
        textField.setPromptText("请输入文本");

        JFXButton button = new JFXButton("提交");
        button.getStyleClass().add("primary-button"); // 添加样式类

        // 将控件放置在VBox中
        VBox vbox = new VBox(textField, button);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));

        // 创建StackPane并将vbox添加到其中
        StackPane root = new StackPane(vbox);

        // 创建场景并将根节点添加到其中
        Scene scene = new Scene(root, 300, 200);

        // 添加CSS样式表
        scene.getStylesheets().add("/jfoenix.css");

        // 设置标题并显示舞台
        primaryStage.setTitle("JFoenix Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
