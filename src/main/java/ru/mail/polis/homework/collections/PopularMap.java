package ru.mail.polis.homework.collections;


import java.util.*;


/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 2 дополнительных метода должны вовзращать самый популярный ключ и его популярность.
 * Популярность - это количество раз, который этот ключ учавствовал в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передю ни в качестве ключа, ни в качестве значения
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
 *
 * Дополнительное задание описано будет ниже
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap;
    private final Map<V, Integer> valuePopularityMap;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyPopularityMap = new HashMap<>();
        this.valuePopularityMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyPopularityMap = new HashMap<>();
        this.valuePopularityMap = new HashMap<>();
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
        refreshPopularity(keyPopularityMap, key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        refreshPopularity(valuePopularityMap, value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        refreshPopularity(keyPopularityMap, key);
        V value = map.get(key);
        if (value != null) {
            refreshPopularity(valuePopularityMap, value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        refreshPopularity(keyPopularityMap, key);
        refreshPopularity(valuePopularityMap, value);
        V oldValue = map.put(key, value);
        if (oldValue != null){
            refreshPopularity(valuePopularityMap, oldValue);
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        refreshPopularity(keyPopularityMap, key);
        V oldValue = map.remove(key);
        if (oldValue != null){
            refreshPopularity(valuePopularityMap, oldValue);
        }
        return oldValue;
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
        return getPopularObj(keyPopularityMap);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getPopularObj(valuePopularityMap);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<V>(valuePopularityMap.keySet());
        values.sort((Comparator.comparing(valuePopularityMap::get)));
        return values.iterator();
    }

    private <T> void refreshPopularity(Map<T, Integer> popularityMap, Object key) {
        popularityMap.put((T)key, popularityMap.getOrDefault(key, 0) + 1);
    }

    private <T> T getPopularObj(Map<T, Integer> popularityMap) {
        int maxPopularity = 0;
        T popularObj = null;
        for (Map.Entry<T, Integer> entry : popularityMap.entrySet()) {
            if (entry.getValue() >= maxPopularity) {
                maxPopularity = entry.getValue();
                popularObj = entry.getKey();
            }
        }
        return popularObj;
    }

}
