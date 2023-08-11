 package demos.components;

 import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




 public class HamburgerDemo
   extends Application
 {
   public void start(Stage stage) {
     FlowPane main = new FlowPane();
     main.setVgap(20.0D);
     main.setHgap(20.0D);

     JFXHamburger h1 = new JFXHamburger();
     HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(h1);
     burgerTask.setRate(-1.0D);
     h1.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
           burgerTask.setRate(burgerTask.getRate() * -1.0D);

           burgerTask.play();
         });
     JFXHamburger h2 = new JFXHamburger();
    HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(h2);
     burgerTask1.setRate(-1.0D);
     h2.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
           burgerTask1.setRate(burgerTask1.getRate() * -1.0D);

           burgerTask1.play();
         });
     JFXHamburger h3 = new JFXHamburger();
     HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(h3);
     burgerTask2.setRate(-1.0D);
     h3.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
           burgerTask2.setRate(burgerTask2.getRate() * -1.0D);

           burgerTask2.play();
         });
     JFXHamburger h4 = new JFXHamburger();
     HamburgerNextArrowBasicTransition burgerTask3 = new HamburgerNextArrowBasicTransition(h4);
     burgerTask3.setRate(-1.0D);
     h4.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
           burgerTask3.setRate(burgerTask3.getRate() * -1.0D);

           burgerTask3.play();
         });

     main.getChildren().add(h1);
     main.getChildren().add(h2);
     main.getChildren().add(h3);
     main.getChildren().add(h4);

     StackPane pane = new StackPane();
     pane.getChildren().add(main);
     StackPane.setMargin(main, new Insets(60.0D));
     pane.setStyle("-fx-background-color:WHITE");

     Scene scene = new Scene(pane, 400.0D, 200.0D);
     scene.getStylesheets().add(HamburgerDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
     stage.setTitle("JFX Burgers Demo :) ");
     stage.setScene(scene);
     stage.setResizable(false);

     stage.show();
   }


   public static void main(String[] args) {
     launch(args);
   }
 }


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\HamburgerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */