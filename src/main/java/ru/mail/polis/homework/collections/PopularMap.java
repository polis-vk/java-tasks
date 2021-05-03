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

    private final Map<K, Integer> mapOfPopularKeys;
    private final Map<V, Integer> mapOfPopularValues;

    public PopularMap() {
        this.map = new HashMap<>();
        mapOfPopularKeys = new HashMap<>();
        mapOfPopularValues = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        mapOfPopularKeys = new HashMap<>();
        mapOfPopularValues = new HashMap<>();
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
        updatingPopularMap(mapOfPopularKeys, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatingPopularMap(mapOfPopularValues, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V returnValue = map.get(key);

        updatingPopularMap(mapOfPopularKeys, (K) key);
        updatingPopularMap(mapOfPopularValues, returnValue);

        return returnValue;
    }

    @Override
    public V put(K key, V value) {
        V returnValue = map.put(key, value);

        updatingPopularMap(mapOfPopularKeys, key);
        updatingPopularMap(mapOfPopularValues, returnValue);
        updatingPopularMap(mapOfPopularValues, value);

        return returnValue;
    }

    @Override
    public V remove(Object key) {
        V returnValue = map.remove(key);

        updatingPopularMap(mapOfPopularKeys, (K) key);
        updatingPopularMap(mapOfPopularValues, returnValue);

        return returnValue;
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
        return findPopular(mapOfPopularKeys);
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return mapOfPopularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return findPopular(mapOfPopularValues);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return mapOfPopularValues.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        List<V> values = new ArrayList<>(mapOfPopularValues.keySet());
        values.sort(Comparator.comparing(mapOfPopularValues::get));
        return values.iterator();
    }

    private <T> void updatingPopularMap(Map<T, Integer> updatableMap, T key) {
        if (key != null) {
            updatableMap.put(key, updatableMap.getOrDefault(key, 0) + 1);
        }
    }

    private <T> T findPopular(Map<T, Integer> mapForFind) {
        T popular = null;
        int maxCountOfPopular = Integer.MIN_VALUE;
        for (Entry<T, Integer> node : mapForFind.entrySet()) {
            if (node.getValue() > maxCountOfPopular) {
                maxCountOfPopular = node.getValue();
                popular = node.getKey();
            }
        }
        return popular;
    }

}
