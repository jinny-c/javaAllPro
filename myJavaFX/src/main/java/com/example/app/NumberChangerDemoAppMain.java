package com.example.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/10/31
 */
public class NumberChangerDemoAppMain extends Application {
    private TextField textField;
    private ScheduledExecutorService executorService;
    private boolean incrementing = false;
    private boolean decrementing = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("长按加减应用");

        textField = new TextField("0");
        Button addButton = new Button("+");
        Button subtractButton = new Button("-");

        addButton.setOnMousePressed(e -> startIncrementing());
        addButton.setOnMouseReleased(e -> stopIncrementing());
        subtractButton.setOnMousePressed(e -> startDecrementing());
        subtractButton.setOnMouseReleased(e -> stopDecrementing());

        VBox root = new VBox(10, addButton, textField, subtractButton);

        Scene scene = new Scene(root, 100, 150);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void startIncrementing() {
        incrementing = true;
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::incrementNumber, 0, 200, TimeUnit.MILLISECONDS);
    }

    private void stopIncrementing() {
        incrementing = false;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void startDecrementing() {
        decrementing = true;
        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::decrementNumber, 0, 200, TimeUnit.MILLISECONDS);
    }

    private void stopDecrementing() {
        decrementing = false;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void incrementNumber() {
        int currentValue = Integer.parseInt(textField.getText());
        currentValue++;
        textField.setText(String.valueOf(currentValue));
    }

    private void decrementNumber() {
        int currentValue = Integer.parseInt(textField.getText());
        currentValue--;
        textField.setText(String.valueOf(currentValue));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
