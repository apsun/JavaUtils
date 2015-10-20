package com.crossbowffs.javautils.collections;

import java.util.Arrays;
import java.util.EmptyStackException;

public class BitStack {
    private byte[] _bytes;
    private int _bitIndex;

    public BitStack() {
        this(16);
    }

    public BitStack(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Initial capacity cannot be negative");
        _bytes = new byte[(initialCapacity + 7) / 8];
        _bitIndex = 0;
    }

    public void push(boolean bit) {
        if (_bitIndex / 8 == _bytes.length) {
            _bytes = Arrays.copyOf(_bytes, _bytes.length * 2);
        }
        if (bit) {
            _bytes[_bitIndex / 8] |= (1 << (_bitIndex % 8));
        }
        ++_bitIndex;
    }

    public boolean peek() {
        if (_bitIndex == 0)
            throw new EmptyStackException();
        return (_bytes[_bitIndex / 8] & (1 << (_bitIndex % 8))) != 0;
    }

    public boolean pop() {
        if (_bitIndex == 0)
            throw new EmptyStackException();
        boolean bit = peek();
        _bytes[_bitIndex / 8] &= ~(1 << (_bitIndex % 8));
        --_bitIndex;
        return bit;
    }

    public int bitSize() {
        return _bitIndex;
    }

    public int byteSize() {
        return (_bitIndex + 7) / 8;
    }

    public boolean isEmpty() {
        return _bitIndex == 0;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(_bytes, (_bitIndex + 7) / 8);
    }

    public boolean[] toBitArray() {
        boolean[] bits = new boolean[_bitIndex];
        for (int i = 0; i < _bytes.length; ++i) {
            byte b = _bytes[i];
            for (int n = 0; n < 8; ++n) {
                int index = i * 8 + n;
                if (index >= bits.length) return bits;
                bits[index] = (b & (1 << n)) != 0;
            }
        }
        return bits;
    }
}
