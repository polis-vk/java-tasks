package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
    private Map<K, V> mapCopy = new HashMap<>();
    private final Map<K, Integer> mapPopularKeys = new HashMap<>();  //ключ - популярность
    private final Map<V, Integer> mapPopularValues = new HashMap<>();  //значение - популярность
    private Map.Entry<K, Integer> popularKey = null;
    private Map.Entry<V, Integer> popularValue = null;

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
        countKeys((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        countValues((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (map.containsKey(key)) {
            countValues(map.get(key));
            countKeys((K) key);
            return map.get(key);
        }
        countKeys((K) key);
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        countValues(value);
        countKeys(key);
        mapCopy.put(key, value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        countValues(mapCopy.get(key));
        countKeys((K) key);
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

    /**
     * Возвращает самый популярный, на данный момент, ключ
     */
    public K getPopularKey() {
        for (Map.Entry<K, Integer> key : mapPopularKeys.entrySet()) {
            if (popularKey == null || key.getValue().compareTo(popularKey.getValue()) > 0) {
                popularKey = key;
            }
        }
        return popularKey.getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!mapPopularKeys.containsKey(key)) {
            return 0;
        }
        return popularKey.getValue();
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        for (Map.Entry<V, Integer> key : mapPopularValues.entrySet()) {
            if (popularValue == null || key.getValue().compareTo(popularValue.getValue()) > 0) {
                popularValue = key;
            }
        }
        return popularValue.getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!mapPopularValues.containsKey(value)) {
            return 0;
        }
        return mapPopularValues.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return mapPopularValues.keySet().iterator();
    }

    //считает популярность ключей
    private void countKeys(K key) {
        if (mapPopularKeys != null && mapPopularKeys.containsKey(key)) {
            int temp = mapPopularKeys.get(key) + 1;
            mapPopularKeys.put(key, temp);
        } else {
            mapPopularKeys.put(key, 1);
        }
    }

    //считает популярность значений
    private void countValues(V value) {
        if (mapPopularValues != null && mapPopularValues.containsKey(value)) {
            int temp = mapPopularValues.get(value) + 1;
            mapPopularValues.put(value, temp);
        } else {
            mapPopularValues.put(value, 1);
        }
    }
}
