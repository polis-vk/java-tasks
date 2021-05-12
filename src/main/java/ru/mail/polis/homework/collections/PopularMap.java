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
 * Всего 9 баллов (3 за общие методы, 6 за специальные)
 *
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;

    private final Map<Object, Integer> keysPopularity = new HashMap<Object, Integer>();

    private final Map<Object, Integer> valuesPopularity = new HashMap<Object, Integer>();

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
        increasePopularity(keysPopularity, key);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity(valuesPopularity, value);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        V value = map.get(key);
        increasePopularity(keysPopularity, key);
        increasePopularity(valuesPopularity, value);
        return value;
    }

    @Override
    public V put(K key, V value) {
        V oldValue = map.put(key, value);
        increasePopularity(keysPopularity, key);
        increasePopularity(valuesPopularity, value);
        increasePopularity(valuesPopularity, oldValue);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        V removedValue = map.remove(key);
        increasePopularity(keysPopularity, key);
        increasePopularity(valuesPopularity, removedValue);
        return removedValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            increasePopularity(keysPopularity, entry.getKey());
            increasePopularity(valuesPopularity, entry.getValue());
        }
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = map.keySet();
        for (K key : keySet) {
            increasePopularity(keysPopularity, key);
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> valueCollection = map.values();
        for (V value : valueCollection) {
            increasePopularity(valuesPopularity, value);
        }
        return valueCollection;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = map.entrySet();
        for (Entry<K, V> entry : entries) {
            increasePopularity(keysPopularity, entry.getKey());
            increasePopularity(valuesPopularity, entry.getValue());
        }
        return entries;
    }

    /**
     * Возвращает самый популярный, на данный момент, ключ
     * 1 балл
     */
    public K getPopularKey() {
        Entry<Object, Integer> popularEntry = getPopularEntry(keysPopularity);
        return (popularEntry == null) ? null : (K) popularEntry.getKey();
    }


    /**
     * Возвращает количество использование ключа
     * 1 балл
     */
    public int getKeyPopularity(K key) {
        if (keysPopularity.containsKey(key)) {
            return keysPopularity.get(key);
        }
        return 0;
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     * 1 балл
     */
    public V getPopularValue() {
        Entry<Object, Integer> popularEntry = getPopularEntry(valuesPopularity);
        return (V) popularEntry.getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     * 1 балл
     */
    public int getValuePopularity(V value) {
        if (valuesPopularity.containsKey(value)) {
            return valuesPopularity.get(value);
        }
        return 0;
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 балла
     */
    public Iterator<V> popularIterator() {
        return (Iterator<V>) valuesPopularity.entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .map(entry -> entry.getKey())
                .iterator();
    }

    private void increasePopularity(Map<Object, Integer> popularityMap, Object keyOrValue) {
        increasePopularity(popularityMap, keyOrValue, 1);
    }

    private void increasePopularity(Map<Object, Integer> popularityMap, Object keyOrValue, int increaseSize) {
        if (keyOrValue == null) {
            return;
        }
        Integer previousCount = popularityMap.get(keyOrValue);
        if (previousCount != null) {
            popularityMap.put(keyOrValue, previousCount + increaseSize);
        } else {
            popularityMap.put(keyOrValue, increaseSize);
        }
    }

    private Entry<Object, Integer> getPopularEntry(Map<Object, Integer> popularMap) {
        Entry<Object, Integer> popularEntry = null;
        for (Entry<Object, Integer> entry : popularMap.entrySet()) {
            if (popularEntry == null || entry.getValue() > popularEntry.getValue()) {
                popularEntry = entry;
            }
        }
        return popularEntry;
    }
}
