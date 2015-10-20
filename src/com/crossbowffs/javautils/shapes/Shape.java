package com.crossbowffs.javautils.shapes;

public abstract class Shape {

    public abstract double getArea();

    public abstract double getPerimeter();

    public int compareArea(Shape other) {
        return (int)Math.signum(getArea() - other.getArea());
    }

    public int comparePerimeter(Shape other) {
        return (int)Math.signum(getPerimeter() - other.getPerimeter());
    }
}