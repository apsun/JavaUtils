package com.crossbowffs.javautils.huffman;

public class ByteWeight implements Comparable<ByteWeight> {
    private final byte _value;
    private final double _weight;
    private final boolean _isLeaf;

    public ByteWeight(byte value, double weight) {
        _value = value;
        _weight = weight;
        _isLeaf = true;
    }

    public ByteWeight(ByteWeight low, ByteWeight high) {
        _value = 0;
        _weight = low._weight + high._weight;
        _isLeaf = false;
    }

    public byte value() {
        if (!_isLeaf) throw new IllegalStateException("Cannot get value of non-leaf node");
        return _value;
    }

    public double weight() {
        if (!_isLeaf) throw new IllegalStateException("Cannot get weight of non-leaf node");
        return _weight;
    }

    public boolean isLeaf() {
        return _isLeaf;
    }

    @Override
    public int compareTo(ByteWeight o) {
        return Double.compare(_weight, o._weight);
    }

    @Override
    public String toString() {
        return String.format("0x%02x ('%s')", _value, (char)_value);
    }
}