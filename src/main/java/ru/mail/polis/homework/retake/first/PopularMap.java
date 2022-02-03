package ru.mail.polis.homework.retake.first;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 * <p>
 * Так же надо сделать итератор (подробности ниже).
 * <p>
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 * <p>
 * Помните, что по мапе тоже можно итерироваться
 * <p>
 * for (Map.Entry<K, V> entry : map.entrySet()) {
 * entry.getKey();
 * entry.getValue();
 * }
 * <p>
 * Всего 10 баллов
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPop = new HashMap<>();
    private final Map<V, Integer> valuePop = new HashMap<>();
    private List<V> sortedValues;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (keyPop.containsKey(key)) {
            int count = keyPop.get(key);
            keyPop.replace((K) key, ++count);
        }

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, V> set : map.entrySet()) {
            if (set.getValue().equals(value)) {
                V key2 = set.getValue();

                int count2 = valuePop.get(key2);
                valuePop.replace(key2, ++count2);
            }
        }
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V result = map.get(key);
        keyPop.merge((K) key, 1, (oldVal, newVal) -> oldVal + newVal);
        if (result != null) {
            valuePop.merge(result, 1, (oldVal, newVal) -> oldVal + newVal);
        }
        return result;

    }

    @Override
    public V put(K key, V value) {
        V result = map.put(key, value);
        keyPop.merge(key, 1, (oldVal, newVal) -> oldVal + newVal);
        if (value != null) {
            valuePop.merge(value, 1, (oldVal, newVal) -> oldVal + newVal);
        }
        if (result != null) {
            valuePop.merge(result, 1, (oldVal, newVal) -> oldVal + newVal);
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        V result = this.map.remove(key);
        keyPop.merge((K) key, 1, (oldVal, newVal) -> oldVal + newVal);
        if (result != null) {
            valuePop.merge(result, 1, (oldVal, newVal) -> oldVal + newVal);
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return new HashSet<>(map.keySet());
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        K mostPopularKey = null;
        int count = -1;
        for (Map.Entry<K, Integer> entry : keyPop.entrySet()) {
            if (entry.getValue() > (count)) {
                count = entry.getValue();
                mostPopularKey = entry.getKey();
            }
        }
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPop.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int count = -1;
        V popularValue = null;
        for (Map.Entry<V, Integer> entry : this.valuePop.entrySet()) {
            if (entry.getValue() > count) {
                popularValue = entry.getKey();
                count = entry.getValue();
            }
        }
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePop.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return new PopularIterator();
    }

    private class PopularIterator implements Iterator<V> {

        private int current;

        PopularIterator() {
            current = 0;
            sortedValues = valuePop.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(Entry::getKey)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean hasNext() {
            return current < sortedValues.size();
        }

        @Override
        public V next() {
            return sortedValues.get(current++);
        }
    }
}
