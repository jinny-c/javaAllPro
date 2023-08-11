package demos.components;

import com.jfoenix.animation.JFXNodesAnimation;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;



public class AnimationDemo  extends Application {
  public static final String STYLE = "-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;";

  @Override
  public void start(Stage stage) {
     FlowPane main = new FlowPane();
     main.setVgap(20.0D);
     main.setHgap(20.0D);

     StackPane colorPane = new StackPane();
/*  37 */     colorPane.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  38 */     colorPane.getStyleClass().add("red-500");
/*  39 */     main.getChildren().add(colorPane);

/*  41 */     StackPane colorPane1 = new StackPane();
/*  42 */     colorPane1.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  43 */     colorPane1.getStyleClass().add("blue-500");

/*  45 */     StackPane placeHolder = new StackPane(new Node[] { colorPane1 });
/*  46 */     placeHolder.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  47 */     main.getChildren().add(placeHolder);


/*  50 */     StackPane colorPane2 = new StackPane();
/*  51 */     colorPane2.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  52 */     colorPane2.getStyleClass().add("green-500");
/*  53 */     main.getChildren().add(colorPane2);

/*  55 */     StackPane colorPane3 = new StackPane();
/*  56 */     colorPane3.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  57 */     colorPane3.getStyleClass().add("yellow-500");
/*  58 */     main.getChildren().add(colorPane3);


/*  61 */     StackPane colorPane4 = new StackPane();
/*  62 */     colorPane4.setStyle("-fx-background-radius:50; -fx-min-width:50; -fx-min-height:50;");
/*  63 */     colorPane4.getStyleClass().add("purple-500");
/*  64 */     main.getChildren().add(colorPane4);


/*  67 */     StackPane wizard = new StackPane();
/*  68 */     wizard.getChildren().add(main);
/*  69 */     StackPane.setMargin(main, new Insets(100.0D));
/*  70 */     wizard.setStyle("-fx-background-color:WHITE");

/*  72 */     StackPane nextPage = new StackPane();

/*  74 */     StackPane newPlaceHolder = new StackPane();
/*  75 */     newPlaceHolder.setStyle("-fx-background-radius:50; -fx-max-width:50; -fx-max-height:50;");
/*  76 */     nextPage.getChildren().add(newPlaceHolder);
/*  77 */     StackPane.setAlignment(newPlaceHolder, Pos.TOP_LEFT);


/*  80 */     JFXHamburger h4 = new JFXHamburger();
/*  81 */     h4.setMaxSize(40.0D, 40.0D);
/*  82 */     HamburgerBackArrowBasicTransition burgerTask3 = new HamburgerBackArrowBasicTransition(h4);
/*  83 */     burgerTask3.setRate(-1.0D);
/*  84 */     h4.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
          burgerTask3.setRate(burgerTask3.getRate() * -1.0D);
          burgerTask3.play();
        });
/*  88 */     nextPage.getChildren().add(h4);
/*  89 */     StackPane.setAlignment((Node)h4, Pos.TOP_LEFT);
/*  90 */     StackPane.setMargin((Node)h4, new Insets(10.0D));


/*  93 */     JFXNodesAnimation<FlowPane, StackPane> animation = new FlowPaneStackPaneJFXNodesAnimation(main, nextPage, wizard, colorPane1);




/*  98 */     colorPane1.setOnMouseClicked(click -> animation.animate());

     Scene scene = new Scene(wizard, 800.0D, 200.0D);
     ObservableList<String> stylesheets = scene.getStylesheets();
     stylesheets.addAll(new String[] { ButtonDemo.class.getResource("/css/jfoenix-design.css").toExternalForm(), ButtonDemo.class
           .getResource("/css/jfoenix-components.css").toExternalForm() });
     stage.setTitle("JFX Button Demo");
     stage.setScene(scene);
     stage.show();
  }


  public static void main(String[] args) {
     launch(args);
  }

  private static final class FlowPaneStackPaneJFXNodesAnimation extends JFXNodesAnimation<FlowPane, StackPane> {
    private final Pane tempPage;
    private final FlowPane main;
    private final StackPane nextPage;
    private final StackPane wizard;
    private final StackPane colorPane1;
    private double newX;
    private double newY;

    FlowPaneStackPaneJFXNodesAnimation(FlowPane main, StackPane nextPage, StackPane wizard, StackPane colorPane1) {
       super(main, nextPage);
       this.main = main;
       this.nextPage = nextPage;
       this.wizard = wizard;
       this.colorPane1 = colorPane1;
       this.tempPage = new Pane();
       this.newX = 0.0D;
       this.newY = 0.0D;
    }


    @Override
    public void init() {
       this.nextPage.setOpacity(0.0D);
       this.wizard.getChildren().add(this.tempPage);
       this.wizard.getChildren().add(this.nextPage);
       this.newX = this.colorPane1.localToScene(this.colorPane1.getBoundsInLocal()).getMinX();
       this.newY = this.colorPane1.localToScene(this.colorPane1.getBoundsInLocal()).getMinY();
       this.tempPage.getChildren().add(this.colorPane1);
       this.colorPane1.setTranslateX(this.newX);
       this.colorPane1.setTranslateY(this.newY);
    }



    @Override
    public void end() {}



    @Override
    public Animation animateSharedNodes() {
       return new Timeline();
    }


    @Override
    public Animation animateExit() {
       Integer endValue = Integer.valueOf(0);
       return new Timeline(new KeyFrame[] { new KeyFrame(
               Duration.millis(300.0D), new KeyValue[] { new KeyValue(this.main
                   .opacityProperty(), endValue, Interpolator.EASE_BOTH) }), new KeyFrame(
               Duration.millis(520.0D), new KeyValue[] { new KeyValue(this.colorPane1
                   .translateXProperty(), endValue, Interpolator.EASE_BOTH), new KeyValue(this.colorPane1
                   .translateYProperty(), endValue, Interpolator.EASE_BOTH) }), new KeyFrame(
               Duration.millis(200.0D), new KeyValue[] { new KeyValue(this.colorPane1
                   .scaleXProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH), new KeyValue(this.colorPane1
                   .scaleYProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH) }), new KeyFrame(
               Duration.millis(1000.0D), new KeyValue[] { new KeyValue(this.colorPane1
                   .scaleXProperty(), Integer.valueOf(40), Interpolator.EASE_BOTH), new KeyValue(this.colorPane1
                   .scaleYProperty(), Integer.valueOf(40), Interpolator.EASE_BOTH) }) });
    }


    @Override
    public Animation animateEntrance() {
       return new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(320.0D), new KeyValue[] { new KeyValue(this.nextPage
                   .opacityProperty(), Integer.valueOf(1), Interpolator.EASE_BOTH) }) });
    }
  }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\AnimationDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */