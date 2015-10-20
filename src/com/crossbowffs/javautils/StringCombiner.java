package com.crossbowffs.javautils;

public class StringCombiner {

    public interface IterableBehavior<T> {
        public String getValue(T iterItem);
    }

    private final StringBuilder _builder;
    private final String _delimiter;
    private final String _terminator;
    private String _nullString = "(null)";
    private boolean _addedValue;

    public StringCombiner() {
        this(", ");
    }

    public StringCombiner(String delimiter) {
        this(delimiter, null, null);
    }

    public StringCombiner(String delimiter, String initializer, String terminator) {
        if (delimiter == null)
            throw new IllegalArgumentException("Delimiter cannot be null");
        _builder = new StringBuilder(initializer == null ? "" : initializer);
        _delimiter = delimiter;
        _terminator = terminator == null ? "" : terminator;
    }

    public StringCombiner setNullString(String value) {
        if (value == null)
            throw new IllegalArgumentException("Default value string cannot be null");
        if (_addedValue)
            throw new IllegalStateException("Cannot change null string after adding items");
        _nullString = value;
        return this;
    }

    public StringCombiner append(String value) {
        if (value == null)
            value = _nullString;
        _builder.append(value);
        _builder.append(_delimiter);
        _addedValue = true;
        return this;
    }

    public StringCombiner append(Object value) {
        if (value == null)
            return append(null);
        return append(value.toString());
    }

    public <T> StringCombiner appendIterable(Iterable<T> iterable) {
        for (T item : iterable)
            append(item);
        return this;
    }

    public <T> StringCombiner appendIterable(Iterable<T> iterable, IterableBehavior<T> behavior) {
        for (T item : iterable)
            append(behavior.getValue(item));
        return this;
    }

    @Override
    public String toString() {
        if (!_addedValue)
            return _builder + _terminator;
        return _builder.substring(0, _builder.length() - _delimiter.length()) + _terminator;
    }
}
