package com.crossbowffs.javautils.calculator.operators;

public class PowerOperator extends BinaryOperator {
    @Override
    protected double operate(double left, double right) {
        return Math.pow(left, right);
    }
}
