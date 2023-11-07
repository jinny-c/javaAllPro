package com.example.app;

import com.example.bean.BallsInfo;
import com.example.my.CommonConvertUtils;
import com.example.my.FxModuleAssemblyUtils;
import com.example.utils.BallHistoryCrawlerProcessing;
import com.example.utils.CommonConstant;
import com.example.utils.MyLotteryProcessing;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */


public class BallGetOnlyAppMain extends Application {
    private TextArea contentArea = new TextArea();
    private TextField numTextField = new TextField();
    private TextField numTextField2 = new TextField();
    private HBox red, blue, toggleHBox;

    private List<BallsInfo> ballsInfoList = null;

    public BallGetOnlyAppMain() {
    }

    public BallGetOnlyAppMain(List<BallsInfo> ballsInfoList) {
        this.ballsInfoList = ballsInfoList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        toggleHBox = FxModuleAssemblyUtils.initMyHBoxToToggleButton("input中自定义列表：", "input列表随机", "input列表去除");
        toggleHBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox numHBox = FxModuleAssemblyUtils.initMyHBoxToNumber(numTextField, "相同数：");
        HBox numHBox2 = FxModuleAssemblyUtils.initMyHBoxToNumber(numTextField2, "取列表数：", 12, 27, 40);
        VBox vBox1 = new VBox(6);
        vBox1.setAlignment(Pos.BOTTOM_LEFT);
        vBox1.getChildren().addAll(numHBox, numHBox2);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox hBox1 = new HBox(12);
        hBox1.getChildren().addAll(toggleHBox, region2, vBox1, new Region());


        //HBox vBox_red = FxModuleAssemblyUtils.initMyHBoxToInPut("red：", "example：");
        red = FxModuleAssemblyUtils.initMyHBoxToInPutWithPrompt(new String[]{"red：", "chance："}, new String[]{"example：1-11-27-30", "example：03=6,12=2,17=2,06=2"}, 0);
        blue = FxModuleAssemblyUtils.initMyHBoxToInPutWithPrompt(new String[]{"blue：", "chance："}, new String[]{"example：3-6-12", "example：01=4,16=1,15=2,9=1"}, 0);

        initInputValue(true);
        // 创建一个按钮
        Button getResultButton = new Button("获取结果");
        getResultButton.setOnAction(e -> button1Click((Button) e.getSource()));
        // 创建一个按钮
        Button refreshtButton = new Button("刷新参数");
        refreshtButton.setOnAction(e -> initInputValue(false));
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox hbox = getHBox();
        hbox.getChildren().setAll(getResultButton, region, refreshtButton);


        // 创建一个文本域用于显示文件内容
        contentArea.setEditable(false);
        contentArea.setPrefHeight(300);

        HBox bottomHbox = getHBox();
        bottomHbox.getChildren().addAll(FxModuleAssemblyUtils.initMyButton("Clear text", contentArea, TextArea::clear),
                FxModuleAssemblyUtils.initMyButton("Close Window", primaryStage, Stage::close));

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(hBox1, red, blue, hbox, contentArea, bottomHbox);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // 创建一个场景并将容器添加到场景中
        Scene scene = new Scene(root, 660, 560);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Get Only Viewer");
        primaryStage.show();
    }


    private void initInputValue(boolean isFirst) {
        Platform.runLater(() -> {
            LaterInitInputValue(isFirst);
        });
    }

    private void LaterInitInputValue(boolean isFirst) {
        if (ballsInfoList == null || ballsInfoList.isEmpty()) {
            return;
        }
        int count = CommonConvertUtils.getSameCount(numTextField2.getText());
        if (count <= 0) {
            count = 9;
        }
        List<BallsInfo> subList = ballsInfoList;
        if (ballsInfoList.size() >= count) {
            subList = ballsInfoList.subList(0, count);
        }

        List<String> values = BallHistoryCrawlerProcessing.crawlerBallAndStatistics(subList);
        List<TextField> redField = CommonConvertUtils.convertSubNode(red, TextField.class);
        redField.get(0).setText(values.get(0));
        redField.get(1).setText(values.get(1));
        List<TextField> blueField = CommonConvertUtils.convertSubNode(blue, TextField.class);
        blueField.get(0).setText(values.get(2));
        blueField.get(1).setText(values.get(3));
        if (isFirst) {
            HBox.setHgrow(redField.get(1), Priority.ALWAYS);
            HBox.setHgrow(blueField.get(1), Priority.ALWAYS);
        }
    }

    private HBox getHBox() {
        //水平布局的容器 HBox
        HBox hBox = new HBox();
        hBox.setSpacing(12);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.setMinHeight(Region.USE_PREF_SIZE);
        return hBox;
    }

    protected void button1Click(Button button) {
        button.setDisable(true);
        String waitVal = "等待查询结果，请稍后。。。。。。";
        if (StringUtils.isBlank(contentArea.getText())) {
            contentArea.setText(waitVal);
        } else {
            contentArea.setText(contentArea.getText() + CommonConstant.line_feed + waitVal);
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //int num = indexArr[comboBox.getSelectionModel().getSelectedIndex()];
                Boolean isIn = CommonConvertUtils.convertSelect(toggleHBox);
                int same = CommonConvertUtils.getSameCount(numTextField.getText());
                String redText = CommonConvertUtils.convertPaneValue(red, 1);
                String redChanceText = CommonConvertUtils.convertPaneValue(red, 2);

                String buleText = CommonConvertUtils.convertPaneValue(blue, 1);
                String blueChanceText = CommonConvertUtils.convertPaneValue(blue, 1);

                List<Integer> redLt = null;
                List<Integer> blueLt = null;
                Map<Integer, Double> redMp = null;
                Map<Integer, Double> blueMp = null;
                if (StringUtils.isNotBlank(redText)) {
                    redLt = CommonConvertUtils.convertList(redText);
                }
                if (StringUtils.isNotBlank(buleText)) {
                    blueLt = CommonConvertUtils.convertList(buleText);
                }
                if (StringUtils.isNotBlank(redChanceText)) {
                    redMp = CommonConvertUtils.convertMap(redChanceText, 33);
                }
                if (StringUtils.isNotBlank(blueChanceText)) {
                    blueMp = CommonConvertUtils.convertMap(blueChanceText, 16);
                }

                //Map<Integer, List<String>> rstMap = new MyLotteryProcessing().getBallsByExecutor(1, isIn, "04", redLt, blueLt, redMp, blueMp, same);
                try (MyLotteryProcessing processing = new MyLotteryProcessing()) {
                    Map<Integer, List<String>> rstMap = processing.getBallsByExecutor(1, isIn, "04", redLt, blueLt, redMp, blueMp, same);
                    rstMap.forEach((k, v) -> {
                        contentArea.setText(contentArea.getText() + CommonConstant.line_feed + StringUtils.join(v, CommonConstant.line_feed));
                    });
                }
                return null;
            }
        };
        task.setOnSucceeded(evt -> {
            button.setDisable(false);
        });

        Platform.runLater(task);
    }

}