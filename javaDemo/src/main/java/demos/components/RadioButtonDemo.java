 package demos.components;

 import com.jfoenix.controls.JFXRadioButton;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.scene.Scene;
 import javafx.scene.control.ToggleGroup;
 import javafx.scene.layout.HBox;
 import javafx.scene.layout.VBox;
 import javafx.stage.Stage;

 public class RadioButtonDemo
   extends Application {
   public void start(Stage primaryStage) {
     ToggleGroup group = new ToggleGroup();

     JFXRadioButton javaRadio = new JFXRadioButton("JavaFX");
     javaRadio.setPadding(new Insets(10.0D));
     javaRadio.setToggleGroup(group);

     JFXRadioButton jfxRadio = new JFXRadioButton("JFoenix");
     jfxRadio.setPadding(new Insets(10.0D));
     jfxRadio.setToggleGroup(group);


     VBox vbox = new VBox();
     vbox.getChildren().add(javaRadio);
     vbox.getChildren().add(jfxRadio);
     vbox.setSpacing(10.0D);

     HBox hbox = new HBox();
    hbox.getChildren().add(vbox);
     hbox.setSpacing(50.0D);
     hbox.setPadding(new Insets(40.0D, 10.0D, 10.0D, 120.0D));

    Scene scene = new Scene(hbox);
     primaryStage.setScene(scene);
     primaryStage.setWidth(500.0D);
     primaryStage.setHeight(400.0D);
     primaryStage.setTitle("JFX RadioButton Demo ");
     scene.getStylesheets()
       .add(RadioButtonDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());

     primaryStage.show();
   }

   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\RadioButtonDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */