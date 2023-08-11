 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXTextField;
 import com.jfoenix.effects.JFXDepthManager;
 import com.jfoenix.utils.JFXHighlighter;
 import com.jfoenix.utils.JFXNodeUtils;
 import io.datafx.controller.ViewController;
 import javafx.fxml.FXML;
 import javafx.scene.Node;
 import javafx.scene.input.KeyEvent;
 import javafx.scene.layout.Pane;
 import javafx.util.Duration;
 import javax.annotation.PostConstruct;









 @ViewController(value = "/fxml/ui/Highlighter.fxml", title = "Material Design Example")
 public class HighlighterController
 {
   @FXML
   private JFXTextField searchField;
   @FXML
   private Pane content;
   private JFXHighlighter highlighter = new JFXHighlighter();




   @PostConstruct
   public void init() {
     JFXDepthManager.setDepth(this.content, 1);
     JFXNodeUtils.addDelayedEventHandler((Node)this.searchField, Duration.millis(400.0D), KeyEvent.KEY_PRESSED, event -> this.highlighter.highlight(this.content, this.searchField.getText()));
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\HighlighterController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */