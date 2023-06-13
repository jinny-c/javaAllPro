package com.example.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/12
 */
public class GridPaneAppMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final int WIDTH = 500;
        final int HEIGHT = 240;
        // 创建网格布局
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color:#fff;"); // 设置成灰色
        setWidthHeight(pane, WIDTH, HEIGHT);
        pane.setAlignment(Pos.CENTER); // 设置GridPane在父元素（这里是Scene）中居中对齐
        pane.setHgap(10); // 设置格子元素的水平距离
        pane.setVgap(10); // 设置格子元素的竖直距离

        // 创建一系列Label
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                Label label = new Label("" + (row * 7 + col + 1));
                setWidthHeight(label, 60, 40);
                label.setStyle("-fx-background-color: lightgreen;" + // 设置背景色
                        "-fx-alignment: center; " + // 设置文字居中
                        "-fx-background-radius: 5;"); // 设置背景的圆角化
                pane.add(label, col, row);
            }
        }

        // 主界面显示
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void setWidthHeight(Region region, int width, int height) {
        region.setMaxHeight(height);
        region.setMinHeight(height);
        region.setMaxWidth(width);
        region.setMinWidth(width);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
