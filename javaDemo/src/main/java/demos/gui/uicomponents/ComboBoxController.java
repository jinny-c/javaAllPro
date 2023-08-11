 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXComboBox;
 import io.datafx.controller.ViewController;
 import javafx.beans.value.ChangeListener;
 import javafx.beans.value.ObservableValue;
 import javafx.fxml.FXML;
 import javafx.scene.control.Label;
 import javafx.util.StringConverter;
 import javax.annotation.PostConstruct;





 @ViewController(value = "/fxml/ui/Combobox.fxml", title = "Material Design Example")
 public class ComboBoxController
 {
   @FXML
   private JFXComboBox<Label> jfxComboBox;
   @FXML
   private JFXComboBox<Label> jfxEditableComboBox;

   @PostConstruct
   public void init() {
     this.jfxComboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
           if (!newVal.booleanValue()) {
             this.jfxComboBox.validate();
           }
         });

    ChangeListener<? super Boolean> comboBoxFocus = (o, oldVal, newVal) -> {
         if (!newVal.booleanValue()) {
           this.jfxEditableComboBox.validate();
         }
       };
     this.jfxEditableComboBox.focusedProperty().addListener(comboBoxFocus);
     this.jfxEditableComboBox.getEditor().focusedProperty().addListener(comboBoxFocus);
     this.jfxEditableComboBox.setConverter(new StringConverter<Label>()
         {
           public String toString(Label object) {
             return (object == null) ? "" : object.getText();
           }

           public Label fromString(String string) {
             return (string == null || string.isEmpty()) ? null : new Label(string);
           }
         });
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\ComboBoxController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */