 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXButton;
 import com.jfoenix.controls.JFXListView;
 import io.datafx.controller.ViewController;
 import javafx.fxml.FXML;
 import javafx.scene.input.MouseEvent;
 import javax.annotation.PostConstruct;


 @ViewController(value = "/fxml/ui/ListView.fxml", title = "Material Design Example")
 public class ListViewController
 {
   @FXML
   private JFXListView<?> list1;
   @FXML
   private JFXListView<?> list2;
   @FXML
   private JFXListView<?> subList;
   @FXML
   private JFXButton button3D;
   @FXML
   private JFXButton collapse;
   @FXML
   private JFXButton expand;
   private int counter = 0;





   @PostConstruct
   public void init() {
     this.button3D.setOnMouseClicked(e -> {
           int val = ++this.counter % 2;

           this.list1.depthProperty().set(Integer.valueOf(val));
           this.list2.depthProperty().set(Integer.valueOf(val));
         });
     this.expand.setOnMouseClicked(e -> this.list2.expandedProperty().set(true));
     this.collapse.setOnMouseClicked(e -> this.list2.expandedProperty().set(false));
     this.list1.depthProperty().set(Integer.valueOf(1));
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\ListViewController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */