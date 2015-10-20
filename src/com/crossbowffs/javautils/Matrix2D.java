package com.crossbowffs.javautils;

import com.crossbowffs.javautils.arrays.ArrayCombiner;
import com.crossbowffs.javautils.arrays.ArrayConverter;

import java.util.Arrays;

public class Matrix2D {
    private double[][] _matrix;
    private int _hashCode;
    private boolean _hashDirty = true;

    private int _rows, _cols;

    public Matrix2D(int rows, int cols) {
        ensureDimensionsPositive(rows, cols);
        _matrix = new double[rows][cols];
        _rows = rows;
        _cols = cols;
    }

    public Matrix2D(double[][] matrix) {
        this(matrix, true, false);
    }

    public Matrix2D(Matrix2D source) {
        this(source._matrix, false, true);
    }

    private Matrix2D(double[][] matrix, boolean check, boolean copy) {
        if (check || copy) {
            double[][] safeMatrix = matrix;
            int rows = matrix.length;
            if (check) ensureDimensionPositive(rows);
            if (copy) safeMatrix = new double[rows][];
            int cols = matrix[0].length;
            if (check) ensureDimensionPositive(cols);
            if (copy) safeMatrix[0] = Arrays.copyOf(matrix[0], cols);
            for (int i = 1; i < matrix.length; ++i) {
                int rowlength = matrix[i].length;
                if (check && rowlength != cols)
                    throw new IllegalArgumentException("Matrix must be rectangular");
                if (copy) safeMatrix[i] = Arrays.copyOf(matrix[i], rowlength);
            }
            matrix = safeMatrix;
        }
        _matrix = matrix;
        _rows = matrix.length;
        _cols = matrix[0].length;
    }

    public int getRows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    public double get(int row, int col) {
        return _matrix[row][col];
    }

    public void set(int row, int col, double value) {
        _matrix[row][col] = value;
        _hashDirty = true;
    }

    public Matrix2D add(Matrix2D other) {
        int arc = _rows;
        int acc = _cols;
        int brc = other._rows;
        int bcc = other._cols;
        ensureDimensionsEqual(arc, brc);
        ensureDimensionsEqual(acc, bcc);
        double[][] res = new double[arc][acc];
        for (int i = 0; i < arc; ++i) {
            double[] arow = _matrix[i];
            double[] brow = other._matrix[i];
            for (int j = 0; j < acc; ++j)
                res[i][j] = arow[j] + brow[j];
        }
        return new Matrix2D(res, false, false);
    }

    public Matrix2D subtract(Matrix2D other) {
        int arc = _rows;
        int acc = _cols;
        int brc = other._rows;
        int bcc = other._cols;
        ensureDimensionsEqual(arc, brc);
        ensureDimensionsEqual(acc, bcc);
        double[][] res = new double[arc][acc];
        for (int i = 0; i < arc; ++i) {
            double[] arow = _matrix[i];
            double[] brow = other._matrix[i];
            for (int j = 0; j < acc; ++j)
                res[i][j] = arow[j] - brow[j];
        }
        return new Matrix2D(res, false, false);
    }

    public Matrix2D multiply(Matrix2D other) {
        int arc = _rows;
        int acc = _cols;
        int brc = other._rows;
        int bcc = other._cols;
        ensureDimensionsEqual(acc, brc);
        double[][] res = new double[arc][bcc];
        for (int i = 0; i < arc; ++i) {
            for (int j = 0; j < bcc; ++j) {
                int sum = 0;
                for (int k = 0; k < acc; ++k)
                    sum += _matrix[i][k] * other._matrix[k][j];
                res[i][j] = sum;
            }
        }
        return new Matrix2D(res, false, false);
    }

    public Matrix2D scale(double factor) {
        int arc = _rows;
        int acc = _cols;
        double[][] res = new double[arc][acc];
        for (int i = 0; i < arc; ++i)
            for (int j = 0; j < acc; ++j)
                res[i][j] = _matrix[i][j] * factor;
        return new Matrix2D(res, false, false);
    }

    public Matrix2D rref() {
        int rows = _rows, cols = _cols, lead = 0;
        double[][] res = new double[rows][];
        for (int i = 0; i < rows; ++i)
            res[i] = Arrays.copyOf(_matrix[i], cols);

        for (int r = 0; r < rows; ++r, ++lead) {
            if (cols <= lead) break;
            int i = r;
            while (res[i][lead] == 0) {
                if (++i == rows) {
                    i = r;
                    if (++lead == cols) {
                        --lead;
                        break;
                    }
                }
            }

            double[] temp = res[r];
            res[r] = res[i];
            res[i] = temp;

            double div = res[r][lead];
            for (int j = 0; j < cols; j++)
                res[r][j] /= div;

            for (int j = 0; j < rows; j++) {
                if (j == r) continue;
                double ratio = res[j][lead];
                for (int k = 0; k < cols; k++)
                    res[j][k] -= (ratio * res[r][k]);
            }
        }
        return new Matrix2D(res, false, false);
    }

    public Matrix2D augment(Matrix2D other) {
        int arc = _rows;
        int acc = _cols;
        int brc = other._rows;
        int bcc = other._cols;
        ensureDimensionsEqual(arc, brc);
        double[][] res = new double[arc][acc + bcc];
        for (int i = 0; i < arc; ++i) {
            System.arraycopy(_matrix[i], 0, res[i], 0, acc);
            System.arraycopy(other._matrix[i], 0, res[i], acc, bcc);
        }
        return new Matrix2D(res, false, false);
    }

    public Matrix2D inverse() {
        int rows = _rows, cols = _cols;
        ensureDimensionsEqual(rows, cols);
        Matrix2D augMat = augment(identity(rows));
        Matrix2D rrefMat = augMat.rref();
        return rrefMat.subMatrix(0, cols);
    }

    public Matrix2D subMatrix(int startRow, int startCol) {
        return subMatrix(startRow, startCol, _rows, _cols);
    }

    public Matrix2D subMatrix(int startRow, int startCol, int endRow, int endCol) {
        double[][] res = new double[endRow - startRow][];
        for (int i = startRow; i < endRow; ++i)
            res[i] = Arrays.copyOfRange(_matrix[i], startCol, endCol);
        return new Matrix2D(res, false, false);
    }

    @Override
    public String toString() {
        return ArrayCombiner.brackets.toString2D(ArrayConverter.toWrapped2D(_matrix));
    }

    @Override
    public int hashCode() {
        if (_hashDirty) {
            _hashCode = Arrays.deepHashCode(_matrix);
            _hashDirty = false;
        }
        return _hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Matrix2D && equals((Matrix2D)obj);
    }

    public boolean equals(Matrix2D other) {
        if (!_hashDirty && !other._hashDirty && _hashCode != other._hashCode) return false;
        int arc = _rows;
        int brc = other._rows;
        if (arc != brc) return false;
        int acc = _cols;
        int bcc = other._cols;
        if (acc != bcc) return false;
        for (int i = 0; i < arc; ++i)
            for (int j = 0; j < acc; ++j)
                if (_matrix[i][j] != other._matrix[i][j])
                    return false;
        return true;
    }

    public static Matrix2D identity(int size) {
        ensureDimensionPositive(size);
        double[][] res = new double[size][size];
        for (int i = 0; i < size; ++i)
            res[i][i] = 1;
        return new Matrix2D(res, false, false);
    }

    public static Matrix2D zero(int rows, int cols) {
        ensureDimensionsPositive(rows, cols);
        double[][] res = new double[rows][cols];
        return new Matrix2D(res, false, false);
    }

    private static void ensureDimensionPositive(int dim) {
        if (dim <= 0) throw new IllegalArgumentException("Dimension must be positive");
    }

    private static void ensureDimensionsPositive(int... dims) {
        for (int dim : dims)
            ensureDimensionPositive(dim);
    }

    private static void ensureDimensionsEqual(int a, int b) {
        if (a != b) throw new IllegalArgumentException("Dimension error");
    }
}
