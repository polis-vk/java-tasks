package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


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

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity;
    private final Map<V, Integer> valPopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new HashMap<>();
        this.valPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularity = new HashMap<>();
        this.valPopularity = new HashMap<>();
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
        keyPopularity.put((K) key, keyPopularity.getOrDefault(key, 0) + 1);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valPopularity.put((V) value, valPopularity.getOrDefault(value, 0) + 1);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V val = map.get(key);
        keyPopularity.put((K) key, keyPopularity.getOrDefault(key, 0) + 1);
        if (val != null) {
            valPopularity.put((V) val, valPopularity.getOrDefault(val, 0) + 1);
        }
        return val;
    }

    @Override
    public V put(K key, V value) {
        keyPopularity.put(key, keyPopularity.getOrDefault(key, 0) + 1);
        valPopularity.put(value, valPopularity.getOrDefault(value, 0) + 1);

        V val = map.put(key, value);
        if (val != null) {
            valPopularity.put(val, valPopularity.getOrDefault(val, 0) + 1);
        }
        return val;
    }

    @Override
    public V remove(Object key) {
        keyPopularity.put((K) key, keyPopularity.getOrDefault(key, 0) + 1);
        V val = map.remove(key);
        if (val != null)
            valPopularity.put(val, valPopularity.getOrDefault(val, 0) + 1);
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyPopularity.clear();
        valPopularity.clear();
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
        int maxPopularity = 0;
        K popularKey = null;
        for (Map.Entry<K, Integer> entry : keyPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularity = entry.getValue();
                popularKey = entry.getKey();
            }
        }
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int maxPopularity = 0;
        V popularVal = null;
        for (Map.Entry<V, Integer> entry : valPopularity.entrySet()) {
            if (entry.getValue() > maxPopularity) {
                maxPopularity = entry.getValue();
                popularVal = entry.getKey();
            }
        }
        return popularVal;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valPopularity
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}
