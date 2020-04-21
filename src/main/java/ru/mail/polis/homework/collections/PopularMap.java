package ru.mail.polis.homework.collections;


import javafx.util.Pair;

import java.util.*;

import static javafx.scene.input.KeyCode.T;


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
    private final Map<V, Integer> valuePopularity;

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
        increasePop(keyPopularity, key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePop(valuePopularity, value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increasePop(keyPopularity, key);
        V value = map.get(key);
        if(value!=null)
        increasePop(valuePopularity, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePop(keyPopularity, key);
        increasePop(valuePopularity, value);
        V oldValue = map.put(key, value);
        if(oldValue !=null)
        increasePop(valuePopularity, oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        increasePop(keyPopularity, key);
        V value = map.remove(key);
        increasePop(valuePopularity, value);
        return map.remove(value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
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

    private <T> void increasePop(Map<T,Integer> map, Object key){
        Integer pop = map.putIfAbsent((T)key, 1);
        if(pop!=null){
            map.put((T)key,pop+1);
        }
        /* Старая версия
        if (map.containsKey(key)) {
            map.replace((T) key, map.get(key) + 1);
        } else {
            map.put((T) key, 1);
        }
        */
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return getPopular(keyPopularity);
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
            return keyPopularity.getOrDefault(key,0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getPopular(valuePopularity);
    }

    public <T> T getPopular(Map<T,Integer> map){
        int maxPopularity = 0;
        T popularValue = null;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxPopularity) {
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
        return valuePopularity.getOrDefault(value,0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return null;
    }
}
