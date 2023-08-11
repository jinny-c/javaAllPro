 package demos.components;

 import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.SecureRandom;

 public class TabsDemo extends Application {
   private static final String TAB_0 = "Tab 0";
   private static final String TAB_01 = "Tab 01";
   private static final String msg = "Tab 0";
   private final SecureRandom random = new SecureRandom();

   public static void main(String[] args) {
     Application.launch(args);
   }


   public void start(Stage primaryStage) {
     primaryStage.setTitle("Tabs");

     JFXTabPane tabPane = new JFXTabPane();

     Tab tab = new Tab();
    tab.setText("Tab 0");
     tab.setContent(new Label("Tab 0"));

     tabPane.getTabs().add(tab);
     tabPane.setPrefSize(300.0D, 200.0D);
     Tab tab1 = new Tab();
     tab1.setText("Tab 01");
     tab1.setContent(new Label("Tab 01"));

     tabPane.getTabs().add(tab1);

     SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
     selectionModel.select(1);

     JFXButton button = new JFXButton("New Tab");
     button.setOnMouseClicked(o -> {
           Tab temp = new Tab();

           int count = tabPane.getTabs().size();
           temp.setText("Tab 0" + count);
           temp.setContent(new Label("Tab 0" + count));
           tabPane.getTabs().add(temp);
         });
     tabPane.setMaxWidth(500.0D);

     HBox hbox = new HBox();
     hbox.getChildren().addAll(new Node[] { (Node)button, (Node)tabPane });
     hbox.setSpacing(50.0D);
     hbox.setAlignment(Pos.CENTER);
     hbox.setStyle("-fx-padding:20");

     Group root = new Group();
     Scene scene = new Scene(root, 700.0D, 250.0D);
     root.getChildren().addAll(new Node[] { hbox });
     scene.getStylesheets().add(TabsDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());

     primaryStage.setTitle("JFX Tabs Demo");
     primaryStage.setScene(scene);
     primaryStage.show();
   }

   public String nextSessionId() {
     return (new BigInteger(50, this.random)).toString(16);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\TabsDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */