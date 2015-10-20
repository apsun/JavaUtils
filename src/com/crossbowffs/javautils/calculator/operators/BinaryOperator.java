package com.crossbowffs.javautils.calculator.operators;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class BinaryOperator extends BaseOperator {
    @Override
    public final void act(Stack<Double> numStack) {
        if (numStack.size() < 2)
            throw new EmptyStackException();
        double right = numStack.pop();
        double left = numStack.pop();
        numStack.push(operate(left, right));
    }

    protected abstract double operate(double left, double right);
}
