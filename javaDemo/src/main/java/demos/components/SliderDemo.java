package demos.components;

import com.jfoenix.controls.JFXSlider;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SliderDemo
        extends Application {
    @Override
    public void start(Stage stage) {
        JFXSlider horLeftSlider = new JFXSlider();
        horLeftSlider.setMinWidth(500.0D);

        JFXSlider horRightSlider = new JFXSlider();
        horRightSlider.setMinWidth(500.0D);
        horRightSlider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);

        JFXSlider verLeftSlider = new JFXSlider();
        verLeftSlider.setMinHeight(500.0D);
        verLeftSlider.setOrientation(Orientation.VERTICAL);

        JFXSlider verRightSlider = new JFXSlider();
        verRightSlider.setMinHeight(500.0D);
        verRightSlider.setOrientation(Orientation.VERTICAL);
        verRightSlider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);

        HBox hbox = new HBox();
        hbox.setSpacing(450.0D);
        hbox.getChildren().addAll(new Node[]{(Node) verRightSlider, (Node) verLeftSlider});

        VBox vbox = new VBox();
        vbox.getChildren().addAll(new Node[]{(Node) horRightSlider, (Node) horLeftSlider, hbox});
        vbox.setSpacing(100.0D);
        vbox.setPadding(new Insets(100.0D, 50.0D, 50.0D, 150.0D));

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().add(vbox);
        scene.getStylesheets().add(SliderDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setScene(scene);
        stage.setWidth(900.0D);
        stage.setHeight(900.0D);
        stage.show();
        stage.setTitle("JFX Slider Demo");
    }


    public static void main(String[] args) {
        launch(args);
    }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\SliderDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */