package ru.mail.polis.homework.collections;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    private final Map<K, Integer> popularityMap;
    private final Map<K, V> map;

    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        popularityMap = new HashMap<>();
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
        increaseCounter((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increaseCounter((K) key);
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        increaseCounter(key);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increaseCounter((K) key);
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

         return popularityMap
                 .entrySet()
                 .stream()
                 .sorted(new Comparator<Entry<K, Integer>>() {
                     @Override
                     public int compare(Entry<K, Integer> kIntegerEntry, Entry<K, Integer> t1) {
                         return kIntegerEntry.getValue() - t1.getValue();
                     }
                 })
                 .collect(Collectors.toList())
                 .get(0)
                 .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularityMap.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        K key = getPopularKey();
        return map.get(key);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        Set<K> l = new HashSet<>();
        map.forEach((k, v) -> {
            if (v == value) l.add(k);
        });

        final int[] sum = {0};
        l.forEach(k -> sum[0] += popularityMap.get(k));

        return sum[0];
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        throw new NotImplementedException();
    }



    private void increaseCounter(K key) {
        Integer i = popularityMap.get(key);
        if (i != null) {
            popularityMap.put(key, ++i);
        } else {
            popularityMap.put(key, 1);
        }
    }
}
