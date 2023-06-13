package com.example.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class LotteryController {
    @FXML
    private Button closeButton;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea contentTextArea;

    public void initialize() {
        BorderPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(closeButton, new Insets(10));

        comboBox.getItems().addAll("2", "4", "6", "10", "12", "18");
        //comboBox.setValue("2");
        comboBox.getSelectionModel().select(1); // 将索引为1（即"2"）的选项设置为默认选项

    }

    @FXML
    protected void selectButtonClick() {
        String periods = comboBox.getValue();
        contentTextArea.setText(periods);
    }
    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
