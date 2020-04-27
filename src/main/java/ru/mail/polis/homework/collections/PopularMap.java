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
    private final Map<K, Integer> mapKeyPopularity;
    private final Map<V, Integer> mapValuePopularity;
    private K popularKey;
    private V popularValue;


    public PopularMap() {
        this.map = new HashMap<>();
        this.mapKeyPopularity = new HashMap<>();
        this.mapValuePopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.mapKeyPopularity = new HashMap<>();
        this.mapValuePopularity = new HashMap<>();
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
        updateKeyPopularity((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updateValuePopularity((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        updateKeyPopularity((K) key);
        V result = map.get(key);
        updateValuePopularity(result);
        return result;
    }

    @Override
    public V put(K key, V value) {
        updateKeyPopularity(key);
        updateValuePopularity(value);
        V oldValue = map.put(key, value);
        updateValuePopularity(oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        updateKeyPopularity((K) key);
        V result = map.remove(key);
        updateValuePopularity(result);
        return result;
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
        return popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return mapKeyPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return mapValuePopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(mapValuePopularity.keySet());
        values.sort((Comparator.comparing(mapValuePopularity::get)));
        return values.iterator();
    }

    private void updateValuePopularity(V value) {
        if (value == null) {
            return;
        }

        int popularity = mapValuePopularity.getOrDefault(value, 0) + 1;
        mapValuePopularity.put(value, popularity);
        if (popularity > mapValuePopularity.getOrDefault(popularValue, 0)) {
            popularValue = value;
        }
    }

    private void updateKeyPopularity(K key) {
        if (key == null) {
            return;
        }

        int popularity = mapKeyPopularity.getOrDefault(key, 0) + 1;
        mapKeyPopularity.put(key, popularity);
        if (popularity > mapKeyPopularity.getOrDefault(popularKey, 0)) {
            popularKey = key;
        }
    }
}
