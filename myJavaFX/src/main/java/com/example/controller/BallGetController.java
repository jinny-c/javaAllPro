package com.example.controller;

import com.example.utils.CommonConstant;
import com.example.utils.LotteryProcessing;
import com.google.common.base.Splitter;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class BallGetController {
    @FXML
    private Button closeButton;
    @FXML
    private Button button1;
    @FXML
    private HBox hbox1;

    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;

    @FXML
    private ComboBox<String> comboBox;

    private static int[] indexArr = new int[]{1, 2, 3};

    private Thread backgroundThread = null;

    public void initialize() {
        comboBox.getItems().addAll("1次", "2次", "3次");
        comboBox.getItems();
        //comboBox.setValue("2");
        comboBox.getSelectionModel().select(0); // 将索引为1（即"2"）的选项设置为默认选项
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
                int count = 1;
                int num = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
                Boolean isIn = checkBox2.isSelected();
                Boolean only = checkBox1.isSelected();
                List<Integer> redLt = null;
                List<Integer> blueLt = null;
                String txt = textField1.getText();

                Map<Integer, Double> redMp = null;
                String txt2 = textField2.getText();
                Map<Integer, Double> blueMp = null;
                String txt3 = textField3.getText();

                if (StringUtils.isNotBlank(txt)) {
                    String[] arr = txt.split(",");
                    if (arr.length > 0) {
                        redLt = convertList(arr[0]);
                        if (arr.length > 1) {
                            blueLt = convertList(arr[1]);
                        }
                    }

                }
                if (StringUtils.isNotBlank(txt2)) {
                    redMp = convertMap(txt2, 33);
                }
                if (StringUtils.isNotBlank(txt3)) {
                    blueMp = convertMap(txt3, 16);
                }
                while (true) {
                    List<String> textValues = convertValue(only, isIn, redLt, blueLt, redMp, blueMp);

                    textArea1.setText(textArea1.getText() + CommonConstant.line_feed + StringUtils.join(textValues, CommonConstant.line_feed));
                    count++;
                    if (count > num) {
                        break;
                    }
                    textArea1.setText(textArea1.getText() + CommonConstant.line_feed + waitVal);
                }
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


    private List<String> convertValue(boolean only, Boolean isIn, List<Integer> redLt, List<Integer> blueLt,
                                      Map<Integer, Double> redMp, Map<Integer, Double> blueMp) {
        if (only) {
            return LotteryProcessing.getBallsByCondations(isIn, only);
        }
        //在预留
        if (isIn) {
            //预留数为空
            if (redLt == null || redLt.isEmpty()) {
                return LotteryProcessing.getBallsByCondations(isIn, redLt, blueLt, redMp, blueMp);
            }
            //预留不为空
            //概率为空
            if (redMp == null || redMp.isEmpty()) {
                return LotteryProcessing.getBallsByCondations(isIn, redLt, blueLt);
            }
            //预留 概率都不为空
            return LotteryProcessing.getBallsByCondations(isIn, redLt, blueLt, redMp, blueMp);
        }
        //不在预留
        //预留数为空
        if (redLt == null || redLt.isEmpty()) {
            return LotteryProcessing.getBallsByCondations(isIn, only);
        }
        if (blueLt == null || blueLt.isEmpty()) {
            return LotteryProcessing.getBallsByCondations(isIn, redLt);
        }
        //预留不为空
        return LotteryProcessing.getBallsByCondations(isIn, redLt, blueLt, redMp, blueMp);
    }

    @FXML
    protected void checkBox1Click() {
        if (checkBox1.isSelected()) {
            checkBox2.setDisable(true);
            textField1.setDisable(true);
            textField2.setDisable(true);
            textField3.setDisable(true);
        } else {
            checkBox2.setDisable(false);
            textField1.setDisable(false);
            textField2.setDisable(false);
            textField3.setDisable(false);
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

        //checkBox2.setSelected(false);
        checkBox2.setDisable(true);
        textField1.setDisable(true);
        textField2.setDisable(true);
        textField3.setDisable(true);
        textField1.clear();
        textField2.clear();
        textField3.clear();

        checkBox1.setSelected(true);
        //checkBox1.setDisable(false);
        //stopButtonClick();
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
