package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны возвращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
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
    private final Map<K, Integer> keys;
    private final Map<V, Integer> values;

    public PopularMap() {
        this.map = new HashMap<>();
        keys = new HashMap<>();
        values = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keys = new HashMap<>();
        values = new HashMap<>();
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
        increasePopularityKey((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularityValue((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        if (value != null) increasePopularityValue(value);
        increasePopularityKey((K) key);
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) increasePopularityValue(map.get(key));
        increasePopularityKey(key);
        increasePopularityValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V value = map.remove(key);
        increasePopularityKey((K) key);
        if (value != null) increasePopularityValue(value);
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
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return values
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

    private void increasePopularityKey(K key) {
        if (keys.containsKey(key)) {
            keys.put(key, keys.get(key) + 1);
        } else {
            keys.put(key, 1);
        }
    }

    private void increasePopularityValue(V value) {
        if (values.containsKey(value)) {
            values.put(value, values.get(value) + 1);
        } else {
            values.put(value, 1);
        }
    }
}