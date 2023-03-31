package ru.mail.polis.homework.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
    private final Map<K, Integer> popularKeys;
    private final Map<V, Integer> popularValues;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKeys = new HashMap<K, Integer>();
        this.popularValues = new HashMap<V, Integer>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKeys = new HashMap<K, Integer>();
        this.popularValues = new HashMap<V, Integer>();
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
        increaseKeyPopularity((K) key);
        return map.containsKey(key);


    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increaseKeyPopularity((K) key);
        increaseValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V prevValue = map.put(key, value);
        increaseKeyPopularity((K) key);
        increaseValuePopularity(prevValue);
        increaseValuePopularity(value);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        increaseValuePopularity(map.get(key));
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

        this.map.putAll(m);
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
        K popularKey = null;
        int popularity = 0;
        for (Map.Entry<K, Integer> entry : popularKeys.entrySet()) {
            if (entry.getValue() > popularity) {
                popularity = entry.getValue();
                popularKey = entry.getKey();
            }
        }
        return popularKey;
    }

    private void increaseValuePopularity(V value) {
        if (value == null) {
            return;
        }
        if (popularValues.containsKey(value)) {
            popularValues.put((V) value, popularValues.get(value) + 1);
        } else {
            popularValues.put((V) value, 1);
        }
    }

    private void increaseKeyPopularity(K key) {
        if (popularKeys.containsKey(key)) {
            popularKeys.put((K) key, popularKeys.get(key) + 1);
        } else {
            popularKeys.put((K) key, 1);
        }
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {

        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        V popularValue = null;
        int popularity = 0;
        for (Map.Entry<V, Integer> entry : popularValues.entrySet()) {
            if (entry.getValue() > popularity) {
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

        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        ArrayList<V> values = new ArrayList<>(popularValues.size());
        for (Map.Entry<V, Integer> entry : popularValues.entrySet()) {
            values.add(entry.getKey());
        }
        return values.iterator();
    }
}
