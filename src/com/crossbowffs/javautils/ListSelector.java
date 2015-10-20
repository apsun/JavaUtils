package com.crossbowffs.javautils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListSelector {

    public static class AndSelector<T> implements Selector<T> {

        private Selector<T>[] _selectors;

        public AndSelector(Selector<T>... selectors) {
            _selectors = selectors;
        }

        @Override
        public boolean select(T value) {
            for (Selector<T> selector : _selectors) {
                if (!selector.select(value)) return false;
            }

            return true;
        }
    }

    public static class OrSelector<T> implements Selector<T> {

        private Selector<T>[] _selectors;

        public OrSelector(Selector<T>... selectors) {
            _selectors = selectors;
        }

        @Override
        public boolean select(T value) {
            for (Selector<T> selector : _selectors) {
                if (selector.select(value)) return true;
            }

            return false;
        }
    }

    public static class NotSelector<T> implements Selector<T> {

        private Selector<T> _a;

        public NotSelector(Selector<T> a) {
            _a = a;
        }

        @Override
        public boolean select(T value) {
            return !_a.select(value);
        }
    }

    public static interface Selector<T> {

        boolean select(T value);
    }

    public static <T> List<T> selectValue(Selector<T> selector, List<T> array) {
        ArrayList<T> newArray = new ArrayList<T>();
        for (T value : array) {
            if (selector.select(value))
                newArray.add(value);
        }

        return newArray;
    }

    public static <T> List<T> selectIndex(Selector<Integer> selector, List<T> array) {
        ArrayList<T> newArray = new ArrayList<T>();
        for (int i = 0; i < array.size(); ++i) {
            if (selector.select(i))
                newArray.add(array.get(i));
        }

        return newArray;
    }

    public static void main(String[] args) {
        ListSelector.Selector<Integer> divisibleBy4Selector = new ListSelector.Selector<Integer>() {
            @Override
            public boolean select(Integer value) {
                return value % 4 == 0;
            }
        };
        ListSelector.Selector<Integer> divisibleBy100Selector = new ListSelector.Selector<Integer>() {
            @Override
            public boolean select(Integer value) {
                return value % 100 == 0;
            }
        };
        ListSelector.Selector<Integer> divisibleBy400Selector = new ListSelector.Selector<Integer>() {
            @Override
            public boolean select(Integer value) {
                return value % 400 == 0;
            }
        };
        ListSelector.Selector<Integer> evenSelector = new ListSelector.Selector<Integer>() {
            @Override
            public boolean select(Integer value) {
                return value % 2 == 0;
            }
        };

        //divisible by 4 AND (NOT divisible by 100 OR divisible by 400)
        @SuppressWarnings("unchecked")
        ListSelector.Selector<Integer> leapYearSelector =
                new ListSelector.AndSelector<Integer>(divisibleBy4Selector,
                        new ListSelector.OrSelector<Integer>(divisibleBy400Selector,
                                new ListSelector.NotSelector<Integer>(divisibleBy100Selector))
                );

        Integer[] lol = new Integer[300];

        for (int i = 0; i < lol.length; ++i)
            lol[i] = i + 1900;

        for (Integer x : ListSelector.selectValue(leapYearSelector, Arrays.asList(lol)))
            assert (x % 4 == 0 && !(x % 100 == 0 && !(x % 400 == 0)));

        Integer[] meh = new Integer[300];

        for (int i = 0; i < meh.length; ++i)
            meh[i] = i;

        for (Integer x : ListSelector.selectValue(evenSelector, Arrays.asList(meh)))
            assert (x % 2 == 0);
    }
}
