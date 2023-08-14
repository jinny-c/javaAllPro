// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.app.demo.jfoenix.controller.ui;

import com.jfoenix.controls.JFXSpinner;
import io.datafx.controller.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

@ViewController(
        value = "/fxml/ui/Spinner.fxml",
        title = "Material Design Example"
)
public class SpinnerController {
    @FXML
    private JFXSpinner blueSpinner;
    @FXML
    private JFXSpinner greenSpinner;

    public SpinnerController() {
    }

    @PostConstruct
    public void init() {
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.blueSpinner.progressProperty(), 0), new KeyValue(this.greenSpinner.progressProperty(), 0)}), new KeyFrame(Duration.seconds(0.5), new KeyValue[]{new KeyValue(this.greenSpinner.progressProperty(), 0.5)}), new KeyFrame(Duration.seconds(2.0), new KeyValue[]{new KeyValue(this.blueSpinner.progressProperty(), 1), new KeyValue(this.greenSpinner.progressProperty(), 1)})});
        timeline.setCycleCount(-1);
        timeline.play();
    }
}
