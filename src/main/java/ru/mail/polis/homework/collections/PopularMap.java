package ru.mail.polis.homework.collections;


import java.util.*;


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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap = new HashMap<>();
    private Pair<K, Integer> mostPopularKey;
    public final Map<V, Integer> valuePopularityMap = new HashMap<>();
    private Pair<V, Integer> mostPopularValue;

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
        mostPopularKey = increaseKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        mostPopularValue = increaseValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V result = map.get(key);
        mostPopularKey = increaseKeyPopularity(key);
        mostPopularValue = increaseValuePopularity(result);
        return result;
    }

    @Override
    public V put(K key, V value) {
        V tempVal = map.get(key);
        mostPopularKey = increaseKeyPopularity(key);
        if (tempVal != null) {
            mostPopularValue = increaseValuePopularity(tempVal);
        }
        mostPopularValue = increaseValuePopularity(value);
        map.put(key, value);
        return tempVal;
    }

    @Override
    public V remove(Object key) {
        V result = map.remove(key);
        mostPopularKey = increaseKeyPopularity(key);
        if (result != null) {
            mostPopularValue = increaseValuePopularity(result);
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
        return mostPopularKey.getFirst().orElse(null);
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значений может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue.getFirst().orElse(null);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если старое
     * значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным) 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopularityMap.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }

    private Pair<K, Integer> increaseKeyPopularity(Object key) {
        return MapValueIncreaser.mapIncrease((K) key, keyPopularityMap, mostPopularKey);
    }

    private Pair<V, Integer> increaseValuePopularity(Object value) {
        return MapValueIncreaser.mapIncrease((V) value, valuePopularityMap, mostPopularValue);
    }
}
