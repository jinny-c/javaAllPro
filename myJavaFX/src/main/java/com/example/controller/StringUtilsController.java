package com.example.controller;

import com.example.utils.CommonConstant;
import com.example.utils.LotteryProcessing;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
@Slf4j
public class StringUtilsController {
    @FXML
    private Button closeButton;
    @FXML
    private Button button1;

    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;

    private ToggleGroup toggleGroup;

    @FXML
    private TextArea textArea1;
    @FXML
    private TextArea textArea2;


    private Thread backgroundThread = null;

    public void initialize() {
        toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);

        radioButton1.setSelected(true);
    }

    @FXML
    protected void button1Click() {
        button1.setDisable(true);
        textArea2.setText("等待处理结果，请稍后。。。。。。");

        String content = textArea1.getText();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    if (StringUtils.isNotBlank(content)) {
                        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                        if (null != selectedRadioButton) {
                            String value = (String) selectedRadioButton.getUserData();
                            //String id = selectedRadioButton.getId();
                            if (StringUtils.equals(value, "01")) {
                                /**
                                 * 格式：
                                 * 等待查询结果，请稍后。。。。。。
                                 * count=10148314,isIn=false
                                 * BallEnty(blue=10, red=[16, 2, 26, 27, 29, 14])[2, 14, 16, 26, 27, 29]
                                 * 等待查询结果，请稍后。。。。。。
                                 * count=40123901,only=true,isIn=false
                                 * BallEnty(blue=15, red=[20, 22, 7, 9, 10, 31])[7, 9, 10, 20, 22, 31]
                                 *
                                 * */
                                Map<String, String> values = LotteryProcessing.lotteryStatistics(content);
                                textArea2.setText(StringUtils.join(values.values(), CommonConstant.line_feed));
                                return null;
                            }
                            if (StringUtils.equals(value, "02")) {
                                /**
                                 * 格式：
                                 * 23085：11-18-23-24-31-33,13
                                 * 23084：09-13-14-17-19-27,03
                                 * */
                                Map<String, String> values = LotteryProcessing.lotteryResult(content);
                                textArea2.setText(StringUtils.join(values.values(), CommonConstant.line_feed));
                                return null;
                            }
                        }

//                        for(Toggle toggle : toggleGroup.getToggles()){
//                            RadioButton radioButton = (RadioButton) toggle;
//                            Object obj = radioButton.getUserData();
//                            String id = radioButton.getId();
//                        }

                    }
                    textArea2.setText("空");
                } catch (Exception e) {
                    log.info("Exception", e);
                }
                return null;
            }
        };

        task.setOnSucceeded(evt -> {
            button1.setDisable(false);
        });

        backgroundThread = new Thread(task);
        backgroundThread.start();

        //button1.setDisable(false);
    }

    @FXML
    protected void closeButtonClick() {
        stopThread();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void clearButtonClick() {
        textArea1.clear();
        textArea2.clear();
        button1.setDisable(false);
        stopThread();
    }

    private void stopThread() {
        if (null != backgroundThread) {
            //backgroundThread.interrupt();
            backgroundThread.stop();
            backgroundThread = null;
        }
    }


}
