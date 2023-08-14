package com.example.app.demo.jfoenix.controller;

import com.example.app.demo.jfoenix.controller.ui.*;
import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(
        value = "/fxml/SideMenu.fxml",
        title = "Material Design Example"
)
public class SideMenuController {
    @FXMLViewFlowContext
    private ViewFlowContext context;
    @FXML
    @ActionTrigger("buttons")
    private Label button;
    @FXML
    @ActionTrigger("checkbox")
    private Label checkbox;
    @FXML
    @ActionTrigger("combobox")
    private Label combobox;
    @FXML
    @ActionTrigger("dialogs")
    private Label dialogs;
    @FXML
    @ActionTrigger("icons")
    private Label icons;
    @FXML
    @ActionTrigger("listview")
    private Label listview;
    @FXML
    @ActionTrigger("treetableview")
    private Label treetableview;
    @FXML
    @ActionTrigger("progressbar")
    private Label progressbar;
    @FXML
    @ActionTrigger("radiobutton")
    private Label radiobutton;
    @FXML
    @ActionTrigger("slider")
    private Label slider;
    @FXML
    @ActionTrigger("spinner")
    private Label spinner;
    @FXML
    @ActionTrigger("textfield")
    private Label textfield;
    @FXML
    @ActionTrigger("togglebutton")
    private Label togglebutton;
    @FXML
    @ActionTrigger("popup")
    private Label popup;
    @FXML
    @ActionTrigger("svgLoader")
    private Label svgLoader;
    @FXML
    @ActionTrigger("pickers")
    private Label pickers;
    @FXML
    @ActionTrigger("masonry")
    private Label masonry;
    @FXML
    @ActionTrigger("scrollpane")
    private Label scrollpane;
    @FXML
    @ActionTrigger("chipview")
    private Label chipview;
    @FXML
    @ActionTrigger("nodeslist")
    private Label nodesList;
    @FXML
    @ActionTrigger("highlighter")
    private Label highlighter;
    @FXML
    private JFXListView<Label> sideList;

    public SideMenuController() {
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(this.context, "context");
        FlowHandler contentFlowHandler = (FlowHandler) this.context.getRegisteredObject("ContentFlowHandler");
        this.sideList.propagateMouseEventsToParent();
        this.sideList.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) -> {
            (new Thread(() -> {
                Platform.runLater(() -> {
                    if (newVal != null) {
                        try {
                            contentFlowHandler.handle(newVal.getId());
                        } catch (VetoException var3) {
                            var3.printStackTrace();
                        } catch (FlowException var4) {
                            var4.printStackTrace();
                        }
                    }

                });
            })).start();
        });
        Flow contentFlow = (Flow) this.context.getRegisteredObject("ContentFlow");
        this.bindNodeToController(this.button, ButtonController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.checkbox, CheckboxController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.combobox, ComboBoxController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.dialogs, DialogController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.icons, IconsController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.listview, ListViewController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.treetableview, TreeTableViewController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.progressbar, ProgressBarController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.radiobutton, RadioButtonController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.slider, SliderController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.spinner, SpinnerController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.textfield, TextFieldController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.highlighter, HighlighterController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.chipview, ChipViewController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.togglebutton, ToggleButtonController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.popup, PopupController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.svgLoader, SVGLoaderController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.pickers, PickersController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.masonry, MasonryPaneController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.scrollpane, ScrollPaneController.class, contentFlow, contentFlowHandler);
        this.bindNodeToController(this.nodesList, NodesListController.class, contentFlow, contentFlowHandler);
    }

    private void bindNodeToController(Node node, Class<?> controllerClass, Flow flow, FlowHandler flowHandler) {
        flow.withGlobalLink(node.getId(), controllerClass);
    }
}
