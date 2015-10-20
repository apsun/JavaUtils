package com.crossbowffs.javautils.shapes;

public class Circle extends Shape {
    public static final double TWO_PI = 2 * Math.PI;

    private final double _radius;

    public Circle(double radius) {
        ensureValidCircle(radius);
        _radius = radius;
    }

    public double getRadius() {
        return _radius;
    }

    public Circle setRadius(double radius) {
        return new Circle(radius);
    }

    @Override
    public String toString() {
        return "Circle";
    }

    @Override
    public double getArea() {
        return Math.PI * _radius * _radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * _radius;
    }

    public double getDiameter() {
        return 2 * _radius;
    }

    public double getArcLength(double angle) {
        return getPerimeter() * angle / TWO_PI;
    }

    public double getSectorArea(double angle) {
        return getArea() * angle / TWO_PI;
    }

    public double getChordLength(double angle) {
        return _radius * Math.sqrt(2 - 2 * Math.cos(angle));
    }

    public double getSegmentArea(double angle) {
        return getSectorArea(angle) - _radius * _radius * Math.sin(angle) / 2;
    }

    private static boolean isValidCircle(double radius) {
        return radius > 0;
    }

    private static void ensureValidCircle(double radius) {
        if (!isValidCircle(radius))
            throw new IllegalArgumentException();
    }
}
