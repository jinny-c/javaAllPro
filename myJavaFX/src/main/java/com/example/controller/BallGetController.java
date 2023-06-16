package com.example.controller;

import com.example.utils.CommonConstant;
import com.example.utils.LotteryProcessing;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private TextArea textArea1;

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
        String waitVal = "等待查询结果，请稍后。。。。。。";
        textArea1.setText(waitVal);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 1;
                int num = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
                Boolean isIn = checkBox2.isSelected();
                Boolean only = checkBox1.isSelected();
                while (true) {
                    List<String> textValues = LotteryProcessing.someThings(isIn, only);
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
        });
        backgroundThread = new Thread(task);
        //在创建后台线程时调用了 setDaemon(true) 方法，将其设置为守护线程。这样，在 JavaFX 应用程序退出时，JVM 会自动终止该线程的执行，从而避免资源泄漏和其他问题。
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    @FXML
    protected void checkBox1Click() {
        if (checkBox1.isSelected()) {
            checkBox2.setDisable(true);
            //checkBox2.setSelected(false);
        } else {
            checkBox2.setDisable(false);
        }
    }


    @FXML
    protected void closeButtonClick() {
        //stopButtonClick();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clearButtonClick() {
        textArea1.clear();
        button1.setDisable(false);
        checkBox1.setSelected(true);
        checkBox2.setSelected(false);
        checkBox1.setDisable(true);
    }

    @FXML
    protected void stopButtonClick() {
        if (null != backgroundThread) {
            //backgroundThread.interrupt();
            backgroundThread.stop();
        }
    }

}