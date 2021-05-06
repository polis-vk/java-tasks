package ru.mail.polis.homework.collections;

import java.util.*;

/**
 * Написать структуру данных, реализующую интерфейс мапы + набор дополнительных методов.
 * 4 дополнительных метода должны возвращать самый популярный ключ и его популярность. (аналогично для самого популярного значения)
 * Популярность - это количество раз, который этот ключ/значение учавствовал/ло в других методах мапы, такие как
 * containsKey, get, put, remove (в качестве параметра и возвращаемого значения).
 * Считаем, что null я вам не передаю ни в качестве ключа, ни в качестве значения
 *
 * Так же надо сделать итератор (подробности ниже).
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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<K, Integer> popularKeys;
    private final Map<V, Integer> popularValues;

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
        updatePopularity(popularKeys, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatePopularity(popularValues, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V getValue = map.get(key);
        updatePopularity(popularKeys, (K) key);
        updatePopularity(popularValues, getValue);
        return getValue;
    }

    @Override
    public V put(K key, V value) {
        V putValue = map.put(key, value);
        updatePopularity(popularKeys, key);
        updatePopularity(popularValues, value);
        updatePopularity(popularValues, putValue);
        return putValue;
    }

    @Override
    public V remove(Object key) {
        V removeValue = map.remove(key);
        updatePopularity(popularKeys, (K) key);
        updatePopularity(popularValues, removeValue);
        return removeValue;
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
     * 1 балл
     */
    public K getPopularKey() {
        return null;
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return this.popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return null;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> valuesPopularList = new ArrayList<>(popularValues.keySet());
        valuesPopularList.sort(Comparator.comparing(popularValues::get));
        return valuesPopularList.iterator();
    }

    private <U> void updatePopularity(Map<U, Integer> updMap, U key) {
        if (key != null) {
            updMap.merge(key, 1, (oldValue, newValue) -> oldValue + newValue);
        }
    }

}
