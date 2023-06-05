package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class Controller {
    @FXML
    private Label label;

    @FXML
    protected void onHelloButtonClick() {
        label.setText("Hello World!");
    }
}
