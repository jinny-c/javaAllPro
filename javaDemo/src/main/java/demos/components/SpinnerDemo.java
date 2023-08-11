package demos.components;

import com.jfoenix.controls.JFXSpinner;
import demos.MainDemo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class SpinnerDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();

        JFXSpinner root = new JFXSpinner();

        pane.getChildren().add(root);

        Scene scene = new Scene(pane, 300.0D, 300.0D);
        scene.getStylesheets().add(MainDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("JFX Spinner Demo");
        stage.show();
    }

    public static void main(String[] arguments) {
        Application.launch(arguments);
    }
}


/* Location:              D:\java\jdk\java\demo-0.0.0-SNAPSHOT\!\demos\components\SpinnerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */