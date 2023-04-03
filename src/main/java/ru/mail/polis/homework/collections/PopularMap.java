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
    private final Map<K, Integer> KeyPopularity;
    private final Map<V, Integer> ValuePopularity;

    public PopularMap() {
        this.map = new HashMap<>();
        this.KeyPopularity = new HashMap<>();
        this.ValuePopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.KeyPopularity = new HashMap<>();
        this.ValuePopularity = new HashMap<>();
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
        boolean isContains = map.containsKey(key);
        increaseKeyPopularity((K) key);
        return isContains;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean isContains = map.containsValue(value);
        increaseValuePopularity((V) value);
        return isContains;
    }

    @Override
    public V get(Object key) {
        V getValue = map.get(key);
        increaseValuePopularity(getValue);
        increaseKeyPopularity((K) key);
        return getValue;
    }

    @Override
    public V put(K key, V value) {
        V putValue = map.put(key, value);
        increaseValuePopularity(putValue);
        increaseValuePopularity(value);
        increaseKeyPopularity(key);
        return putValue;
    }

    @Override
    public V remove(Object key) {
        V removeValue = map.remove(key);
        increaseValuePopularity(removeValue);
        increaseKeyPopularity((K) key);
        return removeValue;
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
        List<K> list = new ArrayList<>(KeyPopularity.keySet());
        list.sort(this::compareKeys);
        return list.get(list.size() - 1);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (KeyPopularity.containsKey(key)) {
            return KeyPopularity.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        List<V> list = new ArrayList<>(ValuePopularity.keySet());
        list.sort(this::compare);
        return list.get(list.size() - 1);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (ValuePopularity.containsKey(value)) {
            return ValuePopularity.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> list = new ArrayList<>(ValuePopularity.keySet());
        list.sort(this::compare);
        return list.iterator();
    }

    private int compare(V v1, V v2) {
        return Integer.compare(getValuePopularity(v1), getValuePopularity(v2));
    }

    private int compareKeys(K k1, K k2) {
        return Integer.compare(getKeyPopularity(k1), getKeyPopularity(k2));
    }

    private void increaseKeyPopularity(K key) {
        if (KeyPopularity.containsKey(key)) {
            KeyPopularity.put(key, KeyPopularity.get(key) + 1);
        } else {
            KeyPopularity.put(key, 1);
        }
    }

    private void increaseValuePopularity(V value) {
        if (ValuePopularity.containsKey(value)) {
            ValuePopularity.put(value, ValuePopularity.get(value) + 1);
        } else if (value != null) {
            ValuePopularity.put(value, 1);
        }
    }
}
