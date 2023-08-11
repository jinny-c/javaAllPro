// Source code is decompiled from a .class file using FernFlower decompiler.
package demos.components;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProgressBarDemo extends Application {
    public ProgressBarDemo() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox pane = new VBox();
        pane.setSpacing(30.0);
        pane.setStyle("-fx-background-color:WHITE");
        ProgressBar bar = new ProgressBar();
        bar.setPrefWidth(500.0);
        ProgressBar cssBar = new ProgressBar();
        cssBar.setPrefWidth(500.0);
        cssBar.setProgress(-1.0);
        JFXProgressBar jfxBar = new JFXProgressBar();
        jfxBar.setPrefWidth(500.0);
        JFXProgressBar jfxBarInf = new JFXProgressBar();
        jfxBarInf.setPrefWidth(500.0);
        jfxBarInf.setProgress(-1.0);
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(bar.progressProperty(), 0), new KeyValue(jfxBar.secondaryProgressProperty(), 0), new KeyValue(jfxBar.progressProperty(), 0)}), new KeyFrame(Duration.seconds(1.0), new KeyValue[]{new KeyValue(jfxBar.secondaryProgressProperty(), 1)}), new KeyFrame(Duration.seconds(2.0), new KeyValue[]{new KeyValue(bar.progressProperty(), 1), new KeyValue(jfxBar.progressProperty(), 1)})});
        timeline.setCycleCount(-1);
        timeline.play();
        pane.getChildren().addAll(new Node[]{bar, jfxBar, cssBar, jfxBarInf});
        StackPane main = new StackPane();
        main.getChildren().add(pane);
        main.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)}));
        StackPane.setMargin(pane, new Insets(20.0, 0.0, 0.0, 20.0));
        Scene scene = new Scene(main, 600.0, 200.0, Color.WHITE);
        scene.getStylesheets().add(ProgressBarDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setTitle("JFX ProgressBar Demo ");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
