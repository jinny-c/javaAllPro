 package demos.components;

 import com.jfoenix.controls.JFXButton;
 import com.jfoenix.controls.JFXListView;
 import com.jfoenix.controls.JFXScrollPane;
 import com.jfoenix.svg.SVGGlyph;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.scene.Node;
 import javafx.scene.Scene;
 import javafx.scene.control.Label;
 import javafx.scene.control.ScrollPane;
 import javafx.scene.layout.StackPane;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;

 public class ScrollPaneDemo
   extends Application {
   @Override
   public void start(Stage stage) {
     JFXListView<Label> list = new JFXListView();
     for (int i = 0; i < 100; i++) {
       list.getItems().add(new Label("Item " + i));
     }
     list.getStyleClass().add("mylistview");
     list.setMaxHeight(3400.0D);


     StackPane container = new StackPane(new Node[] { (Node)list });
     container.setPadding(new Insets(24.0D));

     JFXScrollPane pane = new JFXScrollPane();
    pane.setContent(container);

     JFXButton button = new JFXButton("");
     SVGGlyph arrow = new SVGGlyph(0, "FULLSCREEN", "M402.746 877.254l-320-320c-24.994-24.992-24.994-65.516 0-90.51l320-320c24.994-24.992 65.516-24.992 90.51 0 24.994 24.994 24.994 65.516 0 90.51l-210.746 210.746h613.49c35.346 0 64 28.654 64 64s-28.654 64-64 64h-613.49l210.746 210.746c12.496 12.496 18.744 28.876 18.744 45.254s-6.248 32.758-18.744 45.254c-24.994 24.994-65.516 24.994-90.51 0z", Color.WHITE);





     arrow.setSize(20.0D, 16.0D);
     button.setGraphic((Node)arrow);
     button.setRipplerFill(Color.WHITE);
     pane.getTopBar().getChildren().add(button);

     Label title = new Label("Title");
     pane.getBottomBar().getChildren().add(title);
     title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
       JFXScrollPane.smoothScrolling((ScrollPane)pane.getChildren().get(0));

     StackPane.setMargin(title, new Insets(0.0D, 0.0D, 0.0D, 80.0D));
     StackPane.setAlignment(title, Pos.CENTER_LEFT);
     StackPane.setAlignment((Node)button, Pos.CENTER_LEFT);
     StackPane.setMargin((Node)button, new Insets(0.0D, 0.0D, 0.0D, 20.0D));


       Scene scene = new Scene(new StackPane(new Node[]{pane}), 600.0, 600.0, Color.WHITE);
     stage.setTitle("JFX ListView Demo ");
     stage.setScene(scene);
     stage.show();
   }

   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ScrollPaneDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */