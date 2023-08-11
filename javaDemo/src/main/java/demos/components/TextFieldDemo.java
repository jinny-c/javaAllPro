 package demos.components;

 import com.jfoenix.controls.JFXPasswordField;
 import com.jfoenix.controls.JFXTextField;
 import com.jfoenix.validation.RequiredFieldValidator;
 import javafx.application.Application;
 import javafx.beans.value.ObservableValue;
 import javafx.scene.Node;
 import javafx.scene.Scene;
 import javafx.scene.control.TextField;
 import javafx.scene.layout.VBox;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;
 import org.kordamp.ikonli.Ikon;
 import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
 import org.kordamp.ikonli.javafx.FontIcon;

 public class TextFieldDemo extends Application {
   private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
   private static final String ERROR = "error";

   public void start(Stage stage) throws Exception {
     VBox pane = new VBox();
     pane.setSpacing(30.0D);
     pane.setStyle("-fx-background-color:WHITE;-fx-padding:40;");

     pane.getChildren().add(new TextField());

     JFXTextField field = new JFXTextField();
     field.setLabelFloat(true);
     field.setPromptText("Type Something");
    pane.getChildren().add(field);


     JFXTextField disabledField = new JFXTextField();
    disabledField.setStyle("-fx-label-float:true;");
     disabledField.setPromptText("I'm disabled..");
     disabledField.setDisable(true);
     pane.getChildren().add(disabledField);

     JFXTextField validationField = new JFXTextField();

     validationField.setPromptText("With Validation..");
     RequiredFieldValidator validator = new RequiredFieldValidator();
     validator.setMessage("Input Required");
     FontIcon warnIcon = new FontIcon((Ikon)FontAwesomeSolid.EXCLAMATION_TRIANGLE);
     warnIcon.getStyleClass().add("error");
     validator.setIcon((Node)warnIcon);
     validationField.getValidators().add(validator);
     validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             validationField.validate();
           }
         });
     pane.getChildren().add(validationField);


     JFXPasswordField passwordField = new JFXPasswordField();
     passwordField.setStyle("-fx-label-float:true;");
     passwordField.setPromptText("Password");
     validator = new RequiredFieldValidator();
     validator.setMessage("Password Can't be empty");
     warnIcon = new FontIcon((Ikon)FontAwesomeSolid.EXCLAMATION_TRIANGLE);
     warnIcon.getStyleClass().add("error");
     validator.setIcon((Node)warnIcon);
     passwordField.getValidators().add(validator);
     passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             passwordField.validate();
           }
         });
     pane.getChildren().add(passwordField);

     Scene scene = new Scene(pane, 600.0D, 400.0D, Color.WHITE);
     scene.getStylesheets()
       .add(TextFieldDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
     stage.setTitle("JFX TextField Demo ");
     stage.setScene(scene);
     stage.setResizable(false);
     stage.show();
   }



   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\TextFieldDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */