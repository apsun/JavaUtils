package com.crossbowffs.javautils.arrays;

public class ArrayPrinter {
    public static void println1D(int[] array) {
        print1D(array);
        System.out.println();
    }

    public static void print1D(int[] array) {
        Integer[] objArray = ArrayConverter.toWrapped1D(array);
        print1D(objArray);
    }

    public static void println1D(double[] array) {
        print1D(array);
        System.out.println();
    }

    public static void print1D(double[] array) {
        Double[] objArray = ArrayConverter.toWrapped1D(array);
        print1D(objArray);
    }

    public static void println1D(Object primitiveArray) {
        print1D(primitiveArray);
        System.out.println();
    }

    public static void print1D(Object primitiveArray) {
        Object[] objArray = ArrayConverter.toWrapped1D(primitiveArray);
        print1D(objArray);
    }

    public static void println1D(Object[] array) {
        print1D(array);
        System.out.println();
    }

    public static void print1D(Object[] array) {
        String s = ArrayCombiner.brackets.toString1D(array);
        System.out.print(s);
    }

    public static void println2D(int[][] array) {
        print2D(array);
        System.out.println();
    }

    public static void print2D(int[][] array) {
        Integer[][] objArray = ArrayConverter.toWrapped2D(array);
        print2D(objArray);
    }

    public static void println2D(double[][] array) {
        print2D(array);
        System.out.println();
    }

    public static void print2D(double[][] array) {
        Double[][] objArray = ArrayConverter.toWrapped2D(array);
        print2D(objArray);
    }

    public static void println2D(Object primitiveArray) {
        print2D(primitiveArray);
        System.out.println();
    }

    public static void print2D(Object primitiveArray) {
        Object[][] objArray = ArrayConverter.toWrapped2D(primitiveArray);
        print2D(objArray);
    }

    public static void println2D(Object[][] array) {
        print2D(array);
        System.out.println();
    }

    public static void print2D(Object[][] array) {
        String s = ArrayCombiner.brackets.toString2D(array);
        System.out.print(s);
    }
}
