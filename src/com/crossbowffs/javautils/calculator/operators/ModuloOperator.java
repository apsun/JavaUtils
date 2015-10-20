package com.crossbowffs.javautils.calculator.operators;

public class ModuloOperator extends BinaryOperator {
    @Override
    protected double operate(double left, double right) {
        double num = left % right;
        if (num < 0) num += right;
        return num;
    }
}
