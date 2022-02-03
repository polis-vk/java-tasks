package ru.mail.polis.homework.retake.first;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
 *
 * Важный момент, вам не надо реализовывать мапу, вы должны использовать композицию.
 * Вы можете использовать любые коллекции, которые есть в java.
 *
 * Помните, что по мапе тоже можно итерироваться
 *
 *         for (Map.Entry<K, V> entry : map.entrySet()) {
 *             entry.getKey();
 *             entry.getValue();
 *         }
 *
 * Всего 10 баллов
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<K, Integer> popularKeys = new HashMap<>();
    private final Map<V, Integer> popularValues = new HashMap<>();

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
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        updatePopularKey((K) key);
        if (value != null) {
            updatePopularValue(value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        V v = map.put(key, value);
        updatePopular(key, value);
        if (v != null) {
            updatePopularValue(v);
        }
        return v;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        updatePopularKey((K) key);
        if (value != null) {
            updatePopularValue(value);
        }
        return value;
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
        return map.keySet();
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
        Map.Entry<K, Integer> maxEntry = null;
        for (Map.Entry<K, Integer> entry : popularKeys.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry != null ? maxEntry.getKey() : null;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        Map.Entry<V, Integer> maxEntry = null;
        for (Map.Entry<V, Integer> entry : popularValues.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry != null ? maxEntry.getKey() : null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return new PopularMapIterator();
    }

    private class PopularMapIterator implements Iterator<V> {

        private int position = 0;
        private final ArrayList<V> popularValueArraySorted = new ArrayList<>();

        public PopularMapIterator() {
            popularValues.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> popularValueArraySorted.add(entry.getKey()));
        }

        @Override
        public boolean hasNext() {
            return position < popularValueArraySorted.size();
        }

        @Override
        public V next() {
            return popularValueArraySorted.get(position++);
        }
    }

    private void updatePopularKey(K key) {
        if (popularKeys.containsKey(key)) {
            popularKeys.put(key, popularKeys.get(key) + 1);
        } else {
            popularKeys.put(key, 1);
        }
    }

    private void updatePopularValue(V value) {
        if (popularValues.containsKey(value)) {
            popularValues.put(value, popularValues.get(value) + 1);
        } else {
            popularValues.put(value, 1);
        }
    }

    private void updatePopular(K key, V value) {
        updatePopularKey(key);
        updatePopularValue(value);
    }
}
