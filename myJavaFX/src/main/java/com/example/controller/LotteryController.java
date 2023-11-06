package com.example.controller;

import com.example.app.BallGetOnlyAppMain;
import com.example.bean.BallsInfo;
import com.example.utils.BallHistoryCrawlerProcessing;
import com.example.utils.CommonConstant;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class LotteryController {
    @FXML
    private Button closeButton;
    @FXML
    private Button button1;
    @FXML
    private Button button2;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextArea textArea2;
    @FXML
    private TextArea textArea3;
    @FXML
    private TextField latestPeriods;
    @FXML
    private TextField textField1;

    @FXML
    private VBox disableContent;

    private Thread backgroundThread = null;

    List<BallsInfo> ballsInfoList = null;

    private static String[] indexArr = new String[]{"2", "4", "6", "10", "12", "18"};

    static {
//        ObservableMap<String, String> comboBoxMap = FXCollections.observableHashMap();
//        comboBoxMap.put("2","2期");
//        comboBoxMap.put("4","4期");
//        comboBoxMap.put("6","6期");
//        comboBoxMap.put("10","10期");
//        comboBoxMap.put("12","12期");
//        comboBoxMap.put("18","18期");

    }

    public void initialize() {
        comboBox.getItems().addAll("2期", "4期", "6期", "10期", "12期", "18期");
        comboBox.getItems();
        //comboBox.setValue("2");
        comboBox.getSelectionModel().select(1); // 将索引为1（即"2"）的选项设置为默认选项
        textField1.setText("4");
    }

    @FXML
    protected void button1Click() {
        button1.setDisable(true);
        textArea1.setText("等待查询结果，请稍后。。。。。。");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //hbox1.setDisable(true);
                List<BallsInfo> ballsInfos = BallHistoryCrawlerProcessing.crawlerBall(null);
                if (ballsInfos.isEmpty()) {
                    return null;
                }
                ballsInfoList = ballsInfos;
                List<String> textValues = BallHistoryCrawlerProcessing.crawlerBallByInfo(ballsInfos, 3);
                textArea1.setText(StringUtils.join(textValues, CommonConstant.line_feed));
                latestPeriods.setText(ballsInfos.get(0).getBallDate());
                disableContent.setDisable(false);
                return null;
            }
        };

        task.setOnSucceeded(evt -> {
            button1.setDisable(false);
        });
        new Thread(task).start();
        //button1.setDisable(false);
    }


    @FXML
    protected void comboBoxClick() {
        String val = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
        textField1.setText(val);
    }

    @FXML
    protected void selectButtonClick() {
        //String periods = comboBox.getValue();
        //comboBox.getSelectionModel().getSelectedItem();
        String val = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
        String latest = latestPeriods.getText();
        String val1 = textField1.getText();
        button2.setDisable(true);
        textArea2.setText("等待查询结果，请稍后。。。。。。");

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //hbox1.setDisable(true);
                Map<String, String> reqMap = new HashMap<>();
                reqMap.put(CommonConstant.filed_end, latest);
                reqMap.put(CommonConstant.filed_start, getStart(latest, val, val1));
                List<BallsInfo> ballsInfos = BallHistoryCrawlerProcessing.crawlerBall(reqMap);
                if (ballsInfos.isEmpty()) {
                    return null;
                }
                ballsInfoList = ballsInfos;
                List<String> textValues = BallHistoryCrawlerProcessing.crawlerBallByInfo(ballsInfos, ballsInfos.size());
                textArea2.setText(StringUtils.join(textValues, CommonConstant.line_feed));

                Map<String, String> convertVal = BallHistoryCrawlerProcessing.crawlerBallStatistics(ballsInfos);

                textArea3.setText(StringUtils.join(convertVal.values(), CommonConstant.line_feed));
//                VBox vBox = new VBox();
//                for (String txt : convertVal.values()) {
//                    Text tx1 = new Text();
//                    tx1.setText(txt);
//                    vBox.getChildren().add(tx1);
//                }
//                Platform.runLater(() -> {
//                    // 在这里更新 JavaFX UI 的代码
//                    textFlow1.getChildren().add(vBox);
//                });
                return null;
            }
        };

        task.setOnSucceeded(evt -> {
            button2.setDisable(false);
        });
        //new Thread(task).start();

        backgroundThread = new Thread(task);
        backgroundThread.start();
    }

    private String getStart(String latest, String val, String val1) {
        try {
            int subtraction = Integer.parseInt(val);
            if (StringUtils.isNotBlank(val1)) {
                subtraction = Integer.parseInt(val1);
            }
            if (subtraction > 1) {
                subtraction -= 1;
            }
//            if (subtraction % 3 == 0) {
//                subtraction = subtraction * 2;
//            }else if(subtraction % 3 == 1){
//                subtraction = subtraction * 3;
//            }
            //Integer start = Integer.parseInt(latest) - Integer.parseInt(val);
            Integer start = Integer.parseInt(latest) - subtraction;
            return start + "";
            //return StringUtils.leftPad(start + "", 5, "0");
        } catch (Exception e) {
        }
        return null;
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
        textArea3.clear();
        latestPeriods.clear();
        disableContent.setDisable(true);
        button1.setDisable(false);
        button2.setDisable(false);
        stopThread();
    }

    private void stopThread() {
        if (null != backgroundThread) {
            //backgroundThread.interrupt();
            backgroundThread.stop();
            backgroundThread = null;
        }
    }

    @FXML
    protected void subButtonClick() {
//        Platform.runLater(() -> {
//            if (ballsInfoList != null) {
//                try {
//                    BallGetOnlyAppMain myMainApp = new BallGetOnlyAppMain(ballsInfoList);
//                    myMainApp.start(new Stage());
//                } catch (Exception e) {
//                }
//            }
//        });

        if (ballsInfoList != null) {
            try {
                BallGetOnlyAppMain myMainApp = new BallGetOnlyAppMain(ballsInfoList);
                myMainApp.start(new Stage());
            } catch (Exception e) {
            }
        }
    }
}
