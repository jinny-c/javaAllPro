package com.example.controller;

import com.example.utils.PageProcessing;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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


    @FXML
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    private ToggleGroup toggleGroup;

    private Thread backgroundThread = null;

    public void initialize() {
        //单选框
        toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton5.setToggleGroup(toggleGroup);

        radioButton1.setSelected(true);
    }


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
                    if (StringUtils.isBlank(url)) {
                        textArea1.setText("url is null");
                        return null;
                    }
                    String redioValue = "01";
                    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                    if (null != selectedRadioButton) {
                        redioValue = (String) selectedRadioButton.getUserData();
                    }
                    String contentValue = PageProcessing.pagerGet(url, contentStrat, contentEnd, redioValue);
                    textArea1.setText(contentValue);
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
        textField1.clear();
        textField2.clear();
        textField3.clear();
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
