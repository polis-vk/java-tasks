package ru.mail.polis.homework.collections;


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
    private final Map<K, Integer> keyPopularity;
    private final Map<V, Integer> valuePopularity;
    private K mostPopularKey;
    private V mostPopularValue;
    private int mostPopularKeyPopularity;
    private int mostPopularValuePopularity;

    public PopularMap() {
        this(new HashMap<>());
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyPopularity = new HashMap<>();
        valuePopularity = new HashMap<>();
        mostPopularKey = null;
        mostPopularValue = null;
        mostPopularValuePopularity = 0;
        mostPopularKeyPopularity = 0;
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
        V value = map.get(key);
        incKeyPopularity((K) key);
        incValuePopularity(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        incKeyPopularity(key);
        incValuePopularity(value);
        V prevValue = map.put(key, value);
        incValuePopularity(prevValue);
        return prevValue;
    }

    @Override
    public V remove(Object key) {
        V removingValue = map.remove(key);
        incKeyPopularity((K) key);
        incValuePopularity(removingValue);
        return removingValue;
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
        return keyPopularity.getOrDefault(key, 0);
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
        return valuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuePopularity.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .iterator();
    }


    private <T> int incPopularity(T key, Map<T, Integer> mapOfPopularity) {
        int newPopularity = mapOfPopularity.getOrDefault(key, 0) + 1;
        mapOfPopularity.put(key, newPopularity);
        return newPopularity;
    }

    private void incKeyPopularity(K key) {
        if (key == null) {
            return;
        }
        int newPopularity = incPopularity(key, keyPopularity);
        if (newPopularity > mostPopularKeyPopularity) {
            mostPopularKeyPopularity = newPopularity;
            mostPopularKey = key;
        }
    }

    private void incValuePopularity(V value) {
        if (value == null) {
            return;
        }
        int newPopularity = incPopularity(value, valuePopularity);
        if (newPopularity > mostPopularValuePopularity) {
            mostPopularValuePopularity = newPopularity;
            mostPopularValue = value;
        }
    }
}
