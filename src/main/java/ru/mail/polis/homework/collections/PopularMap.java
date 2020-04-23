package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
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
    private final Map<K, Integer> keyPopularityMap;
    private final Map<V, Integer> valuePopularityMap;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularityMap = new HashMap<>();
        this.valuePopularityMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularityMap = new HashMap<>();
        this.valuePopularityMap = new HashMap<>();
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
        increasePopularityMap((K) key, keyPopularityMap);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularityMap((V) value, valuePopularityMap);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increaseAllPopularityMap((K) key, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increaseAllPopularityMap(key, value);
        V valueBefore = map.put(key, value);

        if (valueBefore != null) {
            increasePopularityMap(valueBefore, valuePopularityMap);
        }
        return valueBefore;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        increasePopularityMap((K) key, keyPopularityMap);

        if (value != null) {
            increasePopularityMap(value, valuePopularityMap);
        }
        return map.remove(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyPopularityMap.clear();
        valuePopularityMap.clear();
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
        return (K) getPopularity(keyPopularityMap);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return (V) getPopularity(valuePopularityMap);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> list = valuePopularityMap
            .keySet()
            .stream()
            .sorted(Comparator.comparing(valuePopularityMap::get))
            .collect(Collectors.toList());
        return list.iterator();
    }

    private <T> void increasePopularityMap(T key, Map<T, Integer> popularityMap) {
        popularityMap.put(key, popularityMap.getOrDefault(key, 0) + 1);
    }

    private <T> void increaseAllPopularityMap(K key, V value) {
        increasePopularityMap(key, keyPopularityMap);
        increasePopularityMap(value, valuePopularityMap);
    }

    private <T> T getPopularity(Map<T, Integer> popularityMap) {
        int maxPopularity = 0;
        T value = null;
        for (Map.Entry<T, Integer> entry : popularityMap.entrySet()) {
            if (entry.getValue() >= maxPopularity) {
                maxPopularity = entry.getValue();
                value = entry.getKey();
            }
        }
        return value;
    }
}