package com.crossbowffs.javautils.shapes;

public class Rectangle extends Shape {

    private double _width;
    private double _height;

    public Rectangle(double w, double h) throws IllegalArgumentException {
        ensureValidRectangle(w, h);
        _width = w;
        _height = h;
    }

    public double getWidth() {
        return _width;
    }

    public Rectangle setWidth(double value) throws IllegalArgumentException {
        return new Rectangle(value, _height);
    }

    public double getHeight() {
        return _height;
    }

    public Rectangle setHeight(double value) throws IllegalArgumentException {
        return new Rectangle(_height, value);
    }

    @Override
    public String toString() {
        return _width == _height ? "Square" : "Rectangle";
    }

    @Override
    public double getArea() {
        return _width * _height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (_width + _height);
    }

    public double getDiagonal() {
        return Math.sqrt(_width * _width + _height * _height);
    }

    public boolean isSquare() {
        return _width == _height;
    }

    private static boolean isValidRectangle(double w, double h) {
        return w > 0 && h > 0;
    }

    private static void ensureValidRectangle(double w, double h) throws IllegalArgumentException {
        if (!isValidRectangle(w, h))
            throw new IllegalArgumentException();
    }
}
