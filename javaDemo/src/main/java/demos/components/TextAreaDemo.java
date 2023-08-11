package demos.components;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class TextAreaDemo extends Application {
    @Override
    public void start(Stage stage) {
        VBox main = new VBox();
        main.setSpacing(50.0D);
        TextArea javafxTextArea = new TextArea();
        javafxTextArea.setPromptText("JavaFX Text Area");
        main.getChildren().add(javafxTextArea);
        JFXTextArea jfxTextArea = new JFXTextArea();
        jfxTextArea.setPromptText("JFoenix Text Area :D");
        jfxTextArea.setLabelFloat(true);
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Please type something!");
        FontIcon warnIcon = new FontIcon((Ikon) FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon((Node) warnIcon);
        jfxTextArea.getValidators().add(validator);
        jfxTextArea.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.booleanValue()) {
                jfxTextArea.validate();
            }
        });
        main.getChildren().add(jfxTextArea);
        StackPane pane = new StackPane();
        pane.getChildren().add(main);
        StackPane.setMargin(main, new Insets(100.0D));
        pane.setStyle("-fx-background-color:WHITE");
        Scene scene = new Scene(pane, 800.0D, 600.0D);
        scene.getStylesheets()
                .add(ButtonDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setTitle("JFX Button Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}