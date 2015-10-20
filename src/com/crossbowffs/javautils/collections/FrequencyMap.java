package com.crossbowffs.javautils.collections;

import java.util.*;

public class FrequencyMap<TKey> {

    private class MutableCounter {
        public int Value;
    }

    private HashMap<TKey, MutableCounter> _countMap;

    public FrequencyMap() {
        _countMap = new HashMap<TKey, MutableCounter>();
    }

    public void increment(TKey value) {
        MutableCounter count = _countMap.get(value);
        if (count == null) _countMap.put(value, count = new MutableCounter());
        ++count.Value;
    }

    public int size() {
        return _countMap.size();
    }

    public int getCount(TKey value) {
        MutableCounter count = _countMap.get(value);
        if (count != null) return count.Value;
        return 0;
    }

    public Set<TKey> keys() {
        return _countMap.keySet();
    }

    public Set<Map.Entry<TKey, Integer>> items() {
        HashMap<TKey, Integer> map = new HashMap<TKey, Integer>();
        for (Map.Entry<TKey, MutableCounter> srcPairs : _countMap.entrySet()) {
            map.put(srcPairs.getKey(), srcPairs.getValue().Value);
        }
        return map.entrySet();
    }
}
