package com.example.app;

import com.example.bean.BallsInfo;
import com.example.my.CommonConvertUtils;
import com.example.my.FxModuleAssemblyUtils;
import com.example.utils.CommonConstant;
import com.example.utils.MyLotteryProcessing;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/7
 */


public class BallGetOnlyAppMain extends Application {
    private TextArea contentArea = new TextArea();
    private TextField numTextField = new TextField();
    private HBox red, blue, toggleHBox;

    private List<BallsInfo> ballsInfoList = null;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public BallGetOnlyAppMain() {
    }

    public BallGetOnlyAppMain(List<BallsInfo> ballsInfoList) {
        this.ballsInfoList = ballsInfoList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        toggleHBox = FxModuleAssemblyUtils.initMyHBoxToToggleButton("input中自定义列表：", "input列表随机", "input列表去除");
        toggleHBox.setAlignment(Pos.CENTER_LEFT);

        HBox numHBox = FxModuleAssemblyUtils.initMyHBoxToNumber(numTextField, "相同数：");
        numHBox.setAlignment(Pos.CENTER_LEFT);

        VBox vBox1 = new VBox(6);
        vBox1.getChildren().addAll(toggleHBox, numHBox);


        //HBox vBox_red = FxModuleAssemblyUtils.initMyHBoxToInPut("red：", "example：");
        red = FxModuleAssemblyUtils.initMyHBoxToInPutWithPrompt(new String[]{"red：", "chance："}, new String[]{"example：", "example："}, 0);
        blue = FxModuleAssemblyUtils.initMyHBoxToInPutWithPrompt(new String[]{"blue：", "chance："}, new String[]{"example：", "example："}, 0);


        // 创建一个按钮
        Button getResultButton = new Button("获取结果");
        getResultButton.setOnAction(e -> button1Click((Button) e.getSource()));

        // 创建一个文本域用于显示文件内容
        contentArea.setEditable(false);
        contentArea.setPrefHeight(300);

        HBox bottomHbox = getHBox();
        bottomHbox.getChildren().addAll(FxModuleAssemblyUtils.initMyButton("Clear text", contentArea, TextArea::clear),
                FxModuleAssemblyUtils.initMyButton("Close Window", primaryStage, this::closeButton));

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(vBox1, red, blue, getResultButton, contentArea, bottomHbox);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // 创建一个场景并将容器添加到场景中
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Get Only Viewer");
        primaryStage.show();
    }

    private void closeButton(Stage stage) {
        try {
            stage.close();
            executor.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        executor.submit(task);
        //Platform.runLater(() -> executor.submit(task));
        //Platform.runLater(task);
    }

}