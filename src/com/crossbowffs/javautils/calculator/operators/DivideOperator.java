package com.crossbowffs.javautils.calculator.operators;

public class DivideOperator extends BinaryOperator {
    @Override
    protected double operate(double left, double right) {
        return left / right;
    }
}
