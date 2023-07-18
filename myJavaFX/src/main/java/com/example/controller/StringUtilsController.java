package com.example.controller;

import com.example.utils.CommonConstant;
import com.example.utils.LotteryProcessing;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
    private TextArea textArea1;
    @FXML
    private TextArea textArea2;


    private Thread backgroundThread = null;


    @FXML
    protected void button1Click() {
        button1.setDisable(true);
        textArea2.setText("等待处理结果，请稍后。。。。。。");

        String content = textArea1.getText();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    if(StringUtils.isNotBlank(content)){
                        Map<String, String> values = LotteryProcessing.lotteryStatistics(content);
                        textArea2.setText(StringUtils.join(values.values(), CommonConstant.line_feed));
                    }else {
                        textArea2.setText("空");
                    }
                }catch (Exception e){
                    log.info("Exception",e);
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
