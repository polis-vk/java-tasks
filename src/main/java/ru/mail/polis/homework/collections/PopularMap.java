package ru.mail.polis.homework.collections;

import java.util.*;

/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 * <p>
 * Дополнительное задание описано будет ниже
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {
    private final Map<K, Integer> keyAmount;
    private final Map<V, Integer> valueAmount;
    private final Map<K, V> map;
    private K topKey;
    private V topValue;

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<V>(valueAmount.keySet());
        values.sort(Comparator.comparing(valueAmount::get));
        return values.iterator();
    }

    private <T> int addPopular(T keyOrValue, Map<T, Integer> myMap) {
        return myMap.compute(keyOrValue, (k, v) -> v == null ? 1 : v + 1);
    }

    private void addValuePopular(V value) {
        if (value != null && valueAmount.getOrDefault(topValue, 0) < addPopular(value, valueAmount)) {
            topValue = value;
        }
    }

    private void addKeyPopular(K key) {
        if (key != null && keyAmount.getOrDefault(topKey, 0) < addPopular(key, keyAmount)) {
            topKey = key;
        }
    }

    public PopularMap() {
        this.map = new HashMap<>();
        keyAmount = new HashMap<>();
        valueAmount = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyAmount = new HashMap<>();
        valueAmount = new HashMap<>();
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
        addKeyPopular((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addValuePopular((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        addValuePopular(value);
        addKeyPopular((K) key);
        return value;
    }

    @Override
    public V put(K key, V value) {
        addValuePopular(value);
        addKeyPopular(key);
        V tmpValue = map.put(key, value);
        addValuePopular(tmpValue);
        return tmpValue;
    }

    @Override
    public V remove(Object key) {
        addKeyPopular((K) key);
        V value = map.remove(key);
        addValuePopular(value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyAmount.clear();
        valueAmount.clear();
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
        return topKey;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return topValue;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyAmount.getOrDefault(key, 0);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valueAmount.getOrDefault(value, 0);
    }
}
