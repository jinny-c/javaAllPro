package com.example.app.demo;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/8/11
 */


public class AdjustableFontSizeWithJFXSlider extends Application {

    private static final double FONT_STEP = 2.0; // 调整字体大小的步长

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextArea textArea = new TextArea("Sample Text in TextArea");
        textArea.setFont(Font.font(14));

        JFXButton increaseButton = new JFXButton("+");
        JFXButton decreaseButton = new JFXButton("-");

        JFXSlider fontSizeSlider = new JFXSlider(10, 50, 14);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(FONT_STEP);
        fontSizeSlider.setBlockIncrement(FONT_STEP);

        increaseButton.setOnAction(event -> {
            double newSize = fontSizeSlider.getValue() + FONT_STEP;
            fontSizeSlider.setValue(newSize);
            textArea.setFont(Font.font(newSize));
        });

        decreaseButton.setOnAction(event -> {
            double newSize = fontSizeSlider.getValue() - FONT_STEP;
            if (newSize >= fontSizeSlider.getMin()) {
                fontSizeSlider.setValue(newSize);
                textArea.setFont(Font.font(newSize));
            }
        });

        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setFont(Font.font(newValue.doubleValue()));
        });

        root.getChildren().addAll(textArea, increaseButton, decreaseButton, fontSizeSlider);

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Adjustable Font Size with JFXSlider");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}