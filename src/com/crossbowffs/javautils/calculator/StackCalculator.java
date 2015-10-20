package com.crossbowffs.javautils.calculator;

import com.crossbowffs.javautils.calculator.operators.BaseOperator;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class StackCalculator {
    private Stack<Double> _numStack;

    public StackCalculator() {
        _numStack = new Stack<Double>();
    }

    public double popNumber() {
        return _numStack.pop();
    }

    public void inputNumber(double number) {
        _numStack.push(number);
    }

    public void inputOperator(BaseOperator operator) {
        operator.act(_numStack);
    }

    public double[] getStack() {
        double[] items = new double[_numStack.size()];
        int i = 0;
        for (double num : _numStack) {
            items[i++] = num;
        }
        return items;
    }

    public void dispatchInput(String input) {
        if (input.equalsIgnoreCase("pop")) {
            popNumber();
            return;
        }

        BaseOperator op = OperatorDispatcher.getOperator(input);
        if (op != null) {
            inputOperator(op);
            return;
        }

        inputNumber(Double.parseDouble(input));
    }

    public static void main(String[] args) {
        StackCalculator calc = new StackCalculator();
        Scanner input = new Scanner(System.in);
        while (true) {
            // Get input
            System.out.print("Enter next input: ");
            String str = input.nextLine().toLowerCase();

            // Process keywords
            if (str.equals("exit")) break;

            // Process input
            try {
                calc.dispatchInput(str);
            } catch (EmptyStackException ex) {
                System.out.println("Stack underflow!");
            } catch (NumberFormatException ex) {
                System.out.println("Enter a number or an operator!");
            }

            // Print current stack
            System.out.print("Stack: ");
            for (double num : calc.getStack()) {
                System.out.print(num);
                System.out.print('|');
            }
            System.out.println();
        }
    }
}
