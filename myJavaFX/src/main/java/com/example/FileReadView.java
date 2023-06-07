package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/31
 */

public class FileReadView {

    private Stage stage;
    private FileChooser fileChooser = new FileChooser();
    private TextArea textArea = new TextArea();

    public FileReadView() {
        stage = new Stage();
        stage.setTitle("File Select View");
        stage.initModality(Modality.APPLICATION_MODAL);

        Button openButton = new Button("Select File");
        openButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    Platform.runLater(() -> textArea.setText(sb.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(openButton, textArea, closeButton);

        Scene scene = new Scene(vbox, 400, 300);

        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }

    public String getText() {
        return textArea.getText();
    }
}