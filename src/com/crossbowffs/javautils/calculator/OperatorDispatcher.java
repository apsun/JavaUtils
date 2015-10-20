package com.crossbowffs.javautils.calculator;

import com.crossbowffs.javautils.calculator.operators.*;

public class OperatorDispatcher {
    public static final BaseOperator ADD = new AddOperator();
    public static final BaseOperator SUBTRACT = new SubtractOperator();
    public static final BaseOperator MULTIPLY = new MultiplyOperator();
    public static final BaseOperator DIVIDE = new DivideOperator();
    public static final BaseOperator POWER = new PowerOperator();
    public static final BaseOperator MODULO = new ModuloOperator();
    public static final BaseOperator NEGATE = new NegateOperator();

    public static BaseOperator getOperator(String opStr) {
        if (opStr.equals("+")) return ADD;
        if (opStr.equals("-")) return SUBTRACT;
        if (opStr.equals("*")) return MULTIPLY;
        if (opStr.equals("/")) return DIVIDE;
        if (opStr.equals("^")) return POWER;
        if (opStr.equals("%")) return MODULO;
        if (opStr.equals("!")) return NEGATE;
        return null;
    }
}
