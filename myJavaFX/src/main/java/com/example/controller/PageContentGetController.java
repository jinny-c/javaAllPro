package com.example.controller;

import com.example.utils.PageProcessing;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
@Slf4j
public class PageContentGetController {
    @FXML
    private Button closeButton;
    @FXML
    private Button button1;

    @FXML
    private TextArea textArea1;

    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;


    private Thread backgroundThread = null;


    @FXML
    protected void button1Click() {
        button1.setDisable(true);
        textArea1.setText("等待查询结果，请稍后。。。。。。");

        String url = textField1.getText();
        String contentStrat = textField2.getText();
        String contentEnd = textField3.getText();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    //hbox1.setDisable(true);
                    if(StringUtils.isBlank(url)){
                        textArea1.setText("url is null");
                        return null;
                    }
                    String contentValue = PageProcessing.pagerGet(url, contentStrat, contentEnd);
                    textArea1.setText(contentValue);
                }catch (Exception e){
                    log.error("Exception",e);
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
        textField1.clear();
        textField2.clear();
        textField3.clear();
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
