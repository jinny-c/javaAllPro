// Source code is decompiled from a .class file using FernFlower decompiler.
package demos.gui.uicomponents;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import io.datafx.controller.ViewController;
import javafx.animation.Transition;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.annotation.PostConstruct;

@ViewController(
        value = "/fxml/ui/Icons.fxml",
        title = "Material Design Example"
)
public class IconsController {
    @FXML
    private JFXHamburger burger1;
    @FXML
    private JFXHamburger burger2;
    @FXML
    private JFXHamburger burger3;
    @FXML
    private JFXHamburger burger4;
    @FXML
    private JFXBadge badge1;
    @FXML
    private StackPane root;
    private JFXSnackbar snackbar;
    private int count = 1;

    public IconsController() {
    }

    @PostConstruct
    public void init() {
        this.bindAction(this.burger1);
        this.bindAction(this.burger2);
        this.bindAction(this.burger3);
        this.bindAction(this.burger4);
        this.snackbar = new JFXSnackbar(this.root);
        this.snackbar.setPrefWidth(300.0);
        this.badge1.setOnMouseClicked((click) -> {
            int value = Integer.parseInt(this.badge1.getText());
            if (click.getButton() == MouseButton.PRIMARY) {
                ++value;
            } else if (click.getButton() == MouseButton.SECONDARY) {
                --value;
            }

            if (value == 0) {
                this.badge1.setEnabled(false);
            } else {
                this.badge1.setEnabled(true);
            }

            this.badge1.setText(String.valueOf(value));
            if (this.count++ % 2 == 0) {
                this.snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Toast Message " + this.count)));
            } else if (this.count % 4 == 0) {
                JFXButton button = new JFXButton("CLOSE");
                button.setOnAction((action) -> {
                    this.snackbar.close();
                });
                this.snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Snackbar Message Persistent " + this.count, "CLOSE", (action) -> {
                    this.snackbar.close();
                }), Duration.INDEFINITE, (PseudoClass)null));
            } else {
                this.snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Snackbar Message" + this.count, "UNDO", (EventHandler)null), Duration.millis(3000.0), (PseudoClass)null));
            }

        });
    }

    private void bindAction(JFXHamburger burger) {
        burger.setOnMouseClicked((e) -> {
            Transition burgerAnimation = burger.getAnimation();
            burgerAnimation.setRate(burgerAnimation.getRate() * -1.0);
            burgerAnimation.play();
        });
    }
}
