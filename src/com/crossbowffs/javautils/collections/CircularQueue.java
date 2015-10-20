package com.crossbowffs.javautils.collections;

public class CircularQueue<T> {
    private Object[] _buffer;
    private int _headIndex;
    private int _count;

    public CircularQueue(int size) {
        _buffer = new Object[size];
    }

    public void enqueue(T item) {
        if (_count == _buffer.length)
            throw new IllegalStateException("Queue is full");
        _buffer[(_headIndex + _count) % _buffer.length] = item;
        _count++;
    }

    public T peek() {
        if (_count == 0)
            throw new IllegalStateException("Queue is empty");
        @SuppressWarnings("unchecked")
        T item = (T)_buffer[_headIndex];
        return item;
    }

    public T dequeue() {
        if (_count == 0)
            throw new IllegalStateException("Queue is empty");
        @SuppressWarnings("unchecked")
        T item = (T)_buffer[_headIndex];
        _buffer[_headIndex] = null;
        _headIndex = (_headIndex + 1) % _buffer.length;
        _count--;
        return item;
    }
}
