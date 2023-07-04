package com.example.app.sub;

import com.example.bean.TableColumnModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/31
 */

public class AddView {

    private Stage stage;

    @Getter
    private TableColumnModel columnModel;

    public AddView() {
        stage = new Stage();
        stage.setTitle("File Select View");
        stage.initModality(Modality.APPLICATION_MODAL);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(15));

        Label label1 = new Label("内容是：");
        TextField field1 = new TextField();

        columnModel = TableColumnModel.fromWork(99, field1.getText());

        Button openButton = new Button("add");
        openButton.setOnAction(event -> {
            String val = field1.getText();
            if (val != null) {
                stage.close();
            }
        });

        hBox.getChildren().addAll(label1, field1, openButton);


        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> stage.close());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(hBox, closeButton);

        Scene scene = new Scene(vbox, 400, 300);

        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }

}