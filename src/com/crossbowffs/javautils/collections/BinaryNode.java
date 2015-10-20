package com.crossbowffs.javautils.collections;

public class BinaryNode<T> {
    private final T _value;
    private BinaryNode<T> _left;
    private BinaryNode<T> _right;
    private BinaryNode<T> _parent;

    public BinaryNode(T value) {
        _value = value;
    }

    public T value() {
        return _value;
    }

    public void setLeft(BinaryNode<T> child) {
        _left = child;
        child._parent = this;
    }

    public void setRight(BinaryNode<T> child) {
        _right = child;
        child._parent = this;
    }

    public BinaryNode<T> parent() {
        return _parent;
    }

    public BinaryNode<T> root() {
        if (_parent == null) return this;
        return _parent.root();
    }

    public BinaryNode<T> left() {
        return _left;
    }

    public BinaryNode<T> right() {
        return _right;
    }

    @Override
    public String toString() {
        return _value.toString();
    }
}
