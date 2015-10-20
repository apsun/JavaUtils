package com.crossbowffs.javautils;

import java.math.BigInteger;
import java.util.Iterator;

public class FibonacciGenerator implements Iterable<BigInteger> {

    private class FibonacciIterator implements Iterator<BigInteger> {
        private BigInteger _num1;
        private BigInteger _num2;

        public FibonacciIterator() {
            this(BigInteger.ZERO, BigInteger.ONE);
        }

        public FibonacciIterator(BigInteger seed1, BigInteger seed2) {
            _num1 = seed1;
            _num2 = seed2;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public BigInteger next() {
            BigInteger temp = _num1;
            _num1 = _num2;
            _num2 = _num2.add(temp);
            return temp;
        }
    }

    @Override
    public Iterator<BigInteger> iterator() {
        return new FibonacciIterator();
    }

    public Iterator<BigInteger> iterator(BigInteger seed1, BigInteger seed2) {
        return new FibonacciIterator(seed1, seed2);
    }

    public static void main(String[] args) {
        FibonacciGenerator fibonacciGenerator = new FibonacciGenerator();
        for (BigInteger x : fibonacciGenerator) {
            System.out.println(x);
        }
    }
}

