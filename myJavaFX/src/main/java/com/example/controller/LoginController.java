package com.example.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends Application {

    public static final String USERNAME = "admin";

    public static final String PASSWORD = "123456";

    private TextField userTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            //设置窗体的标题
            stage.setTitle("登录");
            //网格布局方式,创建一个GridPane面板
            GridPane grid = new GridPane();
            //改变grid的默认位置，默认情况下，grid的位置是在其父容器的左上方，此处父容器是Scene，现在将grid移至Scene的中间
            grid.setAlignment(Pos.CENTER);
            //是用来设置该网格每行和每列之间的水平间距和垂直间距的
            grid.setHgap(10);
            grid.setVgap(10);
            //设置了环绕在该网格面板上的填充距离，这里网格默认被设为在场景容器中居中，这里的填充距离是表示网格边缘距离里面内容的距离。
            // 设置内边距,传入的是一个Insets对象，该insets对象的参数是：上，左，下，右
            grid.setPadding(new Insets(25, 25, 25, 25));

            //<span style="font-size:14px">
            Text sceneTitle = new Text("    想听就听");
            sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            sceneTitle.setFill(Color.ROYALBLUE);

            //设置倒影
            Reflection r = new Reflection();
            r.setFraction(0.7);
            sceneTitle.setEffect(r);

            //grid.add()方法，该方法，有五个参数，该参数不是每个都必填的，分别是要放入的组件，以及第几列，第几行，跨几列和跨几行。
            // 因此我们将Text组件放在第一行，第一列并且跨两列和跨一行；将‘UserName’的Label组件放在第二行第一列，TextField放在第二行第二列，
            // 将‘Password’的Lable组件放在第三行第一列，PasswordField放在第三行第二列。在grid中行和列是从0开始算起的。
            grid.add(sceneTitle, 1, 0, 2, 1);

            //用户名
            Label userName = new Label("用户名:");
            grid.add(userName, 0, 3);

            //用户名输入文本框
            userTextField = new TextField();
            grid.add(userTextField, 1, 3);

            //密码
            Label password = new Label("密   码:");
            grid.add(password, 0, 4);

            //密码输入文本框
            PasswordField passwordField = new PasswordField();
            grid.add(passwordField, 1, 4);
            //</span>

            //登录 按钮
            Button loginButton = new Button("登录");
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.BOTTOM_RIGHT);
            hBox.getChildren().add(loginButton);
            grid.add(hBox, 1, 6);

            //添加一个文本框，用于显示信息的控制
            Text actionTarget = new Text();
            actionTarget.setId("actionTarget");
            grid.add(actionTarget, 1, 8);

            //声明点击事件,点击显示文本信息
            loginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (USERNAME.equals(userTextField.getText()) && PASSWORD.equals(passwordField.getText())) {
                        actionTarget.setFill(Color.GREENYELLOW);
                        actionTarget.setText("        欢迎登录...");
                        MusicPlayerController musicPlayerController = new MusicPlayerController();
                        stage.close();
                        Stage newStage = new Stage();
                        try {
                            musicPlayerController.start(newStage);
                        } catch (Exception e) {

                        }
                    }
                }
            });

            //表示为该舞台创建一个场景，上面的网格就是被安置在这个场景中的，该场景的大小为300和275个像素，oracle官网推荐，
            // 在创建场景的时候，好的做法是给该场景设置大小，如果不设置大小则会默认自动更具场景中的内容调整场景的大小，
            // 此外该场景的大小也直接决定于外围舞台的大小。
            Scene scene = new Scene(grid, 300, 275);
            //场景引入css文件
            //scene.getStylesheets().add(LoginController.class.getResource("LoginController.css").toExternalForm());
            //将场景加入舞台中
            stage.setScene(scene);
            //让窗体显示
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public TextField getUserTextField() {
        return userTextField;
    }

    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }
}