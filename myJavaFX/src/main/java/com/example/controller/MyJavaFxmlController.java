package com.example.controller;

import com.example.app.*;
import com.example.app.calculator.CalculatorApplication;
import com.example.app.demo.jfoenix.MainDemo;
import com.example.view.Base64View;
import com.example.view.FileDigestView;
import com.xwintop.HttpToolMain;
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
            TableDemoControllerAppMain myMainApp = new TableDemoControllerAppMain();
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
    protected void ballGetButtonClick() {
        try {
            BallGetAppMain myMainApp = new BallGetAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }

    @FXML
    protected void myBallGetButtonClick() {
        try {
            MyBallGetAppMain myMainApp = new MyBallGetAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }

    @FXML
    protected void pageContentGetButtonClick() {
        try {
            PageContentGetControllerAppMain myMainApp = new PageContentGetControllerAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }
    @FXML
    protected void contentProcessingButtonClick() {
        try {
            StringUtilsControllerAppMain myMainApp = new StringUtilsControllerAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }
    @FXML
    protected void jfoenixDemoButtonClick() {
        try {
            MainDemo myMainApp = new MainDemo();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }
    @FXML
    protected void httpToolDemoButtonClick() {
        try {
            HttpToolMain myMainApp = new HttpToolMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
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
    protected void openPlantReadExcelAppMain() {
        try {
            OpenPlantReadExcelAppMain myMainApp = new OpenPlantReadExcelAppMain();
            myMainApp.start(new Stage());
        } catch (Exception e) {
        }
    }

    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

}
