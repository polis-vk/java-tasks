package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * Задание оценивается в 2 балла.
 * Одна из самых популярных задач.
 * Наша структура хранит обрывки слов. Надо реализовать метод positionPartString
 * который вернет: позиции, где находятся подстроки, из которых можно составить
 * переданное слово. Так же известно, что слова, которые писались в структуру, изначально
 * делились пополам для записи в нее.
 * Отрабатывать метод должен за О(n).
 */
public class SearchInTheShredderList {
    private final List<String> partStrings;
    private final Trie prefixes = new Trie(SearchInTheShredderList::range);
    private final Trie suffixes = new Trie(SearchInTheShredderList::rangeBackward);

    public SearchInTheShredderList() {
        this.partStrings = new ArrayList<>();
    }

    public SearchInTheShredderList(List<String> partStrings) {
        this.partStrings = partStrings;
        int index = 0;
        for (String part : partStrings) {
            prefixes.put(part, index);
            suffixes.put(part, index);
            index++;
        }
    }

    public void add(String value) {
        final int index = partStrings.size();
        prefixes.put(value, index);
        suffixes.put(value, index);
        partStrings.add(value);
    }

    public String get(int index) {
        return partStrings.get(index);
    }

    /**
     * Ищем позиции подстрок из которых можно составить передаваемое слово
     *
     * @param value - передаваемое слово
     * @return - либо массив с реальными позициями подстрок если нашли, либо null
     */
    public int[] positionPartString(String value) {
        if (value == null) {
            return null;
        }
        Integer[] prefixIndexes = new Integer[value.length() + 1];
        Integer[] suffixIndexes = new Integer[prefixIndexes.length];
        int i = 0;
        for (Iterator<Integer> it = prefixes.get(value); it.hasNext(); ) {
            prefixIndexes[i++] = it.next();
        }
        i = suffixIndexes.length;
        for (Iterator<Integer> it = suffixes.get(value); it.hasNext(); ) {
            suffixIndexes[--i] = it.next();
        }
        for (int j = 0; j < prefixIndexes.length; j++) {
            if (prefixIndexes[j] != null && suffixIndexes[j] != null) {
                return new int[]{prefixIndexes[j], suffixIndexes[j]};
            }
        }
        return null;
    }

    private static PrimitiveIterator.OfInt range(int to) {
        return IntStream.range(0, to).iterator();
    }

    private static PrimitiveIterator.OfInt rangeBackward(int to) {
        return IntStream.iterate(to - 1, i -> i - 1).limit(to).iterator();
    }

    private final static class Trie {
        private final Node root = new Node();
        private final IntFunction<PrimitiveIterator.OfInt> range;

        public Trie(IntFunction<PrimitiveIterator.OfInt> range) {
            this.range = range;
        }

        public void put(String keys, int value) {
            put(keys.toCharArray(), value);
        }

        public Iterator<Integer> get(String keys) {
            return new ValueIterator(keys.toCharArray());
        }

        private void put(char[] keys, int value) {
            Node node = root;
            for (final PrimitiveIterator.OfInt indexes = range.apply(keys.length); indexes.hasNext(); ) {
                node = node.map.computeIfAbsent(keys[indexes.next()], c -> new Node());
            }
            node.value = value;
        }

        private static class Node {
            private Integer value;
            private final Map<Character, Node> map = new HashMap<>();
        }

        private class ValueIterator implements Iterator<Integer> {
            private Node node = root;
            private final char[] keys;
            private final PrimitiveIterator.OfInt indexes;

            private ValueIterator(char[] keys) {
                this.keys = keys;
                indexes = range.apply(keys.length);
            }

            @Override
            public boolean hasNext() {
                return indexes.hasNext() && node != null;
            }

            @Override
            public Integer next() {
                Integer value = node.value;
                node = node.map.get(keys[indexes.next()]);
                return value;
            }
        }
    }
}
