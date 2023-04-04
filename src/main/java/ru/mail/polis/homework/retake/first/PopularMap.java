package ru.mail.polis.homework.retake.first;


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
 * Всего 10 баллов
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyCounters;
    private final Map<V, Integer> valueCounters;
    private K popularKey;
    private V popularValue;

    public PopularMap() {
        this.map = new HashMap<>();
        this.keyCounters = new HashMap<>();
        this.valueCounters = new HashMap<>();
    }

    public PopularMap(Map<K, V> map) {
        this.map = map;
        this.keyCounters = new HashMap<>();
        this.valueCounters = new HashMap<>();
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
        popularKey = checkUsage(keyCounters, popularKey, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        popularValue = checkUsage(valueCounters, popularValue, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        popularKey = checkUsage(keyCounters, popularKey, (K) key);
        V value = map.get(key);
        popularValue = checkUsage(valueCounters, popularValue, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        popularKey = checkUsage(keyCounters, popularKey, key);
        popularValue = checkUsage(valueCounters, popularValue, value);
        V previousValue = map.put(key, value);
        popularValue = checkUsage(valueCounters, popularValue, previousValue);
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        popularKey = checkUsage(keyCounters, popularKey, (K) key);
        V value = map.remove(key);
        popularValue = checkUsage(valueCounters, popularValue, value);
        return value;
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
        return popularKey;
    }

    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyCounters.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return popularValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valueCounters.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        return valueCounters.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Entry::getKey)
                .collect(Collectors.toList())
                .iterator();
    }

    private <T> T checkUsage(Map<T, Integer> countersMap, T popularElement, T element) {
        if (element == null) {
            return popularElement;
        }
        int elementValue = countersMap.merge(element, 1, Integer::sum);
        return elementValue > countersMap.getOrDefault(popularElement, 0) ? element : popularElement;
    }
}
