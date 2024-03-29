package com.example.app;

import com.example.controller.StringUtilsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/9
 */
public class StringUtilsControllerAppMain extends Application {
    private String value;

    public StringUtilsControllerAppMain(){
        super();
    }

    public StringUtilsControllerAppMain(String value){
        super();
        this.value = value;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StringUtils.fxml"));
        Parent root = loader.load();
        if (StringUtils.isNotBlank(value)) {
            // 获取控制器
            StringUtilsController controller = loader.getController();
            // 在控制器中调用方法来更改FXML中的值
            controller.updateLabel(value);
        }

        //可移动窗口
        removableWindow(root, primaryStage);
        primaryStage.setTitle("Page Content Get Processing");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
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
    public static void main(String[] args) {
        launch(args);
    }
}
