package com.example.app;

import com.example.bean.BallsInfo;
import com.example.my.CommonConvertUtils;
import com.example.my.CommonExecutorService;
import com.example.my.FxModuleAssemblyUtils;
import com.example.service.bean.LotteryResultDto;
import com.example.utils.BallHistoryCrawlerProcessing;
import com.example.utils.CommonConstant;
import com.example.utils.NewLotteryProcessing;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @description 无fxml实现
 * @auth chaijd
 * @date 2023/6/7
 */


public class BallGetOnlyAppMain extends Application {
    private TextArea contentArea = new TextArea();
    private TextField numTextField = new TextField();
    private TextField numTextField2 = new TextField();
    private HBox red, blue, toggleHBox;
    private VBox vBox2;
    private ToggleButton toggleButton;

    private List<BallsInfo> ballsInfoList = null;

    public BallGetOnlyAppMain() {
    }

    public BallGetOnlyAppMain(List<BallsInfo> ballsInfoList) {
        this.ballsInfoList = ballsInfoList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        toggleHBox = FxModuleAssemblyUtils.initMyHBoxToToggleButton("自定义：", "input列表随机", "input列表去除");
        toggleHBox.setAlignment(Pos.CENTER);
        toggleButton = FxModuleAssemblyUtils.initMyToggleButton("结果可重复", "结果不重复");
        //toggleButton.setAlignment(Pos.CENTER);
        HBox numHBox = FxModuleAssemblyUtils.initMyHBoxToNumber(numTextField, "相同数：", 1, 9, -1, 32);
        HBox numHBox2 = FxModuleAssemblyUtils.initMyHBoxToNumber(numTextField2, "取列表数：", 11, 99, 40);
        VBox vBox1 = new VBox(6);
        vBox1.setAlignment(Pos.BOTTOM_LEFT);
        vBox1.getChildren().addAll(numHBox, numHBox2);
        vBox2 = FxModuleAssemblyUtils.initMyVBoxToCheckBox("一个个比较", "结果比较");
        vBox2.setAlignment(Pos.BOTTOM_LEFT);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox hBox1 = new HBox(12);
        hBox1.getChildren().addAll(toggleHBox, region2, toggleButton, vBox2, vBox1, new Region());


        //HBox vBox_red = FxModuleAssemblyUtils.initMyHBoxToInPut("red：", "example：");
        red = FxModuleAssemblyUtils.initMyHBoxToAreaWithPrompt(new String[]{"red：", "chance："},
                new String[]{"example：1-11-27-30", "example：03=6,12=2,17=2,06=2"}, 180);
        blue = FxModuleAssemblyUtils.initMyHBoxToAreaWithPrompt(new String[]{"blue：", "chance："},
                new String[]{"example：3-6-12", "example：01=4,16=1,15=2,9=1"}, 180);

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
        bottomHbox.getChildren().addAll(FxModuleAssemblyUtils.initMyButton("Data handle", contentArea, this::dataHandleClick),
                FxModuleAssemblyUtils.initMyButton("Clear text", contentArea, TextArea::clear),
                FxModuleAssemblyUtils.initMyButton("Close Window", primaryStage, this::close));

        // 创建一个布局容器，将按钮和文本域嵌入其中
        VBox root = new VBox(hBox1, red, blue, hbox, contentArea, bottomHbox);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        //可移动窗口
        removableWindow(root, primaryStage);

        // 创建一个场景并将容器添加到场景中
        Scene scene = new Scene(root, 660, 560);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ball Get Only Viewer");
        primaryStage.show();

        // 在程序退出时关闭线程池
        Runtime.getRuntime().addShutdownHook(new Thread(CommonExecutorService::shutdown));
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void dataHandleClick(TextArea contentArea) {
        if (contentArea != null && StringUtils.isNotBlank(contentArea.getText())) {
            try {
                StringUtilsControllerAppMain myMainApp = new StringUtilsControllerAppMain(contentArea.getText());
                myMainApp.start(new Stage());
            } catch (Exception e) {
            }
        }
    }


    private void initInputValue(boolean isFirst) {
        Platform.runLater(() -> {
            LaterInitInputValue(isFirst);
        });
    }

    private void close(Stage primaryStage) {
        primaryStage.close();
        //可以不用显示关闭
        //Platform.exit();
        //CommonExecutorService.getInstannce().execute(()-> System.out.println("------------"));
        //CommonExecutorService.shutdown();
        //CommonExecutorService.getInstannce().execute(()-> System.out.println("---------------------"));
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
        List<TextInputControl> redField = CommonConvertUtils.convertSubNode(red, TextInputControl.class);
        redField.get(0).setText(values.get(0));
        redField.get(1).setText(values.get(1));
        List<TextInputControl> blueField = CommonConvertUtils.convertSubNode(blue, TextInputControl.class);
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

    private String convertValueByMyCheckBox() {
        try {
            List<CheckBox> fields = CommonConvertUtils.convertSubNode(vBox2, CheckBox.class);
            boolean select1 = fields.get(0).isSelected();
            boolean select2 = fields.get(1).isSelected();
            if (select1 && select2) {
                return "3";
            }
            if (select1) {
                return "1";
            }
            if (select2) {
                return "2";
            }
        } catch (Exception e) {
        }
        return "3";
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
                String type = convertValueByMyCheckBox();
                Boolean isIn = CommonConvertUtils.convertSelect(toggleHBox);
                Boolean canRepeat = toggleButton.isSelected();
                int same = CommonConvertUtils.getSameCount(numTextField.getText());
                String redText = CommonConvertUtils.convertPaneValue(red, 1);
                String redChanceText = CommonConvertUtils.convertPaneValue(red, 2);

                String buleText = CommonConvertUtils.convertPaneValue(blue, 1);
                String blueChanceText = CommonConvertUtils.convertPaneValue(blue, 2);


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

//                Map<String, List<String>> rstMap = MyLotteryProcessing.getBallsByExecutor(type, 1, isIn, canRepeat, "04", redLt, blueLt, redMp, blueMp, same);
//                rstMap.forEach((k, v) -> {
//                    contentArea.setText(contentArea.getText() + CommonConstant.line_feed + StringUtils.join(v, CommonConstant.line_feed));
//                });

                Map<String, LotteryResultDto> newRetMap = NewLotteryProcessing.getBallsByExecutor(type, isIn, canRepeat, "04", redLt, blueLt, redMp, blueMp, same);
                newRetMap.forEach((k, v) -> {
                    contentArea.setText(contentArea.getText() + CommonConstant.line_feed + StringUtils.join(v.convertShow(), CommonConstant.line_feed));
                });

                return null;
            }
        };
        task.setOnSucceeded(evt -> {
            button.setDisable(false);
        });
        CommonExecutorService.getInstannce().execute(task);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    private <T extends Parent> void removableWindow(T pan, Stage primaryStage) {
        // 绑定鼠标事件
        pan.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        pan.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }
}