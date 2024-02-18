package com.example.app;

import javafx.application.Application;import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.python.util.PythonInterpreter;


/**
 * @author chaijd
 * @description 可执行 python 方法
 * @date 2024-02-18
 */
public class DoPythonMethodAppMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Button btn = new Button("Run Python Code");
        btn.setOnAction(event -> {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("print('Hello from Python!')");

            // 设置默认编码为 UTF-8
            interpreter.exec("import sys");
            interpreter.exec("reload(sys)");
            interpreter.set("utf8", "utf-8");
            interpreter.set("sys.setdefaultencoding", "utf8");

            // 执行 Python 文件
            interpreter.execfile("javaAllPro/myJavaFX/src/main/resources/python/SomeQuickSort.py");
            // 在执行后查看结果
            // 例如，如果您的 Python 文件中有一个名为 result 的变量，您可以通过以下方式获取它的值
            Object result = interpreter.get("result", Object.class);

            // 输出结果
            System.out.println("Python 文件的执行结果是：" + result);
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("JavaFX with Python");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
