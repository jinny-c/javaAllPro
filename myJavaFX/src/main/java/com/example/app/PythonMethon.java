package com.example.app;

import org.python.util.PythonInterpreter;

/**
 * @author chaijd
 * @description TODO
 * @date 2024-02-18
 */
public class PythonMethon {
    public static void main(String[] args) {
        methodTo();
    }

    private static void methodTo(){
        // 创建 Python 解释器对象
        PythonInterpreter interpreter = new PythonInterpreter();

        // 调用 Python 函数并传递参数
        interpreter.execfile("javaAllPro/myJavaFX/src/main/resources/python/script.py");
        interpreter.set("x", 3);
        interpreter.set("y", 4);
        interpreter.exec("result = multiply(x, y)");
        int result = interpreter.get("result", Integer.class);
        System.out.println("Result from Python: " + result);

        // 获取 Python 变量并输出
        String greeting = interpreter.get("greeting", String.class);
        System.out.println("Message from Python: " + greeting);

        // 调用 Java 方法并传递参数
        MyJavaClass javaObj = new MyJavaClass("Hello from Java!");
        int sum = javaObj.add(5, 7);
        System.out.println("Result from Java: " + sum);

        // 获取 Java 变量并输出
        String message = javaObj.getMessage();
        System.out.println("Message from Java: " + message);
    }

    static class MyJavaClass {
        private String message;

        public MyJavaClass(String message) {
            this.message = message;
        }

        public int add(int x, int y) {
            return x + y;
        }

        public String getMessage() {
            return message;
        }
    }
}
