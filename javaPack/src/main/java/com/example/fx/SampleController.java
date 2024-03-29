package com.example.fx;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class SampleController {

    @FXML
    private Button closeButton;

    public void initialize() {
        BorderPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(closeButton, new Insets(10));
    }

    @FXML
    protected void fileDigestButtonClick() {
        FileDigestView view = new FileDigestView();
        view.show();
    }

    @FXML
    protected void base64ButtonClick() {
        Base64View view = new Base64View();
        view.show();
    }
    @FXML
    protected void readExcelAppMainButtonClick() {
        try {
            ReadExcelAppMain myMainApp = new ReadExcelAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }
    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
