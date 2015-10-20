package com.crossbowffs.javautils.arrays;

import java.lang.reflect.Array;

public class ArrayConverter {
    public static Integer[] toWrapped1D(int[] array) {
        Integer[] converted = new Integer[array.length];
        for (int i = 0; i < array.length; ++i)
            converted[i] = array[i];
        return converted;
    }

    public static Double[] toWrapped1D(double[] array) {
        Double[] converted = new Double[array.length];
        for (int i = 0; i < array.length; ++i)
            converted[i] = array[i];
        return converted;
    }

    public static Integer[][] toWrapped2D(int[][] array) {
        Integer[][] converted = new Integer[array.length][];
        for (int i = 0; i < array.length; ++i) {
            converted[i] = new Integer[array[i].length];
            for (int j = 0; j < array[i].length; ++j)
                converted[i][j] = array[i][j];
        }
        return converted;
    }

    public static Double[][] toWrapped2D(double[][] array) {
        Double[][] converted = new Double[array.length][];
        for (int i = 0; i < array.length; ++i) {
            converted[i] = new Double[array[i].length];
            for (int j = 0; j < array[i].length; ++j)
                converted[i][j] = array[i][j];
        }
        return converted;
    }

    public static int[] toPrimitive1D(Integer[] array) {
        int[] converted = new int[array.length];
        for (int i = 0; i < array.length; ++i)
            converted[i] = array[i];
        return converted;
    }

    public static double[] toPrimitive1D(Double[] array) {
        double[] converted = new double[array.length];
        for (int i = 0; i < array.length; ++i)
            converted[i] = array[i];
        return converted;
    }

    public static int[][] toPrimitive2D(Integer[][] array) {
        int[][] converted = new int[array.length][];
        for (int i = 0; i < array.length; ++i) {
            converted[i] = new int[array[i].length];
            for (int j = 0; j < array[i].length; ++j)
                converted[i][j] = array[i][j];
        }
        return converted;
    }

    public static double[][] toPrimitive2D(Double[][] array) {
        double[][] converted = new double[array.length][];
        for (int i = 0; i < array.length; ++i) {
            converted[i] = new double[array[i].length];
            for (int j = 0; j < array[i].length; ++j)
                converted[i][j] = array[i][j];
        }
        return converted;
    }

    public static Object[] toWrapped1D(Object array) {
        int length = Array.getLength(array);
        Class type = getType(array, true).getComponentType();
        Object[] newArray = (Object[])Array.newInstance(type, length);
        for (int i = 0; i < length; ++i)
            newArray[i] = Array.get(array, i);
        return newArray;
    }

    public static Object[][] toWrapped2D(Object array) {
        int length = Array.getLength(array);
        Class type = getType(array, true).getComponentType();
        Class rowType = type.getComponentType();
        Object[][] newArray = (Object[][])Array.newInstance(type, length);
        for (int i = 0; i < length; ++i) {
            Object rowSrc = Array.get(array, i);
            int rowLength = Array.getLength(rowSrc);
            Object[] rowDest = (Object[])Array.newInstance(rowType, rowLength);
            for (int j = 0; j < rowLength; ++j)
                rowDest[j] = Array.get(rowSrc, j);
            newArray[i] = rowDest;
        }
        return newArray;
    }

    public static Object[] toWrapped(Object array) {
        return copyArrayToSynthetic(array, getType(array, true).getComponentType());
    }

    public static Object toPrimitive1D(Object[] array) {
        Class type = getType(array, false).getComponentType();
        Object newArray = Array.newInstance(type, array.length);
        for (int i = 0; i < array.length; ++i)
            Array.set(newArray, i, array[i]);
        return newArray;
    }

    public static Object toPrimitive2D(Object[][] array) {
        Class type = getType(array, false).getComponentType();
        Class rowType = type.getComponentType();
        Object[] newArray = (Object[])Array.newInstance(type, array.length);
        for (int i = 0; i < array.length; ++i) {
            Object[] rowSrc = array[i];
            Object rowDest = Array.newInstance(rowType, rowSrc.length);
            for (int j = 0; j < rowSrc.length; ++j)
                Array.set(rowDest, j, rowSrc[j]);
            newArray[i] = rowDest;
        }
        return newArray;
    }

    public static Object toPrimitive(Object[] array) {
        return copyArrayToPrimitive(array, getType(array, false).getComponentType());
    }

    private static Class getType(Object array, boolean toSynthetic) {
        Class arrayClass = array.getClass();
        int dimensions = 0;
        while (arrayClass.isArray()) {
            arrayClass = arrayClass.getComponentType();
            ++dimensions;
        }
        if (dimensions == 0) throw new IllegalArgumentException("Object is not an array");
        Class newType = toSynthetic ? getSyntheticTypeBase(arrayClass) : getPrimitiveTypeBase(arrayClass);
        int[] dummyDimensions = new int[dimensions];
        return Array.newInstance(newType, dummyDimensions).getClass();
    }

    private static Object copyArrayToPrimitive(Object[] array, Class type) {
        Object newArray = Array.newInstance(type, array.length);
        Class subType = type.getComponentType();
        boolean isArray = type.isArray();
        for (int i = 0; i < array.length; ++i)
            if (isArray)
                Array.set(newArray, i, copyArrayToPrimitive((Object[])array[i], subType));
            else
                Array.set(newArray, i, array[i]);
        return newArray;
    }

    private static Object[] copyArrayToSynthetic(Object array, Class type) {
        int length = Array.getLength(array);
        Object[] newArray = (Object[])Array.newInstance(type, length);
        Class subType = type.getComponentType();
        boolean isArray = type.isArray();
        for (int i = 0; i < length; ++i)
            if (isArray)
                newArray[i] = copyArrayToSynthetic(Array.get(array, i), subType);
            else
                newArray[i] = Array.get(array, i);
        return newArray;
    }

    private static Class getSyntheticTypeBase(Class type) {
        if (type == byte.class)
            return Byte.class;
        else if (type == short.class)
            return Short.class;
        else if (type == int.class)
            return Integer.class;
        else if (type == long.class)
            return Long.class;
        else if (type == char.class)
            return Character.class;
        else if (type == float.class)
            return Float.class;
        else if (type == double.class)
            return Double.class;
        else if (type == boolean.class)
            return Boolean.class;
        else
            return type;
    }

    private static Class getPrimitiveTypeBase(Class type) {
        if (type != getSyntheticTypeBase(type))
            throw new IllegalArgumentException("Array is already primitive");

        if (type == Byte.class)
            return byte.class;
        else if (type == Short.class)
            return short.class;
        else if (type == Integer.class)
            return int.class;
        else if (type == Long.class)
            return long.class;
        else if (type == Character.class)
            return char.class;
        else if (type == Float.class)
            return float.class;
        else if (type == Double.class)
            return double.class;
        else if (type == Boolean.class)
            return boolean.class;
        else
            throw new IllegalArgumentException("Array cannot be converted to a primitive array");
    }
}
