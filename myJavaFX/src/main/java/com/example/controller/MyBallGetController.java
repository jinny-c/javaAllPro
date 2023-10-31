package com.example.controller;

import com.example.my.FxModuleAssemblyUtils;
import com.example.utils.CommonConstant;
import com.example.utils.MyLotteryProcessing;
import com.google.common.base.Splitter;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description 新随机获取
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyBallGetController {
    @FXML
    private Button closeButton, button1, clear1Button;
    @FXML
    private HBox hbox1, hbox2;
    @FXML
    private VBox vbox1;
    @FXML
    private GridPane gridPane1;
    @FXML
    private ToggleButton toggleButton2;
    @FXML
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextField textField1, textField11, textField2, textField3;
    @FXML
    private Text text1, text2;
    @FXML
    private ComboBox<String> comboBox;

    private TextField textField = new TextField();

    private static int[] indexArr = new int[]{1, 2, 3};
    private ToggleGroup toggleGroup;
    private Thread backgroundThread = null;

    public void initialize() {
        comboBox.getItems().addAll("1次", "2次", "3次");
        comboBox.getItems();
        //comboBox.setValue("2");
        // 将索引为1（即"2"）的选项设置为默认选项
        comboBox.getSelectionModel().select(0);

        //单选框
        toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);

        radioButton1.setSelected(true);

        toggleButton2.textProperty().bind(Bindings.when(toggleButton2.selectedProperty())
                .then("input列表随机")
                .otherwise("input列表去除"));

        // 绑定text2的wrappingWidth属性到text1的宽度
        text2.wrappingWidthProperty().bind(Bindings.createDoubleBinding(() ->
                text1.getBoundsInLocal().getWidth(), text1.boundsInLocalProperty()));


        //clear1Button.onActionProperty().bind(Bindings.createObjectBinding(()-> event -> textArea1.clear()));
        clear1Button.onActionProperty().bind(Bindings.createObjectBinding(() -> new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea1.clear();
            }
        }));

        HBox numHBox = FxModuleAssemblyUtils.initMyHBoxToNumber(textField, "相同数：");
        vbox1.getChildren().add(numHBox);
    }

    @FXML
    protected void button1Click() {
        button1.setDisable(true);
        hbox1.setDisable(true);
        String waitVal = "等待查询结果，请稍后。。。。。。";
        if (StringUtils.isBlank(textArea1.getText())) {
            textArea1.setText(waitVal);
        } else {
            textArea1.setText(textArea1.getText() + CommonConstant.line_feed + waitVal);
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int num = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
                Boolean isIn = toggleButton2.isSelected();
                int same = getSameCount();

                String redioValue = "01";
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                if (null != selectedRadioButton) {
                    redioValue = (String) selectedRadioButton.getUserData();
                }
                List<Integer> redLt = null;
                List<Integer> blueLt = null;
                String txt = textField1.getText();
                String txt11 = textField11.getText();

                Map<Integer, Double> redMp = null;
                String txt2 = textField2.getText();
                Map<Integer, Double> blueMp = null;
                String txt3 = textField3.getText();

                if (StringUtils.isNotBlank(txt)) {
                    redLt = convertList(txt);
                }
                if (StringUtils.isNotBlank(txt11)) {
                    blueLt = convertList(txt11);
                }
                if (StringUtils.isNotBlank(txt2)) {
                    redMp = convertMap(txt2, 33);
                }
                if (StringUtils.isNotBlank(txt3)) {
                    blueMp = convertMap(txt3, 16);
                }

                //Map<Integer, List<String>> rstMap = MyLotteryProcessing.getBallsByExecutor(num, isIn, redioValue, redLt, blueLt, redMp, blueMp);
                Map<Integer, List<String>> rstMap = MyLotteryProcessing.getBallsByExecutor(num, isIn, redioValue, redLt, blueLt, redMp, blueMp, same);
                rstMap.forEach((k, v) -> {
                    textArea1.setText(textArea1.getText() + CommonConstant.line_feed + StringUtils.join(v, CommonConstant.line_feed));
                });
                return null;
            }
        };

        task.setOnSucceeded(evt -> {
            button1.setDisable(false);
            hbox1.setDisable(false);
        });
        backgroundThread = new Thread(task);
        //在创建后台线程时调用了 setDaemon(true) 方法，将其设置为守护线程。这样，在 JavaFX 应用程序退出时，JVM 会自动终止该线程的执行，从而避免资源泄漏和其他问题。
        //多窗口需关闭主窗口
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    private int getSameCount() {
        try {
            return Integer.parseInt(textField.getText());
        } catch (Exception e) {
        }
        return 0;
    }

    private List<Integer> convertList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        List<Integer> lt = null;
        try {
            String[] arr = str.split("-");
            if (arr.length > 0) {
                lt = new ArrayList<>();
                for (String st : arr) {
                    lt.add(Integer.parseInt(st.trim()));
                }
            }
        } catch (Exception e) {
        }
        return lt;
    }

    private Map<Integer, Double> convertMap(String str, Integer length) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            if (StringUtils.startsWith(str, "{")) {
                str = StringUtils.substringAfter(str, "{");
            }
            if (StringUtils.endsWith(str, "}")) {
                str = StringUtils.substringBefore(str, "}");
            }

            Splitter.MapSplitter smsp = Splitter.on(",").withKeyValueSeparator("=");
            Map<String, String> smspMp = smsp.split(str);
            // 使用Stream和Lambda表达式进行类型转换
            Map<Integer, Double> mp = smspMp.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            entry -> Integer.parseInt(entry.getKey().trim()),
                            entry -> Double.parseDouble(entry.getValue().trim()) / length
                    ));

            return mp;
        } catch (Exception e) {
        }
        return null;
    }

    @FXML
    protected void radioButtonClick() {
        if (radioButton4.isSelected()) {
            toggleButton2.setDisable(false);
            textField1.setDisable(false);
            textField11.setDisable(false);
            textField2.setDisable(false);
            textField3.setDisable(false);
        } else {
            toggleButton2.setDisable(true);
            textField1.setDisable(true);
            textField11.setDisable(true);
            textField2.setDisable(true);
            textField3.setDisable(true);
        }
    }

    @FXML
    protected void closeButtonClick() {
        stopButtonClick();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clearButtonClick() {
        textArea1.clear();
        button1.setDisable(false);
        hbox1.setDisable(false);

        toggleButton2.setSelected(true);
        toggleButton2.setDisable(true);

        textField1.setDisable(true);
        textField11.setDisable(true);
        textField2.setDisable(true);
        textField3.setDisable(true);
        textField1.clear();
        textField11.clear();
        textField2.clear();
        textField3.clear();

        radioButton1.setSelected(true);
    }

    @FXML
    protected void stopButtonClick() {
        if (null != backgroundThread) {
            //backgroundThread.interrupt();
            backgroundThread.stop();
            backgroundThread = null;
        }
    }

}
