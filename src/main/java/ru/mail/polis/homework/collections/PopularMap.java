package ru.mail.polis.homework.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
    private final Map<K, Integer> usedKeyMap;
    private final Map<V, Integer> usedValueMap;

    public PopularMap() {
        this.map = new HashMap<>();
        usedKeyMap = new HashMap<>();
        usedValueMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        usedKeyMap = new HashMap<>();
        usedValueMap = new HashMap<>();
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
        updateKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKey((K) key);
        return updateValue(map.get(key));
    }

    @Override
    public V put(K key, V value) {
        V previousValue = map.put(key, value);
        if (previousValue != null) {
            updateValue(previousValue);
        }
        updateKey(key);
        updateValue(value);
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        updateKey((K) key);
        V removedValue = map.remove(key);
        if (removedValue != null && usedValueMap.containsKey(removedValue)) {
            updateValue(removedValue);
        }
        return removedValue;
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
        return usedKeyMap
            .entrySet()
            .stream()
            .max(Entry.comparingByValue())
            .get()
            .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return usedKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return usedValueMap
            .entrySet()
            .stream()
            .max(Entry.comparingByValue())
            .get()
            .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return usedValueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return usedValueMap
            .entrySet()
            .stream()
            .sorted(Entry.comparingByValue())
            .map(Entry::getKey)
            .collect(Collectors.toList())
            .iterator();
    }

    private K updateKey(K key) {
        if (usedKeyMap.containsKey(key)) {
            usedKeyMap.put(key, usedKeyMap.get(key) + 1);
        } else {
            usedKeyMap.put(key, 1);
        }
        return key;
    }

    private V updateValue(V value) {
        if (usedValueMap.containsKey(value)) {
            usedValueMap.put(value, usedValueMap.get(value) + 1);
        } else {
            usedValueMap.put(value, 1);
        }
        return value;
    }
}
