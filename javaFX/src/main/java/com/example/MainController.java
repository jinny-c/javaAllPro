package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private Label helloLabel;

    @FXML
    protected void onHelloButtonClick() {
        helloLabel.setText("Hello, JavaFX!");
    }
}
