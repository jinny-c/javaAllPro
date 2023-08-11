 package demos.components;

 import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

 public class ComboBoxDemo
   extends Application {
   public void start(Stage primaryStage) throws Exception {
     JFXComboBox<Label> combo = new JFXComboBox();
     combo.getItems().add(new Label("Java 1.8"));
     combo.getItems().add(new Label("Java 1.7"));
     combo.getItems().add(new Label("Java 1.6"));
     combo.getItems().add(new Label("Java 1.5"));
     combo.setEditable(true);
     combo.setPromptText("Select Java Version");
     combo.setConverter(new StringConverter<Label>()
         {
           public String toString(Label object) {
             return (object == null) ? "" : object.getText();
           }


           public Label fromString(String string) {
             return new Label(string);
           }
         });

     HBox pane = new HBox(100.0D);
    HBox.setMargin((Node)combo, new Insets(20.0D));
     pane.setStyle("-fx-background-color:WHITE");
     pane.getChildren().add(combo);

     Scene scene = new Scene(pane, 300.0D, 300.0D);
     scene.getStylesheets().add(ComboBoxDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());

     primaryStage.setTitle("JFX ComboBox Demo");
     primaryStage.setScene(scene);
     primaryStage.setResizable(false);
     primaryStage.show();
   }

   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ComboBoxDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */