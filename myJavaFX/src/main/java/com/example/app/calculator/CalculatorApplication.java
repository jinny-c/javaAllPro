package com.example.app.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorApplication extends Application {

    private Button clear;
    private Button plus;
    private Button minus;
    private Button mult;
    private Button div;
    private Button equals;

    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;

    private TextField display;

    private Calculator calculator = new Calculator();
    private String temp = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        // layout
        VBox vBox = new VBox();
        layoutView(vBox);
        vBox.getStylesheets().add("calculator.css");

        // event handlers
        // 补全逻辑代码
        zero.setOnAction(event -> numClick("0"));
        one.setOnAction(event -> numClick("1"));
        two.setOnAction(event -> numClick("2"));
        three.setOnAction(event -> numClick("3"));
        four.setOnAction(event -> numClick("4"));
        five.setOnAction(event -> numClick("5"));
        six.setOnAction(event -> numClick("6"));
        seven.setOnAction(event -> numClick("7"));
        eight.setOnAction(event -> numClick("8"));
        nine.setOnAction(event -> numClick("9"));

        clear.setOnAction(this::handleButtonAction);
        plus.setOnAction(this::handleButtonAction);
        minus.setOnAction(this::handleButtonAction);
        mult.setOnAction(this::handleButtonAction);
        div.setOnAction(this::handleButtonAction);
        equals.setOnAction(this::handleButtonAction);

        // structure
        Scene scene = new Scene(vBox, 400, 500);
        primaryStage.setTitle("Calculator Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void numClick(String num) {
//        display.setText(temp + num);
//        temp = display.getText();

        display.setText(temp += num);
    }

    // 补全代码
    private void handleButtonAction(ActionEvent event) {
        int val = convertButton(event.getSource());
        switch (val) {
            case 0:
                calculator.clear();
                display.setText("");
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                //上次点击
                int lastClick = calculator.getOperation();
                if (lastClick == -1 || lastClick == 5) {//第一次 或者 是=号
                    calculator.setA(Float.parseFloat(display.getText()));
                    calculator.setOperation(val);
                    break;
                }

                calculator.setB(Float.parseFloat(display.getText()));
                display.setText(calculator.getResult() + "");
                calculator.setOperation(val);
                break;
            case 5:
                calculator.setB(Float.parseFloat(display.getText()));
                display.setText(calculator.getResult() + "");
                calculator.setOperation(val);
                break;
            default:
                display.setText("error");
                break;
        }
        temp = "";
    }

    private int convertButton(Object source) {
        if (source == clear) {
            return 0;
        }
        if (source == plus) {
            return 1;
        }
        if (source == minus) {
            return 2;
        }
        if (source == mult) {
            return 3;
        }
        if (source == div) {
            return 4;
        }
        if (source == equals) {
            return 5;
        }
        return -1;
    }

    private void layoutView(VBox vBox) {
        display = new TextField();
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(false);
        GridPane gridPane = new GridPane();
        zero = new Button("0");
        one = new Button("1");
        two = new Button("2");
        three = new Button("3");
        four = new Button("4");
        five = new Button("5");
        six = new Button("6");
        seven = new Button("7");
        eight = new Button("8");
        nine = new Button("9");

        clear = new Button("CL");
        plus = new Button("+");
        minus = new Button("-");
        mult = new Button("*");
        div = new Button("/");
        equals = new Button("=");

        gridPane.add(one, 0, 0);
        gridPane.add(two, 1, 0);
        gridPane.add(three, 2, 0);
        gridPane.add(plus, 3, 0);
        gridPane.add(four, 0, 1);
        gridPane.add(five, 1, 1);
        gridPane.add(six, 2, 1);
        gridPane.add(minus, 3, 1);
        gridPane.add(seven, 0, 2);
        gridPane.add(eight, 1, 2);
        gridPane.add(nine, 2, 2);
        gridPane.add(mult, 3, 2);
        gridPane.add(zero, 0, 3);
        gridPane.add(equals, 1, 3);
        gridPane.add(clear, 2, 3);
        gridPane.add(div, 3, 3);

        vBox.getChildren().addAll(display, gridPane);
    }


}
