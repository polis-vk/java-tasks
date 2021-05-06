package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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

    private final Map<K, V> map;
    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;
    private K mostPopularKey;
    private V mostPopularValue;


    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    private void incKeysPopularity(K key) {
        int tempPopularity = keysPopularity.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
        if (mostPopularKey == null || tempPopularity > keysPopularity.get(mostPopularKey)) {
            mostPopularKey = key;
        }
    }

    private void incValuesPopularity(V value) {
        int tempPopularity = valuesPopularity.compute(value, (k, v) -> (v == null) ? 1 : v + 1);
        if (mostPopularValue == null || tempPopularity > valuesPopularity.get(mostPopularValue)) {
            mostPopularValue = value;
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
        incKeysPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incValuesPopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        incKeysPopularity((K) key);
        V value = map.get(key);
        incValuesPopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        incKeysPopularity((K) key);
        incValuesPopularity((V) value);
        V previousValue = map.put(key, value);
        if (previousValue != null) {
            incValuesPopularity((V) previousValue);
        }
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        incKeysPopularity((K) key);
        V value = map.remove(key);
        if (value != null) {
            incValuesPopularity(value);
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
        K mostPopularKey = null;
        Integer mostPopular = 0;
        for (Entry<K, Integer> entry : keysPopularity.entrySet()) {
            if (entry.getValue() > mostPopular) {
                mostPopularKey = entry.getKey();
                mostPopular = entry.getValue();
            }
        }
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        V mostPopularValue = null;
        Integer mostPopular = 0;
        for (Entry<V, Integer> entry : valuesPopularity.entrySet()) {
            if (entry.getValue() > mostPopular) {
                mostPopularValue = entry.getKey();
                mostPopular = entry.getValue();
            }
        }
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> sortedValuesPopularity = new ArrayList(valuesPopularity.keySet());
        sortedValuesPopularity.sort(Comparator.comparing(valuesPopularity::get));
        return sortedValuesPopularity.iterator();
    }
}
