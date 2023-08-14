package com.example.app.demo.jfoenix.controller.ui;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.io.IOException;


@ViewController(value = "/fxml/ui/Popup.fxml", title = "Material Design Example")
public final class PopupController {
    @FXML
    private StackPane root;
    @FXML
    private JFXRippler rippler1;
    @FXML
    private JFXRippler rippler2;
    @FXML
    private JFXRippler rippler3;
    @FXML
    private JFXRippler rippler4;
    @FXML
    private JFXHamburger burger1;
    @FXML
    private JFXHamburger burger2;
    @FXML
    private JFXHamburger burger3;
    @FXML
    private JFXHamburger burger4;
    @FXML
    private JFXHamburger burger5;
    private JFXPopup popup;

    @PostConstruct
    public void init() {
        try {
            this.popup = new JFXPopup(FXMLLoader.<Region>load(getClass().getResource("/fxml/ui/popup/DemoPopup.fxml")));
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
        }
        this.burger1.setOnMouseClicked(e -> this.popup.show((Node) this.rippler1, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
        this.burger2.setOnMouseClicked(e -> this.popup.show((Node) this.rippler2, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT));
        this.burger3.setOnMouseClicked(e -> this.popup.show((Node) this.rippler3, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT));
        this.burger4.setOnMouseClicked(e -> this.popup.show((Node) this.rippler4, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT));
    }
}
