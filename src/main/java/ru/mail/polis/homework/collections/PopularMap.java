package ru.mail.polis.homework.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private final Map<K, Integer> popularityKeyMap;
    private final Map<V, Integer> popularityValueMap;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularityKeyMap = new HashMap<>();
        this.popularityValueMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularityKeyMap = new HashMap<>();
        this.popularityValueMap = new HashMap<>();
    }

    private void updateKeyPopularity(Object key) {
        int priority = popularityKeyMap.get((K) key) == null ? 1 : popularityKeyMap.get((K) key) + 1;
        popularityKeyMap.put((K) key, priority);
    }

    private void updateValuePopularity(Object value) {
        int priority = popularityValueMap.get((V) value) == null ? 1 : popularityValueMap.get((V) value) + 1;
        popularityValueMap.put((V) value, priority);
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
        updateKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKeyPopularity(key);
        V value = map.get(key);
        if (value != null) {
            updateValuePopularity(value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        updateKeyPopularity(key);
        V oldValue = map.get(key);

        if (oldValue != null) {
            updateValuePopularity(oldValue);
        }
        updateValuePopularity(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        updateKeyPopularity(key);
        if (map.get(key) != null) {
            updateValuePopularity(map.get(key));
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
        popularityValueMap.clear();
        popularityKeyMap.clear();
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
        int maxPopularity = -1;
        K popularKey = null;

        for (Map.Entry<K, Integer> entry : popularityKeyMap.entrySet()) {
            if (entry.getValue() > maxPopularity) {
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
        return popularityKeyMap.get(key) == null ? 0 : popularityKeyMap.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        int maxPopularity = -1;
        V popularValue = null;

        for (Map.Entry<V, Integer> entry : popularityValueMap.entrySet()) {
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
        return popularityValueMap.get(value) == null ? 0 : popularityValueMap.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> valuesArray = new ArrayList<>();
        for (Map.Entry<V, Integer> entry : popularityValueMap.entrySet()) {
            valuesArray.add(entry.getKey());
        }
        valuesArray.sort(Comparator.comparing(popularityValueMap::get));
        return valuesArray.iterator();
    }

}
