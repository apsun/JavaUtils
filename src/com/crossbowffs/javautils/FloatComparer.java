package com.crossbowffs.javautils;

public class FloatComparer {

    public static final FloatComparer radians = new FloatComparer(Math.PI / 1000000);
    public static final FloatComparer degrees = new FloatComparer(180.0 / 1000000);
    private static final boolean _inclusiveEpsilon = false;
    private final double _epsilon;

    public FloatComparer(double epsilon) {
        _epsilon = epsilon;
    }

    public boolean equals(double a, double b) {
        //NNNYY.YYNNN
        return _inclusiveEpsilon ? Math.abs(a - b) <= _epsilon
                : Math.abs(a - b) < _epsilon;
    }

    public boolean lessThan(double a, double b) {
        //YYYNN.NNNNN
        return _inclusiveEpsilon ? a + _epsilon <= b
                : a + _epsilon < b;
    }

    public boolean greaterThan(double a, double b) {
        //NNNNN.NNYYY
        return lessThan(b, a);
    }

    public boolean lessThanEquals(double a, double b) {
        //YYYYY.YYNNN
        return !greaterThan(a, b);
    }

    public boolean greaterThanEquals(double a, double b) {
        //NNNYY.YYYYY
        return lessThanEquals(b, a);
    }

    public boolean anyEquals(double target, double... values) {
        for (double num : values) {
            if (equals(num, target)) return true;
        }

        return false;
    }

    public boolean anyLessThan(double target, double... values) {
        for (double num : values) {
            if (lessThan(num, target)) return true;
        }

        return false;
    }

    public boolean anyGreaterThan(double target, double... values) {
        for (double num : values) {
            if (greaterThan(num, target)) return true;
        }

        return false;
    }

    public boolean anyLessThanEquals(double target, double... values) {
        for (double num : values) {
            if (lessThanEquals(num, target)) return true;
        }

        return false;
    }

    public boolean anyGreaterThanEquals(double target, double... values) {
        for (double num : values) {
            if (greaterThanEquals(num, target)) return true;
        }

        return false;
    }

    public boolean allEquals(double target, double... values) {
        for (double num : values) {
            if (!equals(num, target)) return false;
        }

        return true;
    }

    public boolean allLessThan(double target, double... values) {
        return !anyGreaterThanEquals(target, values);
    }

    public boolean allGreaterThan(double target, double... values) {
        return !anyLessThanEquals(target, values);
    }

    public boolean allLessThanEquals(double target, double... values) {
        return !anyGreaterThan(target, values);
    }

    public boolean allGreaterThanEquals(double target, double... values) {
        return !anyLessThan(target, values);
    }
}
