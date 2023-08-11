 package demos.components;

 import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXToggleNode;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

 public class ToggleButtonDemo
   extends Application {
   public void start(Stage stage) throws Exception {
     VBox pane = new VBox();
     pane.setSpacing(30.0D);
     pane.setStyle("-fx-background-color:#EEE; -fx-padding: 40;");

     ToggleButton button = new ToggleButton("JavaFx Toggle");
     pane.getChildren().add(button);

     JFXToggleButton toggleButton = new JFXToggleButton();
     toggleButton.setText("New Skin");
     pane.getChildren().add(toggleButton);

     JFXToggleNode node = new JFXToggleNode();
     node.setStyle("-fx-padding: 10");
    node.setGraphic((Node)new FontIcon((Ikon)FontAwesomeSolid.HEART));

     pane.getChildren().add(node);


     Scene scene = new Scene(pane, 600.0D, 400.0D, Color.valueOf("#EEE"));
     stage.setTitle("JFX Toggle Button Demo ");
     scene.getStylesheets()
       .add(ToggleButtonDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();
   }



   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ToggleButtonDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */