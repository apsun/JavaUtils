package com.crossbowffs.javautils.calculator.operators;

public class AddOperator extends BinaryOperator {
    @Override
    protected double operate(double left, double right) {
        return left + right;
    }
}
