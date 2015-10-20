package com.crossbowffs.javautils.calculator.operators;

public class NegateOperator extends UnaryOperator {
    @Override
    protected double operate(double num) {
        return -num;
    }
}
