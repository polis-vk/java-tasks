package ru.mail.polis.homework.collections;


import java.util.*;
import java.util.stream.Collectors;


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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap;
    private final Map<V, Integer> valuePopularityMap;

    public PopularMap() {
        map = new HashMap<>();
        keyPopularityMap = new HashMap<>();
        valuePopularityMap = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        keyPopularityMap = new HashMap<>();
        valuePopularityMap = new HashMap<>();
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
        countPopularity((K) key, keyPopularityMap);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        countPopularity((V) value, valuePopularityMap);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        countPopularity((K) key, keyPopularityMap);
        V value = map.get(key);
        if (value != null) {
            countPopularity((V) value, valuePopularityMap);
        }
        return value;
    }

    @Override
    public V put(K key, V value) {
        countPopularity((K) key, keyPopularityMap);
        V previousValue = map.put(key, value);
        if (previousValue != null) {
            countPopularity((V) previousValue, valuePopularityMap);
        }
        countPopularity((V) value, valuePopularityMap);
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        countPopularity((K) key, keyPopularityMap);
        V value = map.remove(key);
        if (value != null) {
            countPopularity((V) value, valuePopularityMap);
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
        keyPopularityMap.clear();
        valuePopularityMap.clear();
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
        return getPopular(keyPopularityMap);
    }

    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        return keyPopularityMap.getOrDefault(key, 0);
    }


    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        return getPopular(valuePopularityMap);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        return valuePopularityMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        return valuePopularityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList()).iterator();
    }

    private <T> T getPopular(Map<T, Integer> popularityMap) {
        if (popularityMap.isEmpty()) {
            return null;
        }

        return popularityMap.entrySet()
                .stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    private <T> void countPopularity(T key, Map<T, Integer> popularityMap) {
        popularityMap.compute(key, (newKey, val) -> val == null ? 1 : ++val);
    }
}
