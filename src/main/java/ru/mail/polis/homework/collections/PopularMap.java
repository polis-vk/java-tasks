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


    private void refreshPopularKey(Object key) {

        if (popularKey.isEmpty()) {
            popularKey.put(key, 1);
            return;
        }
        if (!popularKey.containsKey(key)) {
            popularKey.put(key, 1);
        } else {
            popularKey.put(key, popularKey.get(key) + 1);
        }
    }

    private void refreshPopularValue(Object value) {
        if (popularValue.isEmpty()) {
            popularValue.put(value, 1);
            return;
        }
        if (!popularValue.containsKey(value)) {
            popularValue.put(value, 1);
        } else {
            popularValue.put(value, popularValue.get(value) + 1);
        }

    }

    private final Map<K, V> map;

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
        refreshPopularKey(key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        refreshPopularValue(value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        refreshPopularKey(key);
        V value = map.get(key);
        if (value != null) {
            refreshPopularValue(value);
        } else {
            V oldValue = oldMap.get(key);
            if (oldValue != null) {
                refreshPopularValue(oldValue);
            }
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        refreshPopularKey(key);
        refreshPopularValue(value);
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        refreshPopularKey(key);
        V value = map.get(key);
        if (value != null) {
            refreshPopularValue(value);
            oldMap.put(key, value);
        } else {
            refreshPopularValue(oldMap.get(key));
        }
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
//        объединение словарей
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
        return popular(popularKey);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!popularKey.containsKey(key)) {
            return 0;
        }
        return popularKey.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return (V) popular(popularValue);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!popularValue.containsKey(value)) {
            return 0;
        }
        return popularValue.get(value);
    }

    private K popular(Map<Object, Integer> popularMap) {
        int maxValue = -1;
        Object maxKey = null;
        int value;
        Object key;
        for (Map.Entry<Object, Integer> item : popularMap.entrySet()) {
            key = item.getKey();
            value = popularMap.get(key);
            if (value > maxValue) {
                maxValue = value;
                maxKey = key;
            }
        }
        return (K) maxKey;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return (Iterator<V>) popularValue.keySet().iterator();
    }

    private final Map<Object, Integer> popularKey = new HashMap<Object, Integer>();
    private final Map<Object, Integer> popularValue = new HashMap<Object, Integer>();
    private final Map<Object, V> oldMap = new HashMap<>();
}
