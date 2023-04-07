package ru.mail.polis.homework.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


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
    private final Map<K, Integer> keyUsageCounterMap = new HashMap<>();
    private K maxUsableKey;
    private final Map<V, Integer> valueUsageCounterMap = new HashMap<>();
    private final Set<V> sortedValuesSet = new TreeSet<>((a, b) -> getValuePopularity(a) - getValuePopularity(b));
    private V maxUsableValue;

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
        increaseKeyUsageCounter((K) key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increaseValueUsageCounter((V) value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increaseKeyUsageCounter((K) key);

        V value = map.get(key);
        increaseValueUsageCounter(value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increaseKeyUsageCounter(key);
        increaseValueUsageCounter(value);

        V previousValue = map.put(key, value);
        if (previousValue != null) {
            increaseValueUsageCounter(previousValue);
        }
        return previousValue;
    }

    @Override
    public V remove(Object key) {
        increaseKeyUsageCounter((K) key);
        V deletedValue = map.remove(key);
        increaseValueUsageCounter(deletedValue);
        return deletedValue;
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
        return maxUsableKey;
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        return keyUsageCounterMap.getOrDefault(key, 0);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        return maxUsableValue;
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        return valueUsageCounterMap.getOrDefault(value, 0);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        return sortedValuesSet.iterator();
    }

    private void increaseKeyUsageCounter(K key) {
        if (key == null) {
            return;
        }
        Integer usageCounter = keyUsageCounterMap.merge(key, 1, Integer::sum);
        if (usageCounter > keyUsageCounterMap.getOrDefault(maxUsableKey, 0)) {
            maxUsableKey = key;
        }
    }

    private void increaseValueUsageCounter(V value) {
        if (value == null) {
            return;
        }
        Integer usageCounter = valueUsageCounterMap.merge(value, 1, Integer::sum);
        if (usageCounter > valueUsageCounterMap.getOrDefault(maxUsableValue, 0)) {
            maxUsableValue = value;
        }
        sortedValuesSet.remove(value);
        sortedValuesSet.add(value);
    }
}
