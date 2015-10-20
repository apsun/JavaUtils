package com.crossbowffs.javautils.huffman;

import java.util.Arrays;
import java.util.List;

public class Variabyte {
    private final byte[] _bytes;
    private final int _bitLength;

    public Variabyte(byte[] bytes, int bitLength) {
        if ((bitLength + 7) / 8 != bytes.length)
            throw new IllegalArgumentException("Byte/bit size mismatch");
        _bytes = bytes;
        _bitLength = bitLength;
    }

    public Variabyte(boolean[] bits) {
        _bitLength = bits.length;
        _bytes = new byte[(bits.length + 7) / 8];
        for (int i = 0; i < _bytes.length; ++i) {
            for (int n = 0; n < 8; ++n) {
                int index = i * 8 + n;
                if (index >= bits.length) return;
                if (bits[index]) {
                    _bytes[i] |= (1 << n);
                }
            }
        }
    }

    public int bitLength() {
        return _bitLength;
    }

    public boolean getBit(int index) {
        if (index >= _bitLength)
            throw new IndexOutOfBoundsException();
        byte b = _bytes[index / 8];
        int bitIndex = index % 8;
        return (b & (1 << bitIndex)) != 0;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(_bytes, _bytes.length);
    }

    public boolean[] toBitArray() {
        boolean[] bits = new boolean[_bytes.length];
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

    public static Variabyte merge(List<Variabyte> values) {
        int bitLength = 0;
        for (Variabyte variabyte : values) {
            bitLength += variabyte.bitLength();
        }
        byte[] bytes = new byte[(bitLength + 7) / 8];
        int bitIndex = 0;
        for (Variabyte variabyte : values) {
            for (int i = 0; i < variabyte.bitLength(); ++i) {
                if (variabyte.getBit(i)) {
                    bytes[bitIndex / 8] |= (byte)(1 << (bitIndex % 8));
                }
                ++bitIndex;
            }
        }
        assert bitIndex == bitLength;
        return new Variabyte(bytes, bitIndex);
    }
}
