package com.example.controller;

import com.example.utils.PageProcessing;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
    private Button button1, button2, button3;
    @FXML
    private Button button4, button41, button42;

    @FXML
    private TextArea textArea1;
    @FXML
    private ToggleButton toggleButton1;

    @FXML
    private TextField textField1, textField2, textField3, textField4, textField5;
    @FXML
    private TextField textField611, textField612, textField621, textField622;


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
    protected void button1Click(ActionEvent event) {
        allButton(true);
        textArea1.setText("等待查询结果，请稍后。。。。。。");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    String url = textField1.getText();
                    String contentStrat = textField2.getText();
                    String contentEnd = textField3.getText();
                    String content = textField4.getText();
                    String select = textField5.getText();
                    String redioValue = "01";
                    RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                    if (null != selectedRadioButton) {
                        redioValue = (String) selectedRadioButton.getUserData();
                    }
                    Button sourceButton = (Button) event.getSource();

                    if (StringUtils.isBlank(url)) {
                        textArea1.setText("url is null");
                        return null;
                    }
                    if (sourceButton == button1) {
                        textArea1.setText(PageProcessing.pagerGet(url, contentStrat, contentEnd, redioValue));
                        return null;
                    }
                    if (sourceButton == button2) {
                        if (StringUtils.isBlank(content)) {
                            textArea1.setText("content is null");
                            return null;
                        }
                        textArea1.setText(PageProcessing.pagerElementsGetByContent(url, content));
                        return null;
                    }
                    if (sourceButton == button3) {
                        if (StringUtils.isBlank(select)) {
                            textArea1.setText("select is null");
                            return null;
                        }
                        boolean isLineFeed = toggleButton1.isSelected();
                        textArea1.setText(PageProcessing.pagerGetBySelect(url, select, isLineFeed));
                        return null;
                    }
                } catch (Exception e) {
                    log.info("Exception", e);
                }
                textArea1.setText("value is null");
                return null;
            }
        };

        task.setOnSucceeded(evt -> {
            allButton(false);
        });

        backgroundThread = new Thread(task);
        backgroundThread.start();

        //button1.setDisable(false);
    }

    @FXML
    protected void button3Click(ActionEvent event) {
        allButton(true);
        String url = textField1.getText();
        String selectContent = textField5.getText();

        Button sourceButton = (Button) event.getSource();
        if (sourceButton == button41) {
            url = textField612.getText();
        } else if (sourceButton == button42) {
            url = textField622.getText();
        }else {
            textArea1.setText("sourceButton is null");
            allButton(false);
            return;
        }

        if(StringUtils.isBlank(url)){
            textArea1.setText("url is null");
            allButton(false);
            return;
        }
        textField1.setText(url);

        if (StringUtils.isBlank(selectContent)) {
            textArea1.setText("selectContent is null");
            allButton(false);
            return;
        }
        boolean isLineFeed = toggleButton1.isSelected();
        textArea1.setText(PageProcessing.pagerGetBySelect(url, selectContent, isLineFeed));

        button4Click();
        allButton(false);
    }


    @FXML
    protected void button4Click() {
        allButton(true);

        String url = textField1.getText();
        String select61 = textField611.getText();
        String select62 = textField621.getText();
        if (StringUtils.isNotBlank(select61)) {
            textField612.setText(PageProcessing.pagerGetBySelect(url, select61));
        }
        if (StringUtils.isNotBlank(select62)) {
            textField622.setText(PageProcessing.pagerGetBySelect(url, select62));
        }

        allButton(false);
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
        //textField1.clear();
        textField2.clear();
        textField3.clear();
        //button1.setDisable(false);
        allButton(false);
        stopThread();
    }

    private void stopThread() {
        if (null != backgroundThread) {
            //backgroundThread.interrupt();
            backgroundThread.stop();
            backgroundThread = null;
        }
    }

    private void allButton(boolean disable) {
        button1.setDisable(disable);
        button2.setDisable(disable);
        button3.setDisable(disable);
        button4.setDisable(disable);
        button41.setDisable(disable);
        button42.setDisable(disable);
    }
}
