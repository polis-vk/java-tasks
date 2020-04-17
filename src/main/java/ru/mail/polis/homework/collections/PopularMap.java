package ru.mail.polis.homework.collections;


import javafx.util.Pair;

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
    private Map<K, V> map;
    private Map<K, Integer> keyPopularity;
    private Map<V, Integer> valuePopularity;

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
        increaseKeyPop(keyPopularity,key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePop(valuePopularity, value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increaseKeyPop(keyPopularity, key);
        V value = map.get(key);
        increaseValuePop(valuePopularity, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increaseKeyPop(keyPopularity, key);
        if(map.containsKey(key)){
            increaseValuePop(valuePopularity, map.get(key));
        }
        increaseValuePop(valuePopularity,value);
        return map.put(key,value);
    }

    @Override
    public V remove(Object key) {
        increaseKeyPop(keyPopularity, key);
        increaseValuePop(valuePopularity, map.get(key));
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
        for (Object key : m.keySet()) {
            increaseKeyPop(keyPopularity, key);
        }
        for (Object value : m.values()) {
            increaseValuePop(valuePopularity, value);
        }
    }

    @Override
    public void clear() {
        map.clear();
        keyPopularity.clear();
        valuePopularity.clear();
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

    private void increaseKeyPop(Map<K, Integer> map, Object key) {
        if (map.containsKey(key)) {
            map.replace((K)key, map.get(key) + 1);
        } else {
            map.put((K)key, 1);
        }
    }
    private void increaseValuePop(Map<V, Integer> map, Object key) {
        if (map.containsKey(key)) {
            map.replace((V)key, map.get(key) + 1);
        } else {
            map.put((V)key, 1);
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        int maxPopularity = 0;
        K popularKey = null;
        for (Map.Entry<K, Integer> entry : keyPopularity.entrySet()) {
            if(entry.getValue()>maxPopularity){
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
        if(keyPopularity.containsKey(key)) {
            return keyPopularity.get(key);
        } else {
            return 0;
        }
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int maxPopularity = 0;
        V popularValue = null;
        for (Map.Entry<V, Integer> entry : valuePopularity.entrySet()) {
            if(entry.getValue()>maxPopularity){
                maxPopularity = entry.getValue();
                popularValue = entry.getKey();
            }
        }
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if(valuePopularity.containsKey(value)) {
            return valuePopularity.get(value);
        } else {
            return 0;
        }
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
