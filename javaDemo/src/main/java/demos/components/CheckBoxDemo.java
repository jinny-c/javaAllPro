 package demos.components;

 import com.jfoenix.controls.JFXCheckBox;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.scene.Scene;
 import javafx.scene.control.CheckBox;
 import javafx.scene.layout.FlowPane;
 import javafx.scene.layout.StackPane;
 import javafx.stage.Stage;

 public class CheckBoxDemo
   extends Application {
   private static int step = 1;



   public void start(Stage stage) {
     FlowPane main = new FlowPane();
     main.setVgap(20.0D);
     main.setHgap(20.0D);

     CheckBox cb = new CheckBox("CheckBox");
     JFXCheckBox jfxCheckBox = new JFXCheckBox("JFX CheckBox");
     JFXCheckBox customJFXCheckBox = new JFXCheckBox("Custom JFX CheckBox");
     customJFXCheckBox.getStyleClass().add("custom-jfx-check-box");

     main.getChildren().add(cb);
     main.getChildren().add(jfxCheckBox);
     main.getChildren().add(customJFXCheckBox);

    StackPane pane = new StackPane();
     pane.getChildren().add(main);
     StackPane.setMargin(main, new Insets(100.0D));
     pane.setStyle("-fx-background-color:WHITE");

     Scene scene = new Scene(pane, 600.0D, 200.0D);
     scene.getStylesheets().add(CheckBoxDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
     stage.setTitle("JFX CheckBox Demo ");
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();
   }


   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\CheckBoxDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */