package com.crossbowffs.javautils.arrays;

public class ArraySearcher {
    public static <T extends Comparable<? super T>> int sequentialForwardSearch(T[] array, T item) {
        for (int i = 0; i < array.length; ++i) {
            if (item.compareTo(array[i]) == 0) return i;
        }
        return -1;
    }

    public static <T extends Comparable<? super T>> int sequentialReverseSearch(T[] array, T item) {
        for (int i = array.length; i >= 0; --i) {
            if (item.compareTo(array[i]) == 0) return i;
        }
        return -1;
    }

    public static <T extends Comparable<? super T>> int binarySearch(T[] array, T item) {
        return binarySearch(array, item, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> int binarySearch(T[] array, T item, int start, int end) {
        if (end < start) return -1;
        int index = midpoint(start, end);
        T arrItem = array[index];
        int cmp = item.compareTo(arrItem);
        if (cmp < 0) return binarySearch(array, item, start, index - 1);
        if (cmp > 0) return binarySearch(array, item, index + 1, end);
        return index;
    }

    private static int midpoint(int a, int b) {
        return a + ((b - a) / 2);
    }
}
