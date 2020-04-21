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
    private final Map<Object, Integer> popularKeys;
    private final Map<Object, Integer> popularValues;

    public PopularMap() {
        this.map = new HashMap<>();
        this.popularKeys = new HashMap<>();
        this.popularValues = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.popularKeys = new HashMap<>();
        this.popularValues = new HashMap<>();
    }

    private void updatePopularity(Object object, Map<Object, Integer> popularityMap) {
        popularityMap.put(object, popularityMap.getOrDefault(object, 0) + 1);
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
        updatePopularity(key, popularKeys);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatePopularity(value, popularValues);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updatePopularity(key, popularKeys);
        V value = map.get(key);
        if (value != null) {
            updatePopularity(value, popularValues);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        updatePopularity(key, popularKeys);
        updatePopularity(value, popularValues);
        V checkValue = map.put(key, value);
        if (checkValue != null) {
            updatePopularity(checkValue, popularValues);
        }
        return checkValue;
    }

    @Override
    public V remove(Object key) {
        updatePopularity(key, popularKeys);
        V checkValue = map.remove(key);
        if (checkValue != null) {
            updatePopularity(checkValue, popularValues);
        }
        return checkValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        popularValues.clear();
        popularKeys.clear();
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


    private Object getPopularityObject(Map<Object, Integer> poplarObjects) {
        int max = -1;
        Object object = null;
        for (Map.Entry<Object, Integer> entry : poplarObjects.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                object = entry.getKey();
            }
        }
        return object;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return (K) getPopularityObject(popularKeys);
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
        return (V) getPopularityObject(popularValues);
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
     */
    public Iterator<V> popularIterator() {
        List<V> valuesArray = new ArrayList<>();
        for (Map.Entry<Object, Integer> entry : popularValues.entrySet()) {
            valuesArray.add((V)entry.getKey());
        }
        valuesArray.sort(Comparator.comparing(popularValues::get));
        return valuesArray.iterator();
    }
}
