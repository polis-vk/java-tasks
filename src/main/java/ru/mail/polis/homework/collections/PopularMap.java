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
    private Map<K, Integer> mapK;
    private Map<V, Integer> mapV;

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

    private void updatePopularKey(K key) {
        if (mapK.containsKey(key)) {
            int temp = mapK.get(key);
            temp++;
            mapK.put(key, temp);

        } else {
            mapK.put(key, 1);
        }
    }

    private void updatePopularValue(V value) {
        if (mapV.containsKey(value)) {
            int temp = mapV.get(value);
            temp++;
            mapV.put(value, temp);
        } else {
            mapV.put(value, 1);
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
        updatePopularKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        //updatePopularValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updatePopularKey((K) key);
        V value = map.get((key));
        updatePopularValue(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            updatePopularValue(map.get(key));
        }
        updatePopularKey((K) key);
        updatePopularValue((V) value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updatePopularKey((K) key);
        updatePopularValue(map.get(key));
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        //throw new UnsupportedOperationException("putAll");
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
        int max = -1;
        K maxKey = null;
        for (Map.Entry<K, Integer> entry : mapK.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (mapK.containsKey(key)) {
            return mapK.get(key);
        }
        return 0;

    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int max = -1;
        V maxValue = null;
        for (Map.Entry<V, Integer> entry : mapV.entrySet()) {
            if (entry.getValue()>max) {
                max = entry.getValue();
                maxValue = entry.getKey();
            }
        }
        return maxValue;

    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (mapV.containsKey(value)) {
            return mapV.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
