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

    private Entry<K, Integer> popularKey;
    private Entry<V, Integer> popularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        popularKeys = new HashMap<>();
        popularValues = new HashMap<>();
        popularKey = new AbstractMap.SimpleEntry<>(null, Integer.MIN_VALUE);
        popularValue = new AbstractMap.SimpleEntry<>(null, Integer.MIN_VALUE);
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        popularKeys = new HashMap<>();
        popularValues = new HashMap<>();
        popularKey = new AbstractMap.SimpleEntry<>(null, Integer.MIN_VALUE);
        popularValue = new AbstractMap.SimpleEntry<>(null, Integer.MIN_VALUE);
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
        updatingPopularMap(popularKeys, (K) key, popularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        updatingPopularMap(popularValues, (V) value, popularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V returnValue = map.get(key);

        updatingPopularMap(popularKeys, (K) key, popularKey);
        updatingPopularMap(popularValues, returnValue, popularValue);

        return returnValue;
    }

    @Override
    public V put(K key, V value) {
        V returnValue = map.put(key, value);

        updatingPopularMap(popularKeys, key, popularKey);
        updatingPopularMap(popularValues, returnValue, popularValue);
        updatingPopularMap(popularValues, value, popularValue);

        return returnValue;
    }

    @Override
    public V remove(Object key) {
        V returnValue = map.remove(key);

        updatingPopularMap(popularKeys, (K) key, popularKey);
        updatingPopularMap(popularValues, returnValue, popularValue);

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
        return findPopular(popularKeys, popularKey);
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return popularKeys.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return findPopular(popularValues, popularValue);
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
        List<V> values = new ArrayList<>(popularValues.keySet());
        values.sort(Comparator.comparing(popularValues::get));
        return values.iterator();
    }

    private <T> void updatingPopularMap(Map<T, Integer> updatableMap, T key, Entry<T, Integer> popular) {
        if (key != null) {
            updatableMap.compute(key, (keyNow, value) -> value == null ? 1 : value + 1);

            popular.setValue(Integer.MIN_VALUE);
        }
    }

    private <T> T findPopular(Map<T, Integer> mapForFind, Entry<T, Integer> popular) {
        if (popular.getValue() != Integer.MIN_VALUE) {
            return popular.getKey();
        }

        for (Entry<T, Integer> node : mapForFind.entrySet()) {
            if (node.getValue() > popular.getValue()) {
                popular = node;
            }
        }
        return popular.getKey();
    }

}
