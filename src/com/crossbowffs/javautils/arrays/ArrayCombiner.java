package com.crossbowffs.javautils.arrays;

public class ArrayCombiner {
    public static final ArrayCombiner braces = new ArrayCombiner("{ ", " }");
    public static final ArrayCombiner brackets = new ArrayCombiner("[ ", " ]");

    private final String _delimiter;
    private final String _initializer;
    private final String _terminator;

    public ArrayCombiner() {
        this(", ");
    }

    public ArrayCombiner(String delimiter) {
        this(delimiter, "[ ", " ]");
    }

    public ArrayCombiner(String initializer, String terminator) {
        this(", ", initializer, terminator);
    }

    public ArrayCombiner(String delimiter, String initializer, String terminator) {
        _delimiter = delimiter;
        _initializer = initializer;
        _terminator = terminator;
    }

    public <T> String toString1D(T[] array) {
        return toString1D(array, false);
    }

    public <T> String toString1D(T[] array, boolean padColumn) {
        int maxlen = 0;
        if (padColumn) {
            for (T item : array) {
                int len = item.toString().length();
                if (len > maxlen) maxlen = len;
            }
        }

        StringBuilder sb = new StringBuilder(_initializer);
        for (int i = 0; i < array.length; ++i) {
            if (i > 0) sb.append(_delimiter);
            if (!padColumn) {
                sb.append(array[i]);
            } else {
                sb.append(pad(array[i].toString(), maxlen));
            }
        }
        sb.append(_terminator);
        return sb.toString();
    }

    public <T> String toString2D(T[][] array) {
        return toString2D(array, false, true);
    }

    public <T> String toString2D(T[][] array, boolean padColumn, boolean padMissing) {
        int columnCount = 0;
        for (T[] subarray : array) {
            int len = subarray.length;
            if (len > columnCount) columnCount = len;
        }

        StringBuilder sb = new StringBuilder();
        String newLine = System.lineSeparator();

        if (padColumn) {
            int padding = getMaxColumnLength(array);
            for (T[] row : array) {
                appendConstRow(sb, row, padding, columnCount, padMissing);
                sb.append(newLine);
            }
        } else {
            int[] paddings = getVarColumnLengths(array, columnCount);
            for (T[] row : array) {
                appendVarRow(sb, row, paddings, columnCount, padMissing);
                sb.append(newLine);
            }
        }

        return sb.substring(0, sb.length() - newLine.length());
    }

    private <T> void appendConstRow(StringBuilder sb, T[] row, int columnPadding, int columnCount, boolean padMissing) {
        sb.append(_initializer);
        for (int i = 0; i < columnCount; ++i) {
            if (i >= row.length) {
                if (!padMissing) break;
                sb.append(pad("", columnPadding + _delimiter.length()));
            } else {
                if (i > 0) sb.append(_delimiter);
                sb.append(pad(row[i].toString(), columnPadding));
            }
        }
        sb.append(_terminator);
    }

    private <T> void appendVarRow(StringBuilder sb, T[] row, int[] columnPaddings, int columnCount, boolean padMissing) {
        sb.append(_initializer);
        for (int i = 0; i < columnCount; ++i) {
            if (i >= row.length) {
                if (!padMissing) break;
                sb.append(pad("", columnPaddings[i] + _delimiter.length()));
            } else {
                if (i > 0) sb.append(_delimiter);
                sb.append(pad(row[i].toString(), columnPaddings[i]));
            }
        }
        sb.append(_terminator);
    }

    private static <T> int getMaxColumnLength(T[][] array) {
        int columnWidth = 0;
        for (T[] row : array) {
            for (T item : row) {
                int itemlen = item.toString().length();
                if (itemlen > columnWidth) columnWidth = itemlen;
            }
        }
        return columnWidth;
    }

    private static <T> int[] getVarColumnLengths(T[][] array, int columnCount) {
        int[] columnWidths = new int[columnCount];
        for (int i = 0; i < columnCount; ++i) {
            for (T[] row : array) {
                if (i >= row.length) continue;
                int itemlen = row[i].toString().length();
                if (itemlen > columnWidths[i]) columnWidths[i] = itemlen;
            }
        }
        return columnWidths;
    }

    private static String pad(String str, int size) {
        StringBuilder sb = new StringBuilder(size);
        int numSpaces = size - str.length();
        for (int i = 0; i < numSpaces; ++i)
            sb.append(' ');
        sb.append(str);
        return sb.toString();
    }
}
