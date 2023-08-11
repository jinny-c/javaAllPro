 package demos.components;

 import com.jfoenix.controls.JFXHamburger;
 import com.jfoenix.controls.JFXListView;
 import com.jfoenix.controls.JFXPopup;
 import com.jfoenix.controls.JFXRippler;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.scene.Node;
 import javafx.scene.Scene;
 import javafx.scene.control.Label;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.layout.Region;
 import javafx.scene.layout.StackPane;
 import javafx.stage.Stage;



 public class PopupDemo
   extends Application
 {
   public void start(Stage primaryStage) throws Exception {
     JFXHamburger show = new JFXHamburger();
     show.setPadding(new Insets(10.0D, 5.0D, 10.0D, 5.0D));
     JFXRippler rippler = new JFXRippler((Node)show, JFXRippler.RipplerMask.CIRCLE, JFXRippler.RipplerPos.BACK);

     JFXListView<Label> list = new JFXListView();
     for (int i = 1; i < 5; i++) {
       list.getItems().add(new Label("Item " + i));
     }

     AnchorPane container = new AnchorPane();
     container.getChildren().add(rippler);
     AnchorPane.setLeftAnchor((Node)rippler, Double.valueOf(200.0D));
    AnchorPane.setTopAnchor((Node)rippler, Double.valueOf(210.0D));

     StackPane main = new StackPane();
     main.getChildren().add(container);

     JFXPopup popup = new JFXPopup((Region)list);
     rippler.setOnMouseClicked(e -> popup.show((Node)rippler, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));

     Scene scene = new Scene(main, 800.0D, 800.0D);
     scene.getStylesheets().add(PopupDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());

     primaryStage.setTitle("JFX Popup Demo");
     primaryStage.setScene(scene);
     primaryStage.setResizable(false);
     primaryStage.show();
   }

   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\PopupDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */