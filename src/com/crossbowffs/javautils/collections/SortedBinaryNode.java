package com.crossbowffs.javautils.collections;

public class SortedBinaryNode<T extends Comparable<? super T>> {
    private final T _value;
    private SortedBinaryNode<T> _left;
    private SortedBinaryNode<T> _right;
    private SortedBinaryNode<T> _parent;

    public SortedBinaryNode(T value) {
        this(value, null);
    }

    private SortedBinaryNode(T value, SortedBinaryNode<T> parent) {
        _value = value;
        _parent = parent;
    }

    public SortedBinaryNode<T> add(SortedBinaryNode<T> item) {
        item.ensureNotInTree();
        return addSkipCheck(item);
    }

    public SortedBinaryNode<T> add(T item) {
        return addSkipCheck(new SortedBinaryNode<T>(item));
    }

    private SortedBinaryNode<T> addSkipCheck(SortedBinaryNode<T> item) {
        if (item._value.compareTo(_value) <= 0) {
            if (_left == null) {
                _left = item;
                item._parent = this;
                return item;
            } else {
                return _left.addSkipCheck(item);
            }
        } else {
            if (_right == null) {
                _right = item;
                item._parent = this;
                return item;
            } else {
                return _right.addSkipCheck(item);
            }
        }
    }

    public boolean remove() {
        if (_parent == null) return false;
        if (_parent._left == this) _parent._left = null;
        if (_parent._right == this) _parent._right = null;
        _parent = null;
        return true;
    }

    public boolean contains(SortedBinaryNode<T> node) {
        if (node == this) return true;
        int cmp = node._value.compareTo(_value);
        if (cmp < 0 && _left != null) return _left.contains(node);
        if (cmp > 0 && _right != null) return _right.contains(node);
        return false;
    }

    public boolean contains(T item) {
        return find(item) != null;
    }

    public SortedBinaryNode<T> find(T item) {
        int cmp = item.compareTo(_value);
        if (cmp == 0) return this;
        if (cmp < 0 && _left != null) return _left.find(item);
        if (cmp > 0 && _right != null) return _right.find(item);
        return null;
    }

    public T value() {
        return _value;
    }

    public SortedBinaryNode<T> parent() {
        return _parent;
    }

    public SortedBinaryNode<T> root() {
        if (_parent == null) return this;
        return _parent.root();
    }

    public SortedBinaryNode<T> left() {
        return _left;
    }

    public SortedBinaryNode<T> right() {
        return _right;
    }

    public SortedBinaryNode<T> first() {
        if (_left == null) return this;
        return _left.first();
    }

    public SortedBinaryNode<T> last() {
        if (_right == null) return this;
        return _right.last();
    }

    public SortedBinaryNode<T> previous() {
        if (_left != null) return _left.last();
        SortedBinaryNode<T> n = this;
        while (n._parent != null && n == n._parent._left)
            n = n._parent;
        return n._parent;
    }

    public SortedBinaryNode<T> next() {
        if (_right != null) return _right.first();
        SortedBinaryNode<T> n = this;
        while (n._parent != null && n == n._parent._right)
            n = n._parent;
        return n._parent;
    }

    private void ensureNotInTree() {
        if (_parent != null)
            throw new IllegalArgumentException("Node is already in a binary tree");
    }

    @Override
    public String toString() {
        return _value.toString();
    }
}
