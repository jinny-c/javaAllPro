package demos.components.base;

import com.jfoenix.controls.JFXDecorator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Builder;

public abstract class Overdrive extends Application implements Builder<Node> {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        for (int i = 0; i < 4000; i++) {
            Label child = new Label("label" + i);
            child.setLayoutX(Math.random() * 500.0D + 100.0D);
            child.setLayoutY(Math.random() * 500.0D + 100.0D);
            root.getChildren().add(child);
        }

        root.getChildren().add(build());
        FPSDecorator decorator = new FPSDecorator(stage, root);
        Scene scene = new Scene((Parent) decorator, 800.0D, 800.0D);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        afterShow(stage);
    }


    protected void afterShow(Stage stage) {
    }


    public static void main(String[] args) {
        launch(args);
    }

    class FPSDecorator
            extends JFXDecorator {
        private final long[] frameTimes = new long[100];
        private int frameTimeIndex = 0;

        public FPSDecorator(Stage stage, Node node) {
            super(stage, node);
            final Text fpsLabel = new Text();
            fpsLabel.setFill(Color.WHITE);
            AnimationTimer frameRateMeter = new AnimationTimer() {
                public void handle(long now) {
                    long oldFrameTime = Overdrive.FPSDecorator.this.frameTimes[Overdrive.FPSDecorator.this.frameTimeIndex];
                    Overdrive.FPSDecorator.this.frameTimes[Overdrive.FPSDecorator.this.frameTimeIndex] = now;
                    Overdrive.FPSDecorator.this.frameTimeIndex = (Overdrive.FPSDecorator.this.frameTimeIndex + 1) % Overdrive.FPSDecorator.this.frameTimes.length;
                    if (Overdrive.FPSDecorator.this.frameTimeIndex == 0) {
                        Overdrive.FPSDecorator.this.arrayFilled = true;
                    }
                    if (Overdrive.FPSDecorator.this.arrayFilled) {
                        long elapsedNanos = now - oldFrameTime;
                        long elapsedNanosPerFrame = elapsedNanos / Overdrive.FPSDecorator.this.frameTimes.length;
                        double frameRate = 1.0E9D / elapsedNanosPerFrame;
                        fpsLabel.setText(String.format("Current frame rate: %.3f", new Object[]{Double.valueOf(frameRate)}));
                    }
                }
            };
            frameRateMeter.start();
            setGraphic(fpsLabel);
        }

        private boolean arrayFilled = false;
    }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\base\Overdrive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */