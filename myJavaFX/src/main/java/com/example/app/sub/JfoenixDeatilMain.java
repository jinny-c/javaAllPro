package com.example.app.sub;

import com.example.bean.TableColumnModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/14
 */
public class JfoenixDeatilMain extends Application {

    private Stage stage;
    @Setter
    private TableColumnModel columnModel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建JFXTextField和JFXButton控件
        HBox hbox = new HBox(convertValue("id=" + columnModel.getId()),
                            convertValue("名称是："+columnModel.getTitle()));
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15));

        JFXButton button = new JFXButton("关闭");
        button.getStyleClass().add("primary-button"); // 添加样式类
        button.setOnAction(event -> primaryStage.close());

        // 将控件放置在VBox中
        VBox vbox = new VBox(hbox, button);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));

        // 创建StackPane并将vbox添加到其中
        StackPane root = new StackPane(vbox);

        // 创建场景并将根节点添加到其中
        Scene scene = new Scene(root, 300, 200);


        // 添加CSS样式表
        scene.getStylesheets().add("/jfoenix.css");
        // 设置标题并显示舞台
        primaryStage.setTitle("JFoenix Demo");
        primaryStage.setScene(scene);

        primaryStage.showAndWait();
    }

    private JFXTextField convertValue(String value){
        JFXTextField textField = new JFXTextField();
        textField.setText(value);
        return textField;
    }

    public void show() {
        stage.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
