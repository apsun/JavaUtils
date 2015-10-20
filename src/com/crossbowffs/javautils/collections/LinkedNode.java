package com.crossbowffs.javautils.collections;

public class LinkedNode<T> {
    private final T _value;
    private LinkedNode<T> _prev;
    private LinkedNode<T> _next;

    public LinkedNode(T value) {
        _value = value;
    }

    public T value() {
        return _value;
    }

    public LinkedNode<T> previous() {
        return _prev;
    }

    public LinkedNode<T> next() {
        return _next;
    }

    public LinkedNode<T> first() {
        if (_prev != null)
            return _prev.first();
        return this;
    }

    public LinkedNode<T> last() {
        if (_next != null)
            return _next.last();
        return this;
    }

    public void remove() {
        if (_prev != null)
            _prev._next = _next;
        if (_next != null)
            _next._prev = _prev;
        _prev = null;
        _next = null;
    }

    public void insertAfter(LinkedNode<T> node) {
        node.ensureNotInList();
        node._prev = this;
        if (_next != null) {
            node._next = _next;
            _next._prev = node;
        }
        _next = node;
    }

    public void insertBefore(LinkedNode<T> node) {
        node.ensureNotInList();
        node._next = this;
        if (_prev != null) {
            node._prev = _prev;
            _prev._next = node;
        }
        _prev = node;
    }

    public void insertAtEnd(LinkedNode<T> node) {
        node.ensureNotInList();
        LinkedNode<T> last = last();
        last._next = node;
        node._prev = last;
    }

    public void insertAtBeginning(LinkedNode<T> node) {
        node.ensureNotInList();
        LinkedNode<T> first = first();
        first._prev = node;
        node._next = first;
    }

    private void ensureNotInList() {
        if (_next != null || _prev != null) {
            throw new IllegalArgumentException("Node is already in a linked list");
        }
    }

    @Override
    public String toString() {
        return _value.toString();
    }
}