package ru.mail.polis.homework.collections;

import java.util.*;

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

    private K topKey;
    private V topValue;
    private Map<Object, MyInt> keyAmount;
    private Map<Object, MyInt> valueAmount;
    private final Map<K, V> map;

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        ArrayList<V> values = new ArrayList<>();
        for (Object value : valueAmount.keySet()) {
            values.add((V) value);
        }
        values.sort((Comparator.comparing(valueAmount::get)));
        return values.iterator();
    }

    private class MyInt implements Comparable<MyInt> { //потому что почему бы и нет?)
        private int value = 1;

        public int getValue() {
            return value;
        }

        public void intIncrement() {
            value++;
        }

        @Override
        public int compareTo(MyInt myInt) {
            if (myInt.getValue() < value) {
                return 1;
            } else if (myInt.getValue() > value) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private int add(Object o, Map<Object, MyInt> map) {
        if (map.containsKey(o)) {
            map.get(o).intIncrement();
        } else {
            map.put(o, new MyInt());
        }
        return map.get(o).getValue();
    }

    private void addValue(Object value) {
        if (value == null) {
            return;
        }
        if (topValue == null) {
            topValue = (V) value;
        }
        if (add(value, valueAmount) > valueAmount.get(topValue).getValue()) {
            topValue = (V) value;
        }
    }

    private void addKey(Object key) {
        if (key == null) {
            return;
        }
        if (topKey == null) {
            topKey = (K) key;
        }
        if (add(key, keyAmount) > keyAmount.get(topKey).getValue()) {
            topKey = (K) key;
        }
    }

    public PopularMap() {
        this.map = new HashMap<>();
        keyAmount = new HashMap<>();
        valueAmount = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyAmount = new HashMap<>();
        valueAmount = new HashMap<>();
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
        addKey(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        addValue(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        addValue(value);
        addKey(key);
        return value;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            addValue(map.get(key));
        }
        addValue(value);
        addKey(key);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        addKey(key);
        V value = map.remove(key);
        addValue(value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        //throw new UnsupportedOperationException("putAll");
        for (Object key : m.keySet()) {
            addKey(key);
        }
        for (Object value : m.values()) {
            addValue(value);
        }
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyAmount.clear();
        valueAmount.clear();
        topKey = null;
        topValue = null;
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
        return topKey;

    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return topValue;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keyAmount.containsKey(key)) {
            return keyAmount.get(key).getValue();
        }
        return 0;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (valueAmount.containsKey(value)) {
            return valueAmount.get(value).getValue();
        }
        return 0;

    }
}
