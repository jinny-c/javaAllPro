 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;




 @ViewController(value = "/fxml/ui/TextField.fxml", title = "Material Design Example")
 public class TextFieldController
 {
   @FXML
   private JFXTextField validatedText;
   @FXML
   private JFXPasswordField validatedPassword;
   @FXML
   private JFXTextArea jfxTextArea;

   @PostConstruct
   public void init() {
     this.validatedText.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             this.validatedText.validate();
           }
         });
     this.validatedPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             this.validatedPassword.validate();
           }
         });
    this.jfxTextArea.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue())
             this.jfxTextArea.validate();
         });
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\TextFieldController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */