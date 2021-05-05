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
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        updatePopular(popularKeys, (K) key);

        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatePopular(popularValues, (V) value);

        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V returnValue = this.map.get(key);

        updatePopular(popularKeys, (K) key);
        updatePopular(popularValues, returnValue);

        return returnValue;
    }

    @Override
    public V put(K key, V value) {
        V returnValue = this.map.put(key, value);

        updatePopular(popularKeys, key);
        updatePopular(popularValues, value);
        updatePopular(popularValues, returnValue);

        return returnValue;
    }

    @Override
    public V remove(Object key) {
        V returnValue = this.map.remove(key);

        updatePopular(popularKeys, (K) key);
        updatePopular(popularValues, returnValue);

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        return getPopular(popularKeys);
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
        return getPopular(popularValues);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return this.popularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> valuesList = new ArrayList<>(popularValues.keySet());
        valuesList.sort(Comparator.comparing(popularValues::get));
        return valuesList.iterator();
    }

    private <T> void updatePopular(Map<T, Integer> updMap, T key) {
        if (key != null) {
            updMap.merge(key, 1, (oldValue, newValue) -> oldValue + newValue);
        }
    }

    private <T> T getPopular(Map<T, Integer> searchMap) {
        T popularValue = null;
        Integer maxCount = -1;
        for (Entry<T, Integer> entry : searchMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                popularValue = entry.getKey();
            }
        }
        return popularValue;
    }
}
