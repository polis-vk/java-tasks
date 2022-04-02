package ru.mail.polis.homework.collections;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;


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
 * Всего 10 тугриков (3 тугрика за общие методы, 2 тугрика за итератор, 5 тугриков за логику популярности)
 * @param <K> - тип ключа
 * @param <V> - тип значения
 */
public class PopularMap<K, V> implements Map<K, V> {

    private final Map<K, V> map;
    private final Map<K, Integer> keyPopularityMap = new HashMap<>();
    private final Map<V, Integer> valuePopularityMap = new HashMap<>();

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
        increasePopularity((K) key, keyPopularityMap);
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        increasePopularity((V) value, valuePopularityMap);
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        increasePopularity((K) key, keyPopularityMap);
        V value = map.get(key);
        increasePopularity(value, valuePopularityMap);
        return value;
    }

    @Override
    public V put(K key, V value) {
        increasePopularity(key, keyPopularityMap);
        increasePopularity(value, valuePopularityMap);
        V oldValue = map.put(key, value);
        increasePopularity(oldValue, valuePopularityMap);
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        increasePopularity((K) key, keyPopularityMap);
        V oldValue = map.remove(key);
        increasePopularity(oldValue, valuePopularityMap);
        return oldValue;
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
        if (keyPopularityMap.size() == 0) {
            return null;
        }
        return keyPopularityMap.entrySet().stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }


    /**
     * Возвращает количество использование ключа
     */
    public int getKeyPopularity(K key) {
        if (!keyPopularityMap.containsKey(key)) {
            return 0;
        }
        return keyPopularityMap.get(key);
    }

    /**
     * Возвращает самое популярное, на данный момент, значение. Надо учесть что значени может быть более одного
     */
    public V getPopularValue() {
        if (valuePopularityMap.size() == 0) {
            return null;
        }
        return valuePopularityMap.entrySet().stream()
                .max(Entry.comparingByValue())
                .get()
                .getKey();
    }

    /**
     * Возвращает количество использований значений в методах: containsValue, get, put (учитывается 2 раза, если
     * старое значение и новое - одно и тоже), remove (считаем по старому значению).
     */
    public int getValuePopularity(V value) {
        if (!valuePopularityMap.containsKey(value)) {
            return 0;
        }
        return valuePopularityMap.get(value);
    }

    /**
     * Вернуть итератор, который итерируется по значениям (от самых НЕ популярных, к самым популярным)
     * 2 тугрика
     */
    public Iterator<V> popularIterator() {
        List<V> values = valuePopularityMap.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return values.iterator();
    }

    public <T> void increasePopularity(T priorityMapKey, Map<T, Integer> priorityMap) {
        if (priorityMapKey == null) {
            return;
        }
        if (!priorityMap.containsKey(priorityMapKey)) {
            priorityMap.put(priorityMapKey, 0);
        }
        priorityMap.put(priorityMapKey, priorityMap.get(priorityMapKey) + 1);
    }
}
