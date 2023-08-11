 package demos.components;

 import com.jfoenix.controls.JFXRippler;
import com.jfoenix.effects.JFXDepthManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

 public class RipplerDemo extends Application {
   private static int counter = 0; private static final String FX_BACKGROUND_COLOR_WHITE = "-fx-background-color:WHITE;";
   private static int step = 1;




   public void start(Stage stage) {
     FlowPane main = new FlowPane();
     main.setVgap(20.0D);
     main.setHgap(20.0D);

     Label label = new Label("Click Me");
     label.setStyle("-fx-background-color:WHITE;");
     label.setPadding(new Insets(20.0D));
     JFXRippler rippler = new JFXRippler(label);
     rippler.setEnabled(false);
    main.getChildren().add(rippler);

     label.setOnMousePressed(e -> {
           if (counter == 5) {
             step = -1;
           } else if (counter == 0) {
             step = 1;
           }

           JFXDepthManager.setDepth(label, counter += step % JFXDepthManager.getLevels());
         });
     Label l1 = new Label("TEST");
     l1.setStyle("-fx-background-color:WHITE;");
     l1.setPadding(new Insets(20.0D));
     JFXRippler rippler1 = new JFXRippler(l1);
     main.getChildren().add(rippler1);
     JFXDepthManager.setDepth((Node)rippler1, 1);

     Label l2 = new Label("TEST1");
     l2.setStyle("-fx-background-color:WHITE;");
     l2.setPadding(new Insets(20.0D));
     JFXRippler rippler2 = new JFXRippler(l2);
     main.getChildren().add(rippler2);
     JFXDepthManager.setDepth((Node)rippler2, 2);


     Label l3 = new Label("TEST2");
     l3.setStyle("-fx-background-color:WHITE;");
     l3.setPadding(new Insets(20.0D));
     JFXRippler rippler3 = new JFXRippler(l3);
     main.getChildren().add(rippler3);
     JFXDepthManager.setDepth((Node)rippler3, 3);

     Label l4 = new Label("TEST3");
     l4.setStyle("-fx-background-color:WHITE;");
     l4.setPadding(new Insets(20.0D));
     JFXRippler rippler4 = new JFXRippler(l4);
     main.getChildren().add(rippler4);
     JFXDepthManager.setDepth((Node)rippler4, 4);

     Label l5 = new Label("TEST4");
     l5.setStyle("-fx-background-color:WHITE;");
     l5.setPadding(new Insets(20.0D));
     JFXRippler rippler5 = new JFXRippler(l5);
     main.getChildren().add(rippler5);
     JFXDepthManager.setDepth((Node)rippler5, 5);

     StackPane pane = new StackPane();
     pane.getChildren().add(main);
     StackPane.setMargin(main, new Insets(100.0D));
     pane.setStyle("-fx-background-color:WHITE");

     Scene scene = new Scene(pane, 600.0D, 400.0D);

     stage.setTitle("JavaFX Ripple effect and shadows ");
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();
   }


   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\RipplerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */