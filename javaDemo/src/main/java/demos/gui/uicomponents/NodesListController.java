 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXButton;
 import com.jfoenix.controls.JFXNodesList;
 import io.datafx.controller.ViewController;
 import javafx.fxml.FXML;





 @ViewController(value = "/fxml/ui/NodesList.fxml", title = "Material Design Example")
 public class NodesListController
 {
   @FXML
   JFXNodesList nodesList;
   @FXML
   JFXButton newButton;
   @FXML
   JFXButton fileButton;
   @FXML
   JFXButton commentButton;
   @FXML
   JFXButton filterButton;

   @FXML
   public void onNewFile() {
     System.out.println("New File");

     this.nodesList.animateList(false);
   }

   @FXML
   public void onNewComment() {
     System.out.println("New Comment");

     this.nodesList.animateList(false);
   }

   @FXML
   public void onNewFilter() {
     System.out.println("New Filter");

     this.nodesList.animateList(false);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\NodesListController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */