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
    private final Map<K, Integer> popularKeyMap;
    private final Map<V, Integer> popularValueMap;

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
        calculatePopularity((K) key, popularKeyMap);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        calculatePopularity((V) value, popularValueMap);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        calculatePopularity((K) key, popularKeyMap);
        calculatePopularity(value, popularValueMap);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = map.put(key, value);
        calculatePopularity(oldValue, popularValueMap);
        calculatePopularity(value, popularValueMap);
        calculatePopularity(key, popularKeyMap);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V oldValue = map.remove(key);
        calculatePopularity((K) key, popularKeyMap);
        calculatePopularity(oldValue, popularValueMap);
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

    public <T> void calculatePopularity(T key, Map<T, Integer> mapPopularity) {
        if (key == null) {
            return;
        }
        if (mapPopularity.containsKey(key)) {
            mapPopularity.put(key, mapPopularity.get(key) + 1);
        } else {
            mapPopularity.put(key, 1);
        }
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        return findPopularElement(popularKeyMap);
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return popularKeyMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return findPopularElement(popularValueMap);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return popularValueMap.getOrDefault(value,0);
    }

    public <T> T findPopularElement(Map<T,Integer> popularMap){
        int maxValue = 0;
        T value = null;

        for (Map.Entry<T, Integer> entry : popularMap.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                value = entry.getKey();
            }
        }

        return value;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(popularValueMap.keySet());

        //Здаем компаратору сравнение по методу get в мапе
        values.sort(Comparator.comparingInt(popularValueMap::get));
        return values.iterator();
    }
}
