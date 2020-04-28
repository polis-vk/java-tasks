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
    private final Map<K, Integer> mapK;
    private final Map<V, Integer> mapV;

    private final Map<K, V> map;

    public PopularMap() {
        this.map = new HashMap<>();
        this.mapK = new HashMap<>();
        this.mapV = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.mapK = new HashMap<>();
        this.mapV = new HashMap<>();
    }

    private <T> void updatePopular(Map<T, Integer> map, Object key) {
        if (key != null) {
            if (map.containsKey(key)) {
                map.replace((T) key, map.get(key) + 1);
            } else {
                map.put((T) key, 1);
            }
        }
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
        updatePopular(mapK, key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatePopular(mapV, value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updatePopular(mapK, key);
        V value = map.get((key));
        updatePopular(mapV, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            updatePopular(mapV, map.get(key));
        }
        updatePopular(mapK, key);
        updatePopular(mapV, value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updatePopular(mapK, key);
        V value = map.remove(key);
        updatePopular(mapV, value);
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

    private <T> T getMax(Map<T, Integer> map) {
        int max = -1;
        T maxKey = null;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return getMax(mapK);
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return mapK.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getMax(mapV);

    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return mapV.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
