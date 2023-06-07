package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Base64;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */


public class Base64MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建布局
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(20);
        root.setHgap(20);

        // 创建组件
        Label label = new Label("请输入：");
//        TextField inputTxt = new TextField();
//        inputTxt.setPrefWidth(500);
//        inputTxt.setPrefHeight(50);

        TextArea inputTextArea = new TextArea();
        inputTextArea.setPrefRowCount(3); // 设置首选行数
        inputTextArea.setWrapText(true);

        Label label2 = new Label("处理结果：");
//        TextField resultTxt = new TextField();
//        resultTxt.setPrefHeight(50);

        TextArea resultTextArea = new TextArea();
        resultTextArea.setPrefRowCount(3); // 设置首选行数
        resultTextArea.setWrapText(true);
        //resultTextArea.setPrefColumnCount(20); // 设置首选列数

        Button encoderButton = new Button("Base64 Encoder");
        Button decodeButton = new Button("Base64 Decode");

        Button clearButton = new Button("Clear All");
        clearButton.setOnAction(e -> {
            inputTextArea.clear();
            resultTextArea.clear();
            label2.setText("处理结果：");
        });
        Button closeButton = new Button("关闭窗口");
        closeButton.setOnAction(e -> primaryStage.close());

        // 布局组件
        root.add(label, 0, 0);
//        root.add(inputTxt, 1, 0);
        root.add(inputTextArea, 1, 0);
        root.add(encoderButton, 0, 1);
        root.add(decodeButton, 1, 1);
        root.add(clearButton, 2, 1);
        root.add(label2, 0, 2);
        //root.add(resultTxt, 1, 2);
        root.add(resultTextArea, 1, 2);
        root.add(closeButton, 2, 3);

        // 添加事件处理
        encoderButton.setOnAction(event -> {
            try {
                resultTextArea.clear();
                label2.setText("Base64 Encoder:");
                String text = inputTextArea.getText();
                String encodedText = Base64.getEncoder().encodeToString(text.getBytes());
                resultTextArea.setText(encodedText);
            }catch (Exception e){
                resultTextArea.setText(e.getMessage());
            }
        });

        decodeButton.setOnAction(event -> {
            try {
                resultTextArea.clear();
                label2.setText("Base64 Decode:");
                String text = inputTextArea.getText();
                String decodeText = new String(Base64.getDecoder().decode(text.getBytes()));
                resultTextArea.setText(decodeText);
            }catch (Exception e){
                resultTextArea.setText(e.getMessage());
            }
        });

        // 创建场景
        Scene scene = new Scene(root);

        // 设置窗口标题
        primaryStage.setTitle("Base64 编码器");

        // 设置窗口场景
        primaryStage.setScene(scene);

        // 显示窗口
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}