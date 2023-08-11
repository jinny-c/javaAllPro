 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXDatePicker;
 import com.jfoenix.controls.JFXTimePicker;
 import io.datafx.controller.ViewController;
 import javafx.fxml.FXML;
 import javafx.scene.layout.StackPane;
 import javax.annotation.PostConstruct;


 @ViewController(value = "/fxml/ui/Pickers.fxml", title = "Material Design Example")
 public class PickersController
 {
   @FXML
   private StackPane root;
   @FXML
   private JFXDatePicker dateOverlay;
   @FXML
   private JFXTimePicker timeOverlay;

   @PostConstruct
   public void init() {
     this.dateOverlay.setDialogParent(this.root);
     this.timeOverlay.setDialogParent(this.root);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\PickersController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */