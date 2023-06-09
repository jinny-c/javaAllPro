package com.example.app;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyMainApp extends Application {
    private TextArea fileContentTextArea;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Viewer");
        primaryStage.setResizable(false); // 将窗口大小设置为固定不变

        // 创建一个GridPane布局来安排UI元素
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);


        // 创建标签和文本区域来显示文件内容
        Label fileContentLabel = new Label("File content:");
        fileContentTextArea = new TextArea();
        fileContentTextArea.setEditable(false);
        fileContentTextArea.setWrapText(true);
        //fileContentTextArea.setPrefRowCount(5);
        //fileContentTextArea.setPrefColumnCount(16);

        // 将标签和文本区域添加到网格中
        grid.add(fileContentLabel, 0, 0);
        grid.add(fileContentTextArea, 0, 1);

        // 创建一个按钮来打开文件选择器
        Button selectButton = new Button("Select file");
        selectButton.setPrefHeight(64);
        selectButton.setOnAction(e -> {
            // 创建一个文件选择器对话框
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
//                    new FileChooser.ExtensionFilter("Java Files", "*.java")
//            );

            // 显示文件选择器对话框并等待用户选择文件
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            // 如果用户选择了一个文件，则将其内容显示在文本区域中
            if (selectedFile != null) {
                fileContentTextArea(selectedFile);
//                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
//                    String content = "";
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        content += line + "\n";
//                    }
//                    fileContentTextArea.setText(content);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
            }
        });

        // 创建一个按钮，用于清除文本区域中的内容
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> fileContentTextArea.clear());

        // 创建一个按钮，用于关闭应用程序
        Button closeButton = new Button("Close");
        closeButton.setPrefHeight(64);
        closeButton.setOnAction(e -> primaryStage.close());

        // 将按钮添加到网格中
        grid.add(selectButton, 0, 0);
        //grid.add(clearButton, 1, 1);

        GridPane.setHalignment(closeButton, HPos.RIGHT);
        grid.add(closeButton, 0, 2);

        // 创建场景并将其设置为主要舞台的场景
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);

        // 绑定鼠标事件
        grid.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        grid.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // 显示主要舞台
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void fileContentTextArea(File selectedFile){
//        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
//            String content = "";
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content += line + "\n";
//            }
//            fileContentTextArea.setText(content);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        try {
            fileContentTextArea.setText(getDigestSM3(selectedFile));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String getDigestSM3(File file) throws Exception {
        try (InputStream fis = new FileInputStream(file)) {
            byte buffer[] = new byte[1024];
            MessageDigest md = MessageDigest.getInstance("SM3");
            for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
                md.update(buffer, 0, numRead);
            }
            return Hex.toHexString(md.digest());
//            int num = Integer.parseInt(new String(md.digest()), 2);
//            return Integer.toHexString(num);
        }
    }

}