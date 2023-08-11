 package demos.components;

 import com.jfoenix.controls.JFXChipView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

 public final class ChipViewDemo
   extends Application {
   public void start(Stage stage) {
     JFXChipView<String> chipView = new JFXChipView();
     chipView.getChips().addAll(new String[] { "WEF", "WWW", "JD" });
     chipView.getSuggestions().addAll(new String[] { "HELLO", "TROLL", "WFEWEF", "WEF" });
     chipView.setStyle("-fx-background-color: WHITE;");

     StackPane pane = new StackPane();
     pane.getChildren().add(chipView);
     StackPane.setMargin((Node)chipView, new Insets(100.0D));
     pane.setStyle("-fx-background-color:GRAY;");

     Scene scene = new Scene(pane, 500.0D, 500.0D);

     stage.setTitle("JFX Button Demo");
     stage.setScene(scene);
     stage.show();
   }


   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ChipViewDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */