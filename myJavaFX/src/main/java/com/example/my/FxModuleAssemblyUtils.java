package com.example.my;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * @description 特殊组件
 * @auth chaijd
 * @date 2023/10/12
 */
public class FxModuleAssemblyUtils extends ToggleButton {

    /**
     * checkBox 模拟三元判断
     * @param indeterminateMsg
     * @param selectMsg
     * @param unSelectMsg
     * @return
     */
    public static CheckBox initMyCheckBox(String indeterminateMsg, String selectMsg, String unSelectMsg) {
        CheckBox checkBox = new CheckBox(indeterminateMsg);
        //允许中间态
        checkBox.setAllowIndeterminate(true);
        checkBox.setIndeterminate(true);

        checkBox.setOnAction(event -> {
            if (checkBox.isIndeterminate()) {
                checkBox.setText(indeterminateMsg);
            } else if (checkBox.isSelected()) {
                checkBox.setText(selectMsg);
            } else {
                checkBox.setText(unSelectMsg);
            }
        });
        return checkBox;
    }

    public static VBox initMyVBoxToCheckBox(String checkBoxText1, String checkBoxText2) {
        CheckBox checkBox1 = new CheckBox(checkBoxText1);
        checkBox1.setSelected(true);
        CheckBox checkBox2 = new CheckBox(checkBoxText2);
        checkBox2.setSelected(true);

        checkBox1.setOnAction(event -> {
            if (!checkBox1.isSelected()) {
                if (!checkBox2.isSelected()) {
                    checkBox2.setSelected(true);
                }
            }
        });
        checkBox2.setOnAction(event -> {
            if (!checkBox2.isSelected()) {
                if (!checkBox1.isSelected()) {
                    checkBox1.setSelected(true);
                }
            }
        });
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(6));
        vBox.getChildren().addAll(checkBox1, checkBox2);
        return vBox;
    }

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

    public static HBox initMyHBoxToToggleButton(String textMsg, String toggleMsgY, String toggleMsgN) {
        Text txt = new Text(textMsg);
        ToggleButton mtb = initMyToggleButton(toggleMsgY, toggleMsgN);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(6));
        hBox.getChildren().addAll(txt, mtb);
        return hBox;
    }

    public static VBox initMyVBoxToToggleButton(String textMsg, String toggleMsgY, String toggleMsgN) {
        Text txt = new Text(textMsg);
        ToggleButton mtb = initMyToggleButton(toggleMsgY, toggleMsgN);
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(6));
        vBox.getChildren().addAll(txt, mtb);
        return vBox;
    }

    public static HBox initMyHBoxToInPut(String[] textMsgs, String[] values, int textFieldWidth) {
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

    public static HBox initMyHBoxToInPutWithPrompt(String[] textMsgs, String[] values, int textFieldWidth) {
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
            TextField textField = new TextField();
            textField.setPromptText(values[i]);
            if (textFieldWidth != 0) {
                textField.setPrefWidth(textFieldWidth);
            }
            hBox.getChildren().addAll(txt, textField, new Text("\u00A0\u00A0"));

//            hBox.getChildren().add(txt);
//            hBox.getChildren().add(textField);
        }
        return hBox;
    }

    public static HBox initMyHBoxToAreaWithPrompt(String[] textMsgs, String[] values, int fieldHeight) {
        int length = textMsgs.length;
        if (textMsgs.length > values.length) {
            length = values.length;
        }
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(6));
        // 设置HBox的垂直对齐方式为
        hBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < length; i++) {
            Text txt = new Text(textMsgs[i]);
            TextArea filed = new TextArea();
            filed.setPromptText(values[i]);
            filed.setWrapText(true);

            if (fieldHeight > 0) {
                filed.setPrefHeight(fieldHeight);
            }
            hBox.getChildren().addAll(txt, filed, new Text("\u00A0\u00A0"));
        }
        return hBox;
    }

    public static HBox initMyHBoxToInPut(String textMsg, String promptText) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(6));
        // 设置HBox的垂直对齐方式为
        hBox.setAlignment(Pos.CENTER_LEFT);
        Text txt = new Text(textMsg);
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        hBox.getChildren().addAll(txt, textField);
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
        return initMyHBoxToNumber(textField, textMsg, 0, 9, 32);
    }

    public static HBox initMyHBoxToNumber(TextField textField, String textMsg, int defval, int max, int prefWidth) {
        return initMyHBoxToNumber(textField, textMsg, defval, max, 0, prefWidth);
    }

    public static HBox initMyHBoxToNumber(TextField textField, String textMsg, int defval, int max, int min, int prefWidth) {
        Text txt = new Text(textMsg);
        Button addButton = new Button("+");
        //addButton.setPrefWidth(16);
        Button subtractButton = new Button("-");
        //subtractButton.setPrefWidth(16);
        textField.setText(defval + "");

        addButton.setOnAction(e -> {
            int currentValue = Integer.parseInt(textField.getText());
            if (currentValue < max) {
                currentValue++;
            }
            textField.setText(String.valueOf(currentValue));
        });
        subtractButton.setOnAction(e -> {
            int currentValue = Integer.parseInt(textField.getText());
            if (currentValue > min) {
                currentValue--;
            }
            textField.setText(String.valueOf(currentValue));
        });

        textField.setPrefWidth(prefWidth);
        // 添加事件监听器以验证输入
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) {
//                textField.setText(newValue.replaceAll("[^\\d]", ""));
//            }
            if (!newValue.matches("-?\\d*")) {
                textField.setText(newValue.replaceAll("[^-\\d]", ""));
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


    public static <T> HBox initMyHBoxToButton(String butName, T t, Consumer<T> processor) {
        Button closeButton = new Button(butName);
        closeButton.setPrefHeight(32);
        closeButton.setOnAction(event -> processor.accept(t));
        //水平布局的容器 HBox
        HBox hBox = new HBox();
        hBox.setSpacing(12);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        hBox.getChildren().addAll(closeButton);
        return hBox;
    }

    public static <T> Button initMyButton(String butName, T t, Consumer<T> processor) {
        Button closeButton = new Button(butName);
        closeButton.setPrefHeight(40);
        closeButton.setOnAction(event -> processor.accept(t));
        return closeButton;
    }

    public static Button initMyButton(String butName, Runnable task) {
        Button closeButton = new Button(butName);
        closeButton.setPrefHeight(40);
        closeButton.setOnAction(event -> task.run());
        return closeButton;
    }

//    public static <T> HBox initMyHBoxToButton(List<?>... lists) {
//        //水平布局的容器 HBox
//        HBox hBox = new HBox();
//        hBox.setSpacing(9);
//        hBox.setAlignment(Pos.BOTTOM_RIGHT);
//        hBox.setMinHeight(Region.USE_PREF_SIZE);
//
//        for (List<?> list : lists) {
//            if (list.size() <= 2) {
//                continue;
//            }
//
//            String butName = (String) list.get(0);
//            T t = (T) list.get(1);
//            Consumer<T> processor = (Consumer<T>) list.get(2);
//            Button myButton = initMyButton(butName, t, processor);
//            myButton.setOnAction(event -> processor.accept(t));
//            hBox.getChildren().addAll(myButton);
//        }
//        return hBox;
//    }
}
