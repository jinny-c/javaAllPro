package com.example.app.demo.jfoenix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JavaFxDemo extends Application {
    static String[] fxmlArr = {"Button.fxml", "Checkbox.fxml", "ChipView.fxml", "Combobox.fxml", "Dialog.fxml",
            "Highlighter.fxml", "Icons.fxml", "ListView.fxml", "Masonry.fxml", "NodesList.fxml",};

    static Map<String, String> fxmlMap = new HashMap<String, String>() {{
        put("Button", "Button.fxml");
        put("Checkbox", "Checkbox.fxml");
        put("ChipView", "ChipView.fxml");
        put("Combobox", "Combobox.fxml");
        put("Dialog", "Dialog.fxml");
        put("Highlighter", "Highlighter.fxml");
        put("Icons", "Icons.fxml");
        put("ListView", "ListView.fxml");
        put("Masonry", "Masonry.fxml");
        put("NodesList", "NodesList.fxml");
        put("Pickers", "Pickers.fxml");
        put("Popup", "Popup.fxml");
        put("ProgressBar", "ProgressBar.fxml");
        put("RadioButton", "RadioButton.fxml");
        put("ScrollPane", "ScrollPane.fxml");
        put("Slider", "Slider.fxml");
        put("Spinner", "Spinner.fxml");
        put("SVGLoader", "SVGLoader.fxml");
        put("TextField", "TextField.fxml");
        put("ToggleButton", "ToggleButton.fxml");
        put("TreeTableView", "TreeTableView.fxml");
    }};

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlUri = "/fxml/ui/";
        String button = "Button.fxml";
        int i = (int) (Math.random() * fxmlArr.length);

        String url = fxmlUri + button;
        url = fxmlUri + fxmlArr[i];
        url = fxmlUri + fxmlMap.get("Slider");

        log.info("url={}", url);
        Parent root = FXMLLoader.load(getClass().getResource(url));


        primaryStage.setTitle("My Application");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

