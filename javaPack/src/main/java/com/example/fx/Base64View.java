package com.example.fx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Base64;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/31
 */

public class Base64View {

    private Stage stage;

    private double xOffset = 0;
    private double yOffset = 0;

    public Base64View() {
        stage = new Stage();
        stage.setTitle("Base64 View");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false); // 将窗口大小设置为固定不变


        // 创建布局  网格布局
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(20);
        root.setHgap(20);

        // 创建组件
        Label label = new Label("请输入：");
        TextArea inputTextArea = new TextArea();
        inputTextArea.setPrefRowCount(3); // 设置首选行数
        inputTextArea.setWrapText(true);

        Label label2 = new Label("处理结果：");
        TextArea resultTextArea = new TextArea();
        resultTextArea.setPrefRowCount(3); // 设置首选行数
        resultTextArea.setWrapText(true);

        Button encoderButton = new Button("Base64 Encoder");
        Button decodeButton = new Button("Base64 Decode");

        Button clearButton = new Button("Clear All");
        clearButton.setOnAction(e -> {
            inputTextArea.clear();
            resultTextArea.clear();
            label2.setText("处理结果：");
        });
        Button closeButton = new Button("关闭窗口");
        closeButton.setOnAction(e -> {
            //stage.close();
            e.consume();
            ConfirmDialog confirm = new ConfirmDialog(stage);
            if (confirm.showAndWait().get()) {
                stage.close();
            }
        });

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
            } catch (Exception e) {
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
            } catch (Exception e) {
                resultTextArea.setText(e.getMessage());
            }
        });


        // 创建场景
        Scene scene = new Scene(root);

        stage.setScene(scene);

        // 绑定鼠标事件
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void show() {
        stage.showAndWait();
    }

}