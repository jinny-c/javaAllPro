package com.example.app.demo.jfoenix.controller.ui;

import com.jfoenix.controls.JFXProgressBar;
import io.datafx.controller.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.util.Duration;

import javax.annotation.PostConstruct;

@ViewController(
        value = "/fxml/ui/ProgressBar.fxml",
        title = "Material Design Example"
)
public class ProgressBarController {
    @FXML
    private JFXProgressBar progress1;
    @FXML
    private JFXProgressBar progress2;

    public ProgressBarController() {
    }

    @PostConstruct
    public void init() {
        Timeline task = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.progress1.progressProperty(), 0), new KeyValue(this.progress2.progressProperty(), 0), new KeyValue(this.progress2.secondaryProgressProperty(), 0.5)}), new KeyFrame(Duration.seconds(1.0), new KeyValue[]{new KeyValue(this.progress2.secondaryProgressProperty(), 1)}), new KeyFrame(Duration.seconds(2.0), new KeyValue[]{new KeyValue(this.progress1.progressProperty(), 1), new KeyValue(this.progress2.progressProperty(), 1)})});
        task.setCycleCount(-1);
        task.play();
    }
}
