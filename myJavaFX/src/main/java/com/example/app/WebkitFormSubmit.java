package com.example.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebkitFormSubmit extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load("https://www.itshang.com/as/35285/19799725.html");

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.toString().equals("SUCCEEDED")) {
                // Use JavaScript to fill out and submit the form
                webEngine.executeScript("document.getElementById('inputId').value='Some Value';");
                webEngine.executeScript("document.getElementById('formId').submit();");
            }
        });

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.show();
    }
}