package com.example.app.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * @description 可改变字体大小
 *
 * @auth chaijd
 * @date 2023/8/11
 */
public class AdjustableFontSizeTextArea extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextArea textArea = new TextArea("Sample Text in TextArea");
        Slider fontSizeSlider = new Slider(10, 50, 14);  // 最小值10，最大值50，默认值14
        Label label = new Label("Adjust Font Size:");

        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setFont(Font.font(newValue.doubleValue()));
        });

        root.getChildren().addAll(textArea, label, fontSizeSlider);

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Adjustable Font Size TextArea");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}