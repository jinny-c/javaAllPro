package com.example.app.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author chaijd
 * @description TODO
 * @date 2023-12-13
 */
public class TabPaneExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TabPane Example");

        // 创建标签和内容
        Label label1 = new Label("内容1");
        Label label2 = new Label("内容2");
        Label label3 = new Label("内容3");

        // 创建 VBox 并添加一些组件
        VBox vbox = new VBox(10); // 垂直布局
        vbox.getChildren().addAll(
                new Label("这是一个标签"),
                new Label("这是另一个标签"),
                new Label("这是第三个标签")
        );

        // 创建选项卡
        Tab tab1 = new Tab("选项卡1", label1);
        tab1.setContent(vbox);

        Circle circle = new Circle(22);
        //circle.setCenterX(100);
        //circle.setCenterY(100);
        circle.setFill(Color.BLUE);

        Tab tab2 = new Tab("选项卡2", circle);
        Tab tab3 = new Tab("选项卡3", label3);

        // 创建 TabPane 并添加选项卡
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        // 创建布局
        VBox root = new VBox(10); // 垂直布局
        root.getChildren().add(tabPane);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}