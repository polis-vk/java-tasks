package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove.
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
    private Map<Object, Integer> popularKeyMap;
    private Map<Object, Integer> popularValueMap;

    public PopularMap() {
        this.map = new HashMap<>();
        popularKeyMap = new HashMap<>();
        popularValueMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        popularKeyMap = new HashMap<>();
        popularValueMap = new HashMap<>();
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
        calculatePopularity(key, popularKeyMap);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        calculatePopularity(value, popularValueMap);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        calculatePopularity(key, popularKeyMap);
        calculatePopularity(value, popularValueMap);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = map.put(key, value);
        if (oldValue != null && oldValue.equals(value)) {
            calculatePopularity(value, popularValueMap);
            calculatePopularity(value, popularValueMap);
        } else {
            calculatePopularity(value, popularValueMap);
        }
        calculatePopularity(key, popularKeyMap);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        calculatePopularity(key, popularKeyMap);
        calculatePopularity(map.get(key), popularValueMap);
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

    public void calculatePopularity(Object key, Map<Object, Integer> mapPopularity) {
        if (key == null) return;
        if (mapPopularity.containsKey(key)) {
            mapPopularity.put(key, mapPopularity.remove(key) + 1);
        } else {
            mapPopularity.put(key, 1);
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        if (!popularKeyMap.isEmpty()) {
            int maxValue = 0;
            Object key = null;

            for (Entry<Object, Integer> entry : popularKeyMap.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    key = entry.getKey();
                }
            }
            return (K) key;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            return entry.getKey();
        }
        return null;
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKeyMap.isEmpty() && popularKeyMap.containsKey(key)) {
            return popularKeyMap.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        if (!popularValueMap.isEmpty()) {
            int maxValue = 0;
            Object value = null;

            for (Entry<Object, Integer> entry : popularValueMap.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    value = entry.getKey();
                }
            }
            return (V) value;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            return entry.getValue();
        }
        return null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValueMap.isEmpty() && popularValueMap.containsKey(value)) {
            return popularValueMap.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>();
        for (Object value : popularValueMap.keySet()) {
            values.add((V) value);
        }
        //Здаем компаратору сравнение по методу get в мапе
        values.sort(Comparator.comparingInt(popularValueMap::get));
        return values.iterator();
    }
}
