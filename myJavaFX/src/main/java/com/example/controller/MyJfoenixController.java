package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyJfoenixController {

    @FXML
    private Button login;
    @FXML
    private Button closeButton;

    @FXML
    protected void login() {
    }

    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
