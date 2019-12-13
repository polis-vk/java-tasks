package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


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
    private final Map<K, Integer> keyPopularityMap = new HashMap<>();
    private final Map<V, Integer> valuePopularityMap = new HashMap<>();


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
        incKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        incValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        incKeyPopularity((K) key);
        V value = map.get(key);
        incValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        incKeyPopularity(key);
        incValuePopularity(value);
        V oldValue = map.put(key, value);
        incValuePopularity(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        incKeyPopularity((K) key);
        V removedValue = map.remove(key);
        incValuePopularity(removedValue);
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
        return keyPopularityMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        Integer keyPopularity = keyPopularityMap.get(key);
        return keyPopularity == null ? 0 : keyPopularity;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return valuePopularityMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        Integer valuePopularity = valuePopularityMap.get(value);
        return valuePopularity == null ? 0 : valuePopularity;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valuePopularityMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .iterator();
    }

    private void incKeyPopularity(K key) {
        if (key == null) {
            return;
        }

        if (keyPopularityMap.containsKey(key)) {
            keyPopularityMap.put(key, keyPopularityMap.get(key) + 1);
        } else {
            keyPopularityMap.put(key, 1);
        }
    }

    private void incValuePopularity(V value) {
        if (value == null) {
            return;
        }

        if (valuePopularityMap.containsKey(value)) {
            valuePopularityMap.put(value, valuePopularityMap.get(value) + 1);
        } else {
            valuePopularityMap.put(value, 1);
        }
    }
}