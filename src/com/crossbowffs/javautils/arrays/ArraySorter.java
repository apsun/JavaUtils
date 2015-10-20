package com.crossbowffs.javautils.arrays;

import com.crossbowffs.javautils.Stopwatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ArraySorter {
    public static final ArraySorter ascending = new ArraySorter(true);
    public static final ArraySorter descending = new ArraySorter(false);

    private final boolean _ascending;

    private ArraySorter(boolean ascending) {
        _ascending = ascending;
    }

    public void insertionSort(int[] array) {
        for (int i = 1; i < array.length; ++i) {
            int item = array[i];
            int j = i;
            while (j > 0 && compare(item, array[j - 1]))
                array[j] = array[--j];
            array[j] = item;
        }
    }

    public void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1; ++j) {
                int val1 = array[j];
                int val2 = array[j + 1];
                if (compare(val2, val1)) {
                    array[j] = val2;
                    array[j + 1] = val1;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            int imin = i;
            int min = array[i];
            for (int j = i + 1; j < array.length; ++j) {
                int val = array[j];
                if (compare(val, min)) {
                    imin = j;
                    min = val;
                }
            }
            if (i != imin) {
                array[imin] = array[i];
                array[i] = min;
            }
        }
    }

    public void mergeSort(int[] array) {
        if (array.length <= 1) return;
        int mid = midpoint(0, array.length);
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(left);
        mergeSort(right);
        int imain, ileft, iright;
        imain = ileft = iright = 0;
        while (ileft < left.length && iright < right.length)
            if (compareEquals(left[ileft], right[iright]))
                array[imain++] = left[ileft++];
            else
                array[imain++] = right[iright++];
        while (ileft < left.length)
            array[imain++] = left[ileft++];
        while (iright < right.length)
            array[imain++] = right[iright++];
    }

    public void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) {
        int pivot, ileft, iright;
        pivot = array[midpoint(low, high)];
        ileft = low;
        iright = high;
        while (ileft <= iright) {
            while (compare(array[ileft], pivot)) ++ileft;
            while (compare(pivot, array[iright])) --iright;
            if (ileft > iright) break;
            int templeft = array[ileft];
            int tempright = array[iright];
            array[ileft++] = tempright;
            array[iright--] = templeft;
        }
        if (low < iright)
            quickSort(array, low, iright);
        if (ileft < high)
            quickSort(array, ileft, high);
    }

    private int midpoint(int low, int high) {
        return low + (high - low) / 2;
    }

    private boolean compare(int a, int b) {
        return _ascending ? (a < b) : (a > b);
    }

    private boolean compareEquals(int a, int b) {
        return _ascending ? (a <= b) : (a >= b);
    }

    public static void main(String[] args) {
        //-------Constants-------
        final int arraySize = 5000;
        final int valMin = 0;
        final int valMax = 10;
        final boolean ascending = true;
        //--------Arrays---------
        int[] rnd = ArrayGenerator.random.intArray(arraySize, valMin, valMax);
        int[] asc = ArrayGenerator.ascending.intArray(arraySize, valMin, valMax);
        int[] desc = ArrayGenerator.descending.intArray(arraySize, valMin, valMax);
        //---------Names---------
        String[] enabledAlgorithmNames = {
                "bubbleSort",
                "selectionSort",
                "insertionSort",
                "mergeSort",
                "quickSort"};
        String[] sortOrderNames = {
                "Random",
                "Sorted",
                "Reverse"};
        int[][] arrays = {
                rnd,
                ascending ? asc : desc,
                ascending ? desc : asc};
        //-----------------------

        Stopwatch sw = new Stopwatch();
        System.out.println("Trial data:");
        System.out.println("Array size:      " + arraySize);
        System.out.println("Sort order:      " + (ascending ? "Ascending" : "Descending"));
        System.out.println("Array min value: " + valMin);
        System.out.println("Array max value: " + valMax);
        System.out.println();

        for (String methodName : enabledAlgorithmNames) {
            System.out.println(methodName + ":");
            try {
                Method method = ArraySorter.class.getMethod(methodName, int[].class);
                for (int i = 0; i < arrays.length; ++i) {
                    int[] array = arrays[i].clone();
                    sw.start();
                    //noinspection PrimitiveArrayArgumentToVariableArgMethod
                    method.invoke(ascending ? ArraySorter.ascending : ArraySorter.descending, array);
                    sw.stop();
                    System.out.println("[" + sortOrderNames[i] + "]\tTime taken: " + sw);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
