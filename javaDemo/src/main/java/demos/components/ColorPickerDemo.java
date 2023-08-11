 package demos.components;

 import com.jfoenix.controls.JFXColorPicker;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.scene.Scene;
 import javafx.scene.control.ColorPicker;
 import javafx.scene.layout.FlowPane;
 import javafx.scene.layout.StackPane;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;


 public class ColorPickerDemo
   extends Application
 {
   public void start(Stage stage) {
     FlowPane main = new FlowPane();
     main.setVgap(20.0D);
     main.setHgap(20.0D);

     ColorPicker picker = new ColorPicker(Color.RED);
     picker.getStyleClass().add("button");

     main.getChildren().add(picker);

     main.getChildren().add(new JFXColorPicker());

     StackPane pane = new StackPane();
     pane.getChildren().add(main);
     StackPane.setMargin(main, new Insets(100.0D));
    pane.setStyle("-fx-background-color:WHITE");

     Scene scene = new Scene(pane, 800.0D, 200.0D);

    stage.setTitle("JFX Button Demo");
     stage.setScene(scene);
     stage.show();
   }


   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ColorPickerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */