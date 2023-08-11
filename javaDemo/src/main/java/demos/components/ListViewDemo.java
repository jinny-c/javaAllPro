 package demos.components;

 import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

 public class ListViewDemo extends Application {
   private int counter = 0;

   private static final String ITEM = "Item ";

   @Override
   public void start(Stage stage) throws Exception {
     JFXListView<Label> list = new JFXListView();
     for (int i = 0; i < 4; i++) {
       list.getItems().add(new Label("Item " + i));
     }
     list.getStyleClass().add("mylistview");

     ListView<String> javaList = new ListView<>();
     for (int j = 0; j < 4; j++) {
       javaList.getItems().add("Item " + j);
     }

     FlowPane pane = new FlowPane();
     pane.setStyle("-fx-background-color:WHITE");

    JFXButton button3D = new JFXButton("3D");
     button3D.setOnMouseClicked(e -> list.depthProperty().set(Integer.valueOf(++this.counter % 2)));

     JFXButton buttonExpand = new JFXButton("EXPAND");
     buttonExpand.setOnMouseClicked(e -> {
           list.depthProperty().set(Integer.valueOf(1));

           list.setExpanded(Boolean.valueOf(true));
         });
     JFXButton buttonCollapse = new JFXButton("COLLAPSE");
     buttonCollapse.setOnMouseClicked(e -> {
           list.depthProperty().set(Integer.valueOf(1));

           list.setExpanded(Boolean.valueOf(false));
         });
     pane.getChildren().add(button3D);
     pane.getChildren().add(buttonExpand);
     pane.getChildren().add(buttonCollapse);

     AnchorPane listsPane = new AnchorPane();
     listsPane.getChildren().add(list);
     AnchorPane.setLeftAnchor((Node)list, Double.valueOf(20.0D));
     listsPane.getChildren().add(javaList);
     AnchorPane.setLeftAnchor(javaList, Double.valueOf(300.0D));

     VBox box = new VBox();
     box.getChildren().add(pane);
     box.getChildren().add(listsPane);
     box.setSpacing(40.0D);

     StackPane main = new StackPane();
     main.getChildren().add(box);
     main.setBackground(new Background(new BackgroundFill[] { new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY) }));
     StackPane.setMargin(pane, new Insets(20.0D, 0.0D, 0.0D, 20.0D));

     Scene scene = new Scene(main, 600.0D, 600.0D, Color.WHITE);
     stage.setTitle("JFX ListView Demo ");
     scene.getStylesheets().add(ListViewDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();
   }

   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\ListViewDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */