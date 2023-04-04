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

    private final Map<K, Integer> keysPopularity;
    private final Map<V, Integer> valuesPopularity;

    private K mostPopularKey;
    private V mostPopularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keysPopularity = new HashMap<>();
        this.valuesPopularity = new HashMap<>();
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
        mostPopularKey = increasePopularity((K) key, keysPopularity, mostPopularKey);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        mostPopularValue = increasePopularity((V) value, valuesPopularity, mostPopularValue);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        mostPopularKey = increasePopularity((K) key, keysPopularity, mostPopularKey);
        V value = map.get(key);
        mostPopularValue = increasePopularity(value, valuesPopularity, mostPopularValue);
        return value;
    }

    @Override
    public V put(K key, V value) {
        mostPopularKey = increasePopularity(key, keysPopularity, mostPopularKey);
        mostPopularValue = increasePopularity(value, valuesPopularity, mostPopularValue);
        V currentValue = map.put(key, value);
        mostPopularValue = increasePopularity(currentValue, valuesPopularity, mostPopularValue);
        return currentValue;
    }

    @Override
    public V remove(Object key) {
        mostPopularKey = increasePopularity((K) key, keysPopularity, mostPopularKey);
        V currentValue = map.remove(key);
        mostPopularValue = increasePopularity(currentValue, valuesPopularity, mostPopularValue);
        return currentValue;
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
        return mostPopularKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keysPopularity.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return mostPopularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valuesPopularity.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return valuesPopularity.keySet().stream()
                .sorted(this::compare)
                .iterator();
    }

    private int compare(V v1, V v2) {
        return Integer.compare(getValuePopularity(v1), getValuePopularity(v2));
    }

    private <T> T increasePopularity(T value, Map<T, Integer> popularityMap, T mostPopular) {
        if (value == null) {
            return mostPopular;
        }
        int popularity = popularityMap.merge(value, 1, Integer::sum);
        return popularityMap.getOrDefault(mostPopular, 0) < popularity ? value : mostPopular;
    }
}
