package ru.mail.polis.homework.retake.first;


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
 * Всего 10 баллов
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyCounters;
    private final Map<V, Integer> valueCounters;

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
        checkUsage(keyCounters, (K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        checkUsage(valueCounters, (V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        checkUsage(keyCounters, (K) key);
        V value = map.get(key);
        checkUsage(valueCounters, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        checkUsage(keyCounters, key);
        checkUsage(valueCounters, value);
        V previousValue = map.put(key, value);
        checkUsage(valueCounters, previousValue);
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        checkUsage(keyCounters, (K) key);
        checkUsage(valueCounters, map.get(key));
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
        return getPopularElement(keyCounters);
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (keyCounters.containsKey(key)) {
            return keyCounters.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return getPopularElement(valueCounters);
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (valueCounters.containsKey(value)) {
            return valueCounters.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     */
    public Iterator<V> popularIterator() {
        List<Map.Entry<V, Integer>> pairs = new ArrayList<>(valueCounters.entrySet());
        pairs.sort(Entry.comparingByValue());
        List<V> valuesList = new ArrayList<>();
        for (Map.Entry<V, Integer> pair : pairs) {
            valuesList.add(pair.getKey());
        }
        return valuesList.iterator();
    }

    private <T> T getPopularElement(Map<T, Integer> countersMap) {
        int max = 0;
        T element = null;
        for (Map.Entry<T, Integer> pair : countersMap.entrySet()) {
            if (pair.getValue() >= max) {
                element = pair.getKey();
                max = pair.getValue();
            }
        }
        return element;
    }

    private <T> void checkUsage(Map<T, Integer> countersMap, T element) {
        if (element == null) {
            return;
        }
        if (countersMap.containsKey(element)) {
            countersMap.put(element, countersMap.get(element) + 1);
        } else {
            countersMap.put(element, 1);
        }
    }
}
