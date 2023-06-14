package com.example.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
public class TestCommonAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建菜单
        Button fileMenu = new Button("File");
        Button editMenu = new Button("Edit");
        Button viewMenu = new Button("View");

        // 创建工具栏
        Button saveButton = new Button("Save");
        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");

        HBox toolBar = new HBox(saveButton, undoButton, redoButton);
        toolBar.setAlignment(Pos.CENTER_LEFT);

        // 创建状态栏
        VBox statusBar = new VBox();
        statusBar.getChildren().addAll(new Button("Status 1"), new Button("Status 2"));

        // 创建侧边栏
        Button navigationButton1 = new Button("Navigation 1");
        Button navigationButton2 = new Button("Navigation 2");

        VBox sideBar = new VBox(navigationButton1, navigationButton2);

        // 设置BorderPane布局
        BorderPane root = new BorderPane();
        root.setTop(new HBox(fileMenu, editMenu, viewMenu));
        root.setLeft(sideBar);
        root.setCenter(new VBox());
        root.setBottom(statusBar);
        root.setPrefSize(800, 600);

        // 创建场景并显示舞台
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
