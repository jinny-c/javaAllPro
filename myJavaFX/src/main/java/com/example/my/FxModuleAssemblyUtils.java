package com.example.my;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/10/12
 */
public class FxModuleAssemblyUtils extends ToggleButton {

    public static ToggleButton initMyToggleButton() {
        return initMyToggleButton("是", "否");
    }

    public static ToggleButton initMyToggleButton(String selectMsg, String unSelectMsg) {
        ToggleButton mtb = new ToggleButton(selectMsg);
        mtb.setSelected(true);
        mtb.setOnAction(event -> {
            if (mtb.isSelected()) {
                mtb.setText(selectMsg);
            } else {
                mtb.setText(unSelectMsg);
            }
        });
        return mtb;
    }

    public static HBox initMyHBoxToToggleButton(String textMsg) {
        Text txt = new Text(textMsg);
        ToggleButton mtb = initMyToggleButton();
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(6));
        hBox.getChildren().addAll(txt, mtb);
        return hBox;
        //return new HBox(txt, mtb);
    }

    public static HBox initMyHBoxToInPut(String[] textMsgs, Integer[] values, int textFieldWidth) {
        int length = textMsgs.length;
        if (textMsgs.length > values.length) {
            length = values.length;
        }
        HBox hBox = new HBox();
//        hBox.setSpacing(6);
        hBox.setPadding(new Insets(6));
        // 设置HBox的垂直对齐方式为
        hBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < length; i++) {
            Text txt = new Text(textMsgs[i]);
            TextField textField = new TextField(values[i] + "");
            if (textFieldWidth != 0) {
                textField.setPrefWidth(textFieldWidth);
            }
            hBox.getChildren().addAll(txt, textField, new Text("\u00A0\u00A0"));

//            hBox.getChildren().add(txt);
//            hBox.getChildren().add(textField);
        }
        return hBox;
    }

    public static HBox initMyHBoxToCloseButton(Stage stage) {
        Button closeButton = new Button("Close Window");
        closeButton.setPrefHeight(50);
        closeButton.setOnAction(event -> stage.close());
        //水平布局的容器 HBox
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        hBox.getChildren().addAll(closeButton);
        return hBox;
    }

    public static HBox initMyHBoxToNumber(TextField textField, String textMsg) {
        Text txt = new Text(textMsg);
        Button addButton = new Button("+");
        //addButton.setPrefWidth(16);
        Button subtractButton = new Button("-");
        //subtractButton.setPrefWidth(16);
        textField.setText("0");

        addButton.setOnAction(e -> {
            int currentValue = Integer.parseInt(textField.getText());
            if (currentValue < 9) {
                currentValue++;
            }
            textField.setText(String.valueOf(currentValue));
        });
        subtractButton.setOnAction(e -> {
            int currentValue = Integer.parseInt(textField.getText());
            if (currentValue > 0) {
                currentValue--;
            }
            textField.setText(String.valueOf(currentValue));
        });

        textField.setPrefWidth(32);
        // 添加事件监听器以验证输入
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //水平布局的容器 HBox
        HBox hBox = new HBox();
        hBox.setSpacing(3);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        hBox.getChildren().addAll(txt, textField, addButton, subtractButton);
        return hBox;
    }
}
