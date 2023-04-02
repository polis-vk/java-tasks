package ru.mail.polis.homework.collections;


import java.util.*;


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
    private final Map<K, Integer> keyPopularityMap = new HashMap<>();
    private final Map<V, Integer> valuePopularityMap = new HashMap<>();

    public PopularMap() {
        this.map = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
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
        keyMapIncrease((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        valueMapIncrease((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V result = map.get(key);
        keyMapIncrease((K) key);
        valueMapIncrease(result);
        return result;
    }

    @Override
    public V put(K key, V value) {
        keyMapIncrease(key);
        valueMapIncrease(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V result = map.remove(key);
        keyMapIncrease((K) key);
        valueMapIncrease(result);
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
        int maxPopularityValue = 0;
        K popularKey = null;
        for (Entry<K, Integer> e : keyPopularityMap.entrySet()) {
            if (maxPopularityValue < e.getValue()) {
                maxPopularityValue = e.getValue();
                popularKey = e.getKey();
            }
        }
        return (K) popularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значений может быть более одного
     */
    public V getPopularValue() {
        int maxPopularityValue = 0;
        V popularKeyVal = null;
        for (Entry<V, Integer> e : valuePopularityMap.entrySet()) {
            if (maxPopularityValue <= e.getValue()) {
                maxPopularityValue = e.getValue();
                popularKeyVal = e.getKey();
            }
        }
        return popularKeyVal;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return null;
    }

    private void keyMapIncrease(K key) {
        if (keyPopularityMap.containsKey(key)) {
            keyPopularityMap.replace(key, keyPopularityMap.get(key) + 1);
        } else {
            keyPopularityMap.put(key, 1);
        }
    }

    private void valueMapIncrease(V keyVal) {
        if (valuePopularityMap.containsKey(keyVal)) {
            valuePopularityMap.replace(keyVal, valuePopularityMap.get(keyVal) + 1);
        } else {
            valuePopularityMap.put(keyVal, 1);
        }
    }
}
