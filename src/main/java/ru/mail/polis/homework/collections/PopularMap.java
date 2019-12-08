package ru.mail.polis.homework.collections;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 *
 * Дополнительное задание описано будет ниже
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private Map<K, Integer> popularityKeyMap = new HashMap<>();
    private Map<V, Integer> popularityValueMap = new HashMap<>();

    private void addKey(K key) {
        if (key == null) {
            return;
        }
        if (popularityKeyMap.containsKey(key)) {
            popularityKeyMap.put(key, popularityKeyMap.get(key) + 1);
        } else {
            popularityKeyMap.put(key, 1);
        }
    }

    private void addValue(V value) {
        if (value == null) {
            return;
        }
        if (popularityValueMap.containsKey(value)) {
            popularityValueMap.put(value, popularityValueMap.get(value) + 1);
        } else {
            popularityValueMap.put(value, 1);
        }
    }

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
        addKey((K)key);
        return map.containsKey((K)key);
    }

    @Override
    public boolean containsValue(Object value) {
        addValue((V) value);
        return map.containsValue((V)value);
    }

    @Override
    public V get(Object key) {
        addKey((K)key);

        V value = map.get((K) key);
        addValue(value);
        return value;
    }

    @Override
    public V put(K key, V value) {

        addKey(key);
        addValue(value);

        V valueForPut = map.put(key, value);
        addValue(valueForPut);

        return valueForPut;
    }

    @Override
    public V remove(Object key) {
        addKey((K)key);
        V removed = map.remove((K) key);
        addValue(removed);
        return removed;
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
        return popularityKeyMap.entrySet().stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularityKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularityValueMap.entrySet().stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularityValueMap.getOrDefault(value, 0);
    }


    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return popularityValueMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}
