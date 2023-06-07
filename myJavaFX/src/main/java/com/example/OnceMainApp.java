package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */
public class OnceMainApp extends Application {
    private Stage primaryStage;
    private TextArea fileContentArea;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // 创建一个按钮
        Button openFileButton = new Button("打开文件");
        openFileButton.setOnAction(e -> showFileSelectionDialog());

        // 创建一个文本域用于显示文件内容
        fileContentArea = new TextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setPrefHeight(300);

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(openFileButton, fileContentArea);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // 创建一个场景并将容器添加到场景中
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("File Viewer");
        primaryStage.show();
    }

    /**
     * 显示文件选择对话框并读取所选文件内容
     */
    private void showFileSelectionDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            readFileContent(selectedFile);
        }
    }

    /**
     * 读取文件内容并在文本域中显示
     */
    private void readFileContent(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            fileContentArea.setText(content.toString());
            // 显示新的视图
            showFileContentView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示包含文件内容的新视图
     */
    private void showFileContentView() {
        Stage fileContentViewStage = new Stage();
        Label fileLabel = new Label("文件内容：");
        VBox root = new VBox(fileLabel, fileContentArea);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 400, 400);
        fileContentViewStage.setScene(scene);
        fileContentViewStage.setTitle("File Viewer");
        fileContentViewStage.show();

        // 创建一个关闭按钮，用于关闭新的视图
        Button closeButton = new Button("关闭");
        closeButton.setOnAction(e -> fileContentViewStage.close());
        root.getChildren().add(closeButton);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
