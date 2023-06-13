package com.example.app.calculator;

public class Calculator {

    private float A;
    private float B;

    /**
     * operation: 计算操作
     * defined:
     * 1 => plus
     * 2 => minus
     * 3 => multiply
     * 4 => divide
     * 5 => =号
     */
    private int operation = -1;

    public Calculator() {
    }

    public void clear() {
        A = B = 0f;
        operation = -1;
    }

    public float plus() {
        return A + B;
    }

    public float minus() {
        return A - B;
    }

    public float multiply() {
        return A * B;
    }

    public float divide() {
        return A / B;
    }

    public void setA(float a) {
        A = a;
    }

    public void setB(float b) {
        B = b;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
    public int getOperation(){
        return this.operation;
    }

    public float getResult() {
        float result = 0f;
        switch (operation) {
            case 1: result = plus(); break;
            case 2: result = minus(); break;
            case 3: result = multiply(); break;
            case 4: result = divide(); break;
            default: result = A;
        }
        A = result;
        B = 0f;
        return result;
    }
}
