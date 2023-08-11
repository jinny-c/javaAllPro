package demos.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DrawerDemo
  extends Application
{
  private static final String LEFT = "LEFT";
  private static final String TOP = "TOP";
  private static final String RIGHT = "RIGHT";
  private static final String BOTTOM = "BOTTOM";

  public void start(Stage stage) {
/*  25 */     FlowPane content = new FlowPane();
/*  26 */     JFXButton leftButton = new JFXButton("LEFT");
/*  27 */     JFXButton topButton = new JFXButton("TOP");
/*  28 */     JFXButton rightButton = new JFXButton("RIGHT");
/*  29 */     JFXButton bottomButton = new JFXButton("BOTTOM");
/*  30 */     content.getChildren().addAll(new Node[] { (Node)leftButton, (Node)topButton, (Node)rightButton, (Node)bottomButton });
    content.setMaxSize(200.0D, 200.0D);


     JFXDrawer leftDrawer = new JFXDrawer();
/*  35 */     StackPane leftDrawerPane = new StackPane();
     leftDrawerPane.getStyleClass().add("red-400");
/*  37 */     leftDrawerPane.getChildren().add(new JFXButton("Left Content"));
/*  38 */     leftDrawer.setSidePane(new Node[] { leftDrawerPane });
/*  39 */     leftDrawer.setDefaultDrawerSize(150.0D);
/*  40 */     leftDrawer.setResizeContent(true);
/*  41 */     leftDrawer.setOverLayVisible(false);
/*  42 */     leftDrawer.setResizableOnDrag(true);


/*  45 */     JFXDrawer bottomDrawer = new JFXDrawer();
/*  46 */     StackPane bottomDrawerPane = new StackPane();
/*  47 */     bottomDrawerPane.getStyleClass().add("deep-purple-400");
/*  48 */     bottomDrawerPane.getChildren().add(new JFXButton("Bottom Content"));
/*  49 */     bottomDrawer.setDefaultDrawerSize(150.0D);
/*  50 */     bottomDrawer.setDirection(JFXDrawer.DrawerDirection.BOTTOM);
/*  51 */     bottomDrawer.setSidePane(new Node[] { bottomDrawerPane });
/*  52 */     bottomDrawer.setResizeContent(true);
/*  53 */     bottomDrawer.setOverLayVisible(false);
/*  54 */     bottomDrawer.setResizableOnDrag(true);


/*  57 */     JFXDrawer rightDrawer = new JFXDrawer();
/*  58 */     StackPane rightDrawerPane = new StackPane();
/*  59 */     rightDrawerPane.getStyleClass().add("blue-400");
/*  60 */     rightDrawerPane.getChildren().add(new JFXButton("Right Content"));
/*  61 */     rightDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
/*  62 */     rightDrawer.setDefaultDrawerSize(150.0D);
/*  63 */     rightDrawer.setSidePane(new Node[] { rightDrawerPane });
/*  64 */     rightDrawer.setOverLayVisible(false);
/*  65 */     rightDrawer.setResizableOnDrag(true);


/*  68 */     JFXDrawer topDrawer = new JFXDrawer();
/*  69 */     StackPane topDrawerPane = new StackPane();
/*  70 */     topDrawerPane.getStyleClass().add("green-400");
/*  71 */     topDrawerPane.getChildren().add(new JFXButton("Top Content"));
/*  72 */     topDrawer.setDirection(JFXDrawer.DrawerDirection.TOP);
/*  73 */     topDrawer.setDefaultDrawerSize(150.0D);
/*  74 */     topDrawer.setSidePane(new Node[] { topDrawerPane });
/*  75 */     topDrawer.setOverLayVisible(false);
/*  76 */     topDrawer.setResizableOnDrag(true);


/*  79 */     JFXDrawersStack drawersStack = new JFXDrawersStack();
/*  80 */     drawersStack.setContent(content);

/*  82 */     leftDrawer.setId("LEFT");
/*  83 */     rightDrawer.setId("RIGHT");
/*  84 */     bottomDrawer.setId("BOTTOM");
/*  85 */     topDrawer.setId("TOP");

/*  87 */     leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> drawersStack.toggle(leftDrawer));
/*  88 */     bottomButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> drawersStack.toggle(bottomDrawer));
/*  89 */     rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> drawersStack.toggle(rightDrawer));
/*  90 */     topButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> drawersStack.toggle(topDrawer));


/*  93 */     Scene scene = new Scene((Parent)drawersStack, 800.0D, 800.0D);
/*  94 */     ObservableList<String> stylesheets = scene.getStylesheets();
/*  95 */     stylesheets.addAll(new String[] { DrawerDemo.class.getResource("/css/jfoenix-components.css").toExternalForm(), DrawerDemo.class
/*  96 */           .getResource("/css/jfoenix-design.css").toExternalForm() });

/*  98 */     stage.setTitle("JFX Drawer Demo");
/*  99 */     stage.setScene(scene);
     stage.setResizable(true);
     stage.show();
  }

  public static void main(String[] args) {
     launch(args);
  }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\DrawerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */