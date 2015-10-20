package com.crossbowffs.javautils.homework;

import com.crossbowffs.javautils.arrays.ArrayPrinter;

public class HelixMatrix {
    public static int[][] getMatrix(int size) {
        int[][] mat = new int[size][size];
        fillMatrix(mat, size, 0, -1, 0, 0);
        return mat;
    }

    private static void fillMatrix(int[][] matrix, int size, int dir, int x, int y, int num) {
        if (size == 0) return;
        double d = dir * Math.PI / 2;
        int dx = (int)Math.round(Math.cos(d));
        int dy = (int)Math.round(Math.sin(d));
        for (int i = 0; i < size; ++i) {
            matrix[y += dy][x += dx] = ++num;
        }
        if (dir % 2 == 0) size -= 1;
        fillMatrix(matrix, size, dir + 1, x, y, num);
    }

    public static void main(String[] args) {
        ArrayPrinter.println2D(getMatrix(11));
    }
}
