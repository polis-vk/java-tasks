package ru.mail.polis.homework.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularity;
    private final Map<V, Integer> valuePopularity;
    private K mostPopularKey;
    private V mostPopularValue;

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
        V result = map.get(key);
        increaseKeyPopularity((K) key);
        increaseValuePopularity(result);
        return result;
    }

    @Override
    public V put(K key, V value) {
        V result = map.put(key, value);
        increaseKeyPopularity(key);
        increaseValuePopularity(value);
        increaseValuePopularity(result);
        return result;
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity((K) key);
        V result = map.remove(key);
        increaseValuePopularity(result);
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        K key;
        V value;
        map.putAll(m);
        for (Map.Entry<? extends K, ? extends V> x : m.entrySet()) {
            key = x.getKey();
            value = x.getValue();
            increaseKeyPopularity(key);
            increaseValuePopularity(value);
        }
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
        return valuePopularity.keySet()
                .stream()
                .sorted((first, second) -> valuePopularity.get(first) - valuePopularity.get(second))
                .iterator();
    }

    private void increaseKeyPopularity(K key) {
        int popularity = 1;
        if (key == null) {
            return;
        }

        if (keyPopularity.containsKey(key)) {
            popularity = keyPopularity.get(key) + 1;
            keyPopularity.put(key, popularity);
        } else {
            keyPopularity.put(key, popularity);
        }
        if (mostPopularKey == null || popularity > keyPopularity.get(mostPopularKey)) {
            mostPopularKey = key;
        }
    }

    private void increaseValuePopularity(V value) {
        int popularity = 1;
        if (value == null) {
            return;
        }

        if (valuePopularity.containsKey(value)) {
            popularity = valuePopularity.get(value) + 1;
            valuePopularity.put(value, popularity);
        } else {
            valuePopularity.put(value, popularity);
        }
        if (mostPopularValue == null || popularity > valuePopularity.get(mostPopularValue)) {
            mostPopularValue = value;
        }
    }

}
