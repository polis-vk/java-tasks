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

    private final Map<K, V> map;
    private final Map<Object, Integer> keyPopularity;
    private final Map<Object, Integer> valuePopularity;
    private Object mostPopularKey;
    private Object mostPopularValue;


    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularity = new HashMap<>();
        this.valuePopularity = new HashMap<>();
    }

    private int updatePopularity(Map<Object, Integer> mapPopularity, Object parametr) {
        mapPopularity.put(parametr, mapPopularity.getOrDefault(parametr, 0) + 1);
        return mapPopularity.get(parametr);
    }

    private void updateKeyPopularity(Object parametr) {
        if (keyPopularity.getOrDefault(mostPopularKey, 0) < updatePopularity(keyPopularity, parametr)) {
            mostPopularKey = parametr;
        }
    }

    private void updateValuePopularity(Object parametr) {
        if (valuePopularity.getOrDefault(mostPopularValue, 0) < updatePopularity(valuePopularity, parametr)) {
            mostPopularValue = parametr;
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
        updateKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKeyPopularity(key);
        V value = map.get(key);
        updateValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (map.get(key) != null) {
            updateValuePopularity(map.get(key));
        }
        updateKeyPopularity(key);
        updateValuePopularity(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updateKeyPopularity(key);
        if (map.containsKey(key)) {
            updateValuePopularity(map.get(key));
        }
        return map.remove(key);
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
        Set<K> keys = map.keySet();
        for (K key : keys) {
            updateKeyPopularity(key);
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> valueCollection = map.values();
        for (V value : valueCollection) {
            updateValuePopularity(value);
        }
        return valueCollection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = map.entrySet();
        for (Entry<K, V> setEntry : set) {
            updateValuePopularity(setEntry.getValue());
            updateKeyPopularity(setEntry.getKey());
        }
        return set;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return (K) mostPopularKey;
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
        return (V) mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>();
        for (Object value : valuePopularity.keySet()) {
            values.add((V) value);
        }
        values.sort((Comparator.comparing(valuePopularity::get)));
        return values.iterator();
    }
}
