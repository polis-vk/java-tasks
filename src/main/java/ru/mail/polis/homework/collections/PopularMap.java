package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.Comparator;
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
        increaseKeyPopularity( (K) key);
        if (map.containsKey(key)) {
            increaseValuePopularity(map.get(key));
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increaseKeyPopularity((K) key);
        if (map.containsKey(key)) {
            increaseValuePopularity(map.get(key));
        }
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        //increase old value
        if (map.containsKey(key)) {
            increaseValuePopularity(map.get(key));
        }
        // increase new value
        increaseValuePopularity(value);
        increaseKeyPopularity(key);

        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        if (map.containsKey(key)) {
            increaseValuePopularity(map.get(key));
        }

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
        int popularity = 0;
        K mostPopularKey = null;

        for (Map.Entry<K, Integer> entry : keyPopularityMap.entrySet()) {
            if (entry.getValue() > popularity) {
                popularity = entry.getValue();
                mostPopularKey = entry.getKey();
            }
        }

        return mostPopularKey;
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
        int popularity = 0;
        V mostPopularValue = null;
        for (Entry<V, Integer> entry : valuePopularityMap.entrySet()) {
            if (entry.getValue() >= popularity) {
                popularity = entry.getValue();
                mostPopularValue = entry.getKey();
            }
        }
        return mostPopularValue;
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
        return valuePopularityMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Entry::getValue))
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }


    private void increaseKeyPopularity(K key) {
        keyPopularityMap.put(key, keyPopularityMap.get(key) == null ? 1 : keyPopularityMap.get(key) + 1);
    }


    private void increaseValuePopularity(V value) {
        valuePopularityMap.put(value, valuePopularityMap.get(value) == null ? 1 : valuePopularityMap.get(value) + 1);
    }
}
