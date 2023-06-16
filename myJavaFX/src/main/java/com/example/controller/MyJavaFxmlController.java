package com.example.controller;

import com.example.app.LotteryControllerAppMain;
import com.example.app.MyAppMain;
import com.example.app.MyControllerAppMain;
import com.example.app.calculator.CalculatorApplication;
import com.example.view.Base64View;
import com.example.view.FileDigestView;
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
public class MyJavaFxmlController {

    @FXML
    private Button closeButton;

    public void initialize() {
        BorderPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(closeButton, new Insets(10));
    }

    @FXML
    protected void openFileButtonClick() {
        FileDigestView view = new FileDigestView();
        view.show();
    }

    @FXML
    protected void base64ButtonClick() {
        Base64View view = new Base64View();
        view.show();
    }

    @FXML
    protected void aloneWindowButtonClick() {
        try {
            CalculatorApplication myMainApp = new CalculatorApplication();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }

    @FXML
    protected void fxmlWindowButtonClick() {
        try {
            MyControllerAppMain myMainApp = new MyControllerAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }
    @FXML
    protected void lotteryButtonClick() {
        try {
            LotteryControllerAppMain myMainApp = new LotteryControllerAppMain();
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
