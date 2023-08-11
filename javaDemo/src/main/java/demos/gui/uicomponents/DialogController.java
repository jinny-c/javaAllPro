package demos.gui.uicomponents;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

@ViewController(
        value = "/fxml/ui/Dialog.fxml",
        title = "Material Design Example"
)
public class DialogController {
    public static final String CONTENT_PANE = "ContentPane";
    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    private JFXButton centerButton;
    @FXML
    private JFXButton topButton;
    @FXML
    private JFXButton rightButton;
    @FXML
    private JFXButton bottomButton;
    @FXML
    private JFXButton leftButton;
    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton alertButton;
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog dialog;

    public DialogController() {
    }

    @PostConstruct
    public void init() {
        this.root.getChildren().remove(this.dialog);
        this.centerButton.setOnAction((action) -> {
            this.dialog.setTransitionType(DialogTransition.CENTER);
            this.dialog.show((StackPane) this.context.getRegisteredObject("ContentPane"));
        });
        this.topButton.setOnAction((action) -> {
            this.dialog.setTransitionType(DialogTransition.TOP);
            this.dialog.show((StackPane) this.context.getRegisteredObject("ContentPane"));
        });
        this.rightButton.setOnAction((action) -> {
            this.dialog.setTransitionType(DialogTransition.RIGHT);
            this.dialog.show((StackPane) this.context.getRegisteredObject("ContentPane"));
        });
        this.bottomButton.setOnAction((action) -> {
            this.dialog.setTransitionType(DialogTransition.BOTTOM);
            this.dialog.show((StackPane) this.context.getRegisteredObject("ContentPane"));
        });
        this.leftButton.setOnAction((action) -> {
            this.dialog.setTransitionType(DialogTransition.LEFT);
            this.dialog.show((StackPane) this.context.getRegisteredObject("ContentPane"));
        });
        this.acceptButton.setOnAction((action) -> {
            this.dialog.close();
        });
        this.alertButton.setOnAction((action) -> {
            JFXAlert alert = new JFXAlert((Stage) this.alertButton.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Node[]{new Label("Modal Dialog using JFXAlert")});
            layout.setBody(new Node[]{new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Utenim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")});
            JFXButton closeButton = new JFXButton("ACCEPT");
            closeButton.getStyleClass().add("dialog-accept");
            closeButton.setOnAction((event) -> {
                alert.hideWithAnimation();
            });
            layout.setActions(new Node[]{closeButton});
            alert.setContent(new Node[]{layout});
            alert.show();
        });
    }
}