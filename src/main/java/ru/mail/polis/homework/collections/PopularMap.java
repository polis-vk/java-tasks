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
 *
 * modified by БорискинМА
 * 03.12.19
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keys = new HashMap<>();
    private final Map<V, Integer> values = new HashMap<>();


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
        encourageKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        encourageValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        V value = map.get(key);
        if (value != null) {
            encourageValue(value);
        }
        encourageKey((K) key);
        return value;
    }

    private void encourageKey(K key) {
        if (keys.containsKey(key)) {
            keys.put(key, keys.get(key) + 1);
        } else {
            keys.put(key, 1);
        }
    }

    private void encourageValue(V value) {
        if (values.containsKey(value)) {
            values.put(value, values.get(value) + 1);
        } else {
            values.put(value, 1);
        }
    }

    @Override
    public V put(K key, V value) {
        encourageKey(key);
        encourageValue(value);
        V prevValue = map.put(key, value);
        if (prevValue != null) {
            encourageValue(map.get(key));
        }
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        encourageKey((K) key);
        if (value != null) {
            encourageValue(value);
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
        return keys
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
        return keys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значений может быть более одного
     */
    public V getPopularValue() {
        return values
                .entrySet()
                .stream()
                .max(Comparator.comparing(Entry::getValue))
                .map(Entry::getKey).orElse(null);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return values.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return values
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }
}
