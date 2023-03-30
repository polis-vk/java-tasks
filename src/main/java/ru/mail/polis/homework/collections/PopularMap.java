package ru.mail.polis.homework.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 * <p>
 * Так же надо сделать итератор (подробности ниже).
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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;
    private K mostPopularKey = null;
    private V mostPopularValue = null;

    public PopularMap() {
        this.map = new HashMap<>();
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keysPopularity = new HashMap<>();
        valuesPopularity = new HashMap<>();
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
        addKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        addKeyPopularity((K) key);

        V value = map.get(key);
        if (value != null) {
            addValuePopularity(value);
        }

        return value;
    }

    @Override
    public V put(K key, V value) {
        addKeyPopularity(key);
        addValuePopularity(value);

        V prevValue = map.put(key, value);
        if (prevValue != null) {
            addValuePopularity(prevValue);
        }
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        addKeyPopularity((K) key);

        V prevValue = map.remove(key);
        if (prevValue != null) {
            addValuePopularity(prevValue);
        }
        return prevValue;
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
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> sortedValues = new ArrayList<>(valuesPopularity.keySet());
        sortedValues.sort((a, b) -> getValuePopularity(a) - getValuePopularity(b));
        return sortedValues.iterator();
    }

    private void addValuePopularity(V value) {
        if (!valuesPopularity.containsKey(value)) {
            valuesPopularity.put(value, 0);
        }
        valuesPopularity.compute(value, (k, v) -> (v == null) ? 1 : v + 1);
        updateMostPopularValue(value);
    }

    private void addKeyPopularity(K key) {
        if (!keysPopularity.containsKey(key)) {
            keysPopularity.put(key, 0);
        }
        keysPopularity.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
        updateMostPopularKey(key);
    }

    private void updateMostPopularValue(V value) {
        int popularity = getValuePopularity(value);
        if (popularity > getValuePopularity(mostPopularValue)) {
            mostPopularValue = value;
        }
    }

    private void updateMostPopularKey(K key) {
        int popularity = getKeyPopularity(key);
        if (popularity > getKeyPopularity(mostPopularKey)) {
            mostPopularKey = key;
        }
    }
}
