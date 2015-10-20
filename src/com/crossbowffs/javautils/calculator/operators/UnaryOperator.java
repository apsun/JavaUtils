package com.crossbowffs.javautils.calculator.operators;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class UnaryOperator extends BaseOperator {
    @Override
    public void act(Stack<Double> numStack) {
        if (numStack.size() < 1)
            throw new EmptyStackException();
        double num = numStack.pop();
        numStack.push(operate(num));
    }

    protected abstract double operate(double num);
}
