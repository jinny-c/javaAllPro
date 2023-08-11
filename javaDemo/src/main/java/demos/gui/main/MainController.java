package demos.gui.main;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.controls.events.JFXDrawerEvent;
import demos.datafx.ExtendedAnimatedFlowContainer;
import demos.gui.sidemenu.SideMenuController;
import demos.gui.uicomponents.ButtonController;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowContainer;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.annotation.PostConstruct;








@ViewController(value = "/fxml/Main.fxml", title = "Material Design Example")
public final class MainController
{
  @FXMLViewFlowContext
  private ViewFlowContext context;
  @FXML
  private StackPane root;
  @FXML
  private StackPane titleBurgerContainer;
  @FXML
  private JFXHamburger titleBurger;
  @FXML
  private StackPane optionsBurger;
  @FXML
  private JFXRippler optionsRippler;
  @FXML
  private JFXDrawer drawer;
  private JFXPopup toolbarPopup;

  @PostConstruct
  public void init() throws Exception {
/*  60 */     JFXTooltip burgerTooltip = new JFXTooltip("Open drawer");

/*  62 */     this.drawer.setOnDrawerOpening(e -> {
          Transition animation = this.titleBurger.getAnimation();
          burgerTooltip.setText("Close drawer");
          animation.setRate(1.0D);
          animation.play();
        });
/*  68 */     this.drawer.setOnDrawerClosing(e -> {
          Transition animation = this.titleBurger.getAnimation();
          burgerTooltip.setText("Open drawer");
          animation.setRate(-1.0D);
          animation.play();
        });
/*  74 */     this.titleBurgerContainer.setOnMouseClicked(e -> {
          if (this.drawer.isClosed() || this.drawer.isClosing()) {
            this.drawer.open();
          } else {
            this.drawer.close();
          }
        });

/*  82 */     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui/popup/MainPopup.fxml"));
/*  83 */     loader.setController(new InputController());
/*  84 */     this.toolbarPopup = new JFXPopup(loader.<Region>load());

/*  86 */     this.optionsBurger.setOnMouseClicked(e -> this.toolbarPopup.show(this.optionsBurger, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -12.0D, 15.0D));





/*  92 */     JFXTooltip.setVisibleDuration(Duration.millis(3000.0D));
/*  93 */     JFXTooltip.install(this.titleBurgerContainer, burgerTooltip, Pos.BOTTOM_CENTER);


/*  96 */     this.context = new ViewFlowContext();

/*  98 */     Flow innerFlow = new Flow(ButtonController.class);

     FlowHandler flowHandler = innerFlow.createHandler(this.context);
     this.context.register("ContentFlowHandler", flowHandler);
     this.context.register("ContentFlow", innerFlow);
     Duration containerAnimationDuration = Duration.millis(320.0D);
     this.drawer.setContent(new Node[] { flowHandler.start((FlowContainer)new ExtendedAnimatedFlowContainer(containerAnimationDuration, ContainerAnimations.SWIPE_LEFT)) });
     this.context.register("ContentPane", this.drawer.getContent().get(0));


     Flow sideMenuFlow = new Flow(SideMenuController.class);
     FlowHandler sideMenuFlowHandler = sideMenuFlow.createHandler(this.context);
     this.drawer.setSidePane(new Node[] { sideMenuFlowHandler.start((FlowContainer)new ExtendedAnimatedFlowContainer(containerAnimationDuration, ContainerAnimations.SWIPE_LEFT)) });
  }


  public static final class InputController
  {
    @FXML
    private JFXListView<?> toolbarPopupList;

    @FXML
    private void submit() {
       if (this.toolbarPopupList.getSelectionModel().getSelectedIndex() == 1)
         Platform.exit();
    }
  }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\gui\main\MainController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */