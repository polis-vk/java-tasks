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

    private final Map<K, V> map;
    private final Map<Object, Integer> keyPopularity = new HashMap<>();
    private final Map<Object, Integer> valuePopularity = new HashMap<>();
    private K topKey = null;
    private V topValue = null;

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
    }

    private void increasePopularity(Object key, Map<Object, Integer> map) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    private void increaseKeyPopularity(Object key) {
        increasePopularity(key, keyPopularity);

        if (topKey == null) {
            topKey = (K) key;
        }
        if (keyPopularity.get(key) > keyPopularity.get(topKey)) {
            topKey = (K) key;
        }
    }

    private void increaseValuePopularity(Object value) {
        increasePopularity(value, valuePopularity);

        if (topValue == null) {
            topValue = (V) value;
        }
        if (valuePopularity.get(value) > valuePopularity.get(topValue)) {
            topValue = (V) value;
        }
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
        increaseKeyPopularity(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValuePopularity(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V mapValue = map.get(key);

        increaseKeyPopularity(key);
        if (mapValue != null) {
            increaseValuePopularity(mapValue);
        }
        return mapValue;
    }

    @Override
    public V put(K key, V value) {
        increaseKeyPopularity(key);
        increaseValuePopularity(value);

        if (map.containsKey(key)) {
            increaseValuePopularity(map.get(key));
        }
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        increaseKeyPopularity(key);
        V removedValue = map.remove(key);

        if (removedValue != null) {
            increaseValuePopularity(removedValue);
        }
        return removedValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            increaseKeyPopularity(k);
        }
        for (V v : m.values()) {
            increaseValuePopularity(v);
        }
        map.putAll(m);
    }

    @Override
    public void clear() {
        topValue = null;
        topKey = null;
        keyPopularity.clear();
        valuePopularity.clear();
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = map.keySet();

        for (K k : set) {
            increaseKeyPopularity(k);
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = map.values();

        for (V v : collection) {
            increaseValuePopularity(v);
        }
        return collection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = map.entrySet();
        for (Entry<K, V> entry : set) {
            increaseKeyPopularity(entry.getKey());
            increaseValuePopularity(entry.getValue());
        }
        return set;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return topKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keyPopularity.containsKey(key)) {
            return keyPopularity.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return topValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (valuePopularity.containsKey(value)) {
            return valuePopularity.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        ArrayList<V> iterArray = new ArrayList<>();

        for (Map.Entry<Object, Integer> entry : valuePopularity.entrySet()) {
            iterArray.add((V) entry.getKey());
        }
        iterArray.sort(Comparator.comparing(valuePopularity::get));
        return iterArray.iterator();
    }
}
