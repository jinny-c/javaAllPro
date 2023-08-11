 package demos.gui.uicomponents;

 import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDefaultChip;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.util.StringConverter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Objects;


 @ViewController(value = "/fxml/ui/ChipView.fxml", title = "Material Design Example")
 public class ChipViewController
 {
   @PostConstruct
   public void init() throws Exception {
     this.chipView.setChipFactory((emailJFXChipView, email) -> new JFXDefaultChip<MyShape>(emailJFXChipView, email)
         {

         });





     final HashMap<String, MyShape> suggestions = new HashMap<>();
    suggestions.put("Glass", new MyShape("Glass", (Node)SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.glass")));
     suggestions.put("Star", new MyShape("Star", (Node)SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.star")));
     suggestions.put("Music", new MyShape("Music", (Node)SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.music")));
     SVGGlyph icoMoonGlyph = SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.heart");
     icoMoonGlyph.getStyleClass().add("heart");
     suggestions.put("Heart", new MyShape("Heart", (Node)icoMoonGlyph));
     suggestions.put("Film", new MyShape("Film", (Node)SVGGlyphLoader.getIcoMoonGlyph("icomoon.svg.film")));

     this.chipView.setConverter(new StringConverter<MyShape>()
         {
           public String toString(ChipViewController.MyShape object) {
             return object.toString();
           }


           public ChipViewController.MyShape fromString(String string) {
             ChipViewController.MyShape found = (ChipViewController.MyShape)suggestions.get(string);
             return (found == null) ? new ChipViewController.MyShape(string, null) : found;
           }
         });
     this.chipView.getSuggestions().addAll(suggestions.values());
     this.chipView.setSuggestionsCellFactory(param -> new JFXListCell());

     JFXDepthManager.setDepth((Node)this.chipView, 2);
   }
   @FXML
   private JFXChipView<MyShape> chipView;
   class MyShape { String name;
     Node image;

     public MyShape(String name, Node image) {
       this.name = name;
       this.image = image;
     }


     public String toString() {
       return this.name;
     }


     public boolean equals(Object o) {
       if (this == o) {
         return true;
       }
       if (o == null || getClass() != o.getClass()) {
         return false;
       }
       MyShape email1 = (MyShape)o;
       return Objects.equals(this.name, email1.name);
     }


     public int hashCode() {
       return Objects.hash(new Object[] { this.name });
     } }

 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gu\\uicomponents\ChipViewController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */