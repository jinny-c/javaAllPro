package com.example.app;

import org.python.util.PythonInterpreter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author chaijd
 * @description TODO
 * @date 2024-02-18
 */
public class PythonMethon {
    public static void main(String[] args) {
//        methodTo1();
        methodTo2();
    }

    private static PythonInterpreter initPythonInterpreter(){
        // 获取当前类的 ClassLoader
        //ClassLoader classLoader = PythonMethon.class.getClassLoader();
        // 获取当前线程的上下文类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 使用 ClassLoader 加载资源文件
        //InputStream inputStream = classLoader.getResourceAsStream("python/script.py");

        // 使用类加载器加载资源文件，并获取资源文件的 URL
        URL resourceUrl = classLoader.getResource("python/script.py");
        // 获取 Python 文件所在的目录
        String pythonFileDirectory = new File(resourceUrl.getPath()).getParent();

        // 设置 user.dir 为 Python 文件所在的目录
        System.setProperty("user.dir", pythonFileDirectory);

        // 创建 Python 解释器对象
        return new PythonInterpreter();
    }
    private static void methodTo1(){
        PythonInterpreter interpreter = initPythonInterpreter();

        // 调用 Python 函数并传递参数
        //interpreter.execfile("javaAllPro/myJavaFX/src/main/resources/python/script.py");
        //interpreter.execfile(inputStream);
        interpreter.execfile("script.py");
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


    private static void methodTo2(){
        PythonInterpreter interpreter = initPythonInterpreter();

        // 调用 Python 函数并传递参数
        interpreter.execfile("SomeQuickSort.py");
        Integer[] arr = new Integer[]{11, 1, 22, 2, 33, 3, 44, 5, 4, 6, 55, 77};
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(i + 3));
        }
        System.out.println("Result from Python: " + list);
        //interpreter.set("arr", arr);
        interpreter.set("arr", list);
        interpreter.exec("result = quick_sort(arr)");
        List result = interpreter.get("result", List.class);

        System.out.println("Result from Python: " + result.get(0).getClass());
        System.out.println("Result from Python: " + result);

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
