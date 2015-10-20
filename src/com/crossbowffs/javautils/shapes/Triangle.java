package com.crossbowffs.javautils.shapes;

import com.crossbowffs.javautils.FloatComparer;

public class Triangle extends Shape {

    public enum AngleType {
        Acute,
        Right,
        Obtuse
    }

    public enum SideType {
        Scalene,
        Isosceles,
        Equilateral
    }

    public static final double PI_OVER_2 = Math.PI / 2;
    private final double _sideA;
    private final double _sideB;
    private final double _sideC;
    private final double _angleA;
    private final double _angleB;
    private final double _angleC;

    public Triangle(double a, double b, double c) throws IllegalArgumentException {
        ensureValidTriangle(a, b, c);

        _sideA = a;
        _sideB = b;
        _sideC = c;

        double a2 = a * a;
        double b2 = b * b;
        double c2 = c * c;

        _angleA = Math.acos((b2 + c2 - a2) / (2 * b * c));
        _angleB = Math.acos((a2 + c2 - b2) / (2 * a * c));
        _angleC = Math.acos((a2 + b2 - c2) / (2 * a * b));
    }

    public double getSideA() {
        return _sideA;
    }

    public Triangle setSideA(double value) throws IllegalArgumentException {
        return new Triangle(value, _sideB, _sideC);
    }

    public double getSideB() {
        return _sideB;
    }

    public Triangle setSideB(double value) throws IllegalArgumentException {
        return new Triangle(_sideA, value, _sideC);
    }

    public double getSideC() {
        return _sideC;
    }

    public Triangle setSideC(double value) throws IllegalArgumentException {
        return new Triangle(_sideA, _sideB, value);
    }

    public double getAngleA() {
        return _angleA;
    }

    public double getAngleB() {
        return _angleB;
    }

    public double getAngleC() {
        return _angleC;
    }

    @Override
    public String toString() {
        String angleType = getAngleType().toString();
        String sideType = getSideType().toString().toLowerCase();
        return String.format("%s %s triangle", angleType, sideType);
    }

    @Override
    public double getArea() {
        double s = getPerimeter() / 2.0;
        return Math.sqrt(s * (s - _sideA) * (s - _sideB) * (s - _sideC));
    }

    @Override
    public double getPerimeter() {
        return _sideA + _sideB + _sideC;
    }

    public SideType getSideType() {
        if (_sideA == _sideB)
            if (_sideA == _sideC)
                return SideType.Equilateral;
            else
                return SideType.Isosceles;

        if (_sideB == _sideC)
            return SideType.Isosceles;

        if (_sideC == _sideA)
            return SideType.Isosceles;

        return SideType.Scalene;
    }

    public AngleType getAngleType() {
        if (FloatComparer.radians.allLessThan(PI_OVER_2, _angleA, _angleB, _angleC))
            return AngleType.Acute;

        if (FloatComparer.radians.anyEquals(PI_OVER_2, _angleA, _angleB, _angleC))
            return AngleType.Right;

        return AngleType.Obtuse;
    }

    private static boolean isValidTriangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) return false;
        if (a + b <= c || b + c <= a || c + a <= b) return false;
        return true;
    }

    private static void ensureValidTriangle(double a, double b, double c) {
        if (!isValidTriangle(a, b, c))
            throw new IllegalArgumentException();
    }
}
