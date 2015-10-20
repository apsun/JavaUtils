package com.crossbowffs.javautils.arrays;

public abstract class ArrayGenerator {

    private static class RandomArrayGenerator extends ArrayGenerator {
        public int[] intArray(int size, int min, int max) {
            int[] array = new int[size];
            for (int i = 0; i < array.length; ++i)
                array[i] = min + (int)(Math.random() * max);
            return array;
        }

        public double[] doubleArray(int size, double min, double max) {
            double[] array = new double[size];
            for (int i = 0; i < array.length; ++i)
                array[i] = min + (Math.random() * max);
            return array;
        }
    }

    private static class AscendingArrayGenerator extends ArrayGenerator {
        public int[] intArray(int size, int min, int max) {
            int[] array = new int[size];
            int range = max - min;
            for (int i = 0; i < array.length; ++i) {
                double ratio = i / (double)array.length;
                array[i] = min + (int)(ratio * range);
            }
            return array;
        }

        public double[] doubleArray(int size, double min, double max) {
            double[] array = new double[size];
            double range = max - min;
            for (int i = 0; i < array.length; ++i) {
                double ratio = i / (double)array.length;
                array[i] = min + (ratio * range);
            }
            return array;
        }
    }

    private static class DescendingArrayGenerator extends ArrayGenerator {
        public int[] intArray(int size, int min, int max) {
            int[] array = new int[size];
            int range = max - min;
            for (int i = 0; i < array.length; ++i) {
                double ratio = (array.length - i - 1) / (double)array.length;
                array[i] = min + (int)(ratio * range);
            }
            return array;
        }

        public double[] doubleArray(int size, double min, double max) {
            double[] array = new double[size];
            double range = max - min;
            for (int i = 0; i < array.length; ++i) {
                double ratio = (array.length - i - 1) / (double)array.length;
                array[i] = min + (ratio * range);
            }
            return array;
        }
    }

    public static final ArrayGenerator random = new RandomArrayGenerator();
    public static final ArrayGenerator ascending = new AscendingArrayGenerator();
    public static final ArrayGenerator descending = new DescendingArrayGenerator();

    public abstract int[] intArray(int size, int min, int max);

    public abstract double[] doubleArray(int size, double min, double max);
}
