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
    private final Map<K, Integer> keyMap;
    private final Map<V, Integer> valueMap;

    public PopularMap() {
        this.map = new HashMap<>();
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyMap = new HashMap<>();
        valueMap = new HashMap<>();
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
        if(key == null){
            return false;
        }

        updateKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if(value == null){
            return false;
        }
        updateValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if(key == null){
            return null;
        }

        updateKey((K) key);
        V tempV = map.get(key);
        if (tempV != null) {
            updateValue(tempV);
        }
        return tempV;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            updateValue(map.get(key));
        }
        updateKey(key);
        updateValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if(key == null){
            return null;
        }

        updateKey((K) key);
        V removedValue = map.remove(key);
        if (removedValue != null) {
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
        return keyMap
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
        return keyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return valueMap
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
        return valueMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valueMap
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }

    private void updateKey(K key) {
        if (keyMap.containsKey(key)) {
            keyMap.put(key, keyMap.get(key) + 1);
        } else {
            keyMap.put(key, 1);
        }
    }

    private void updateValue(V value) {
        if (valueMap.containsKey(value)) {
            valueMap.put(value, valueMap.get(value) + 1);
        } else {
            valueMap.put(value, 1);
        }
    }
}